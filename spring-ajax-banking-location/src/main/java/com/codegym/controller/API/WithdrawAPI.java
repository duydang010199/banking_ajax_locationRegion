package com.codegym.controller.API;

import com.codegym.exception.DataInputException;
import com.codegym.model.Customer;
import com.codegym.model.Withdraw;
import com.codegym.model.dto.WithdrawDTO;
import com.codegym.service.customer.ICustomerService;
import com.codegym.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/withdraws")
public class WithdrawAPI {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping
    public ResponseEntity<?> withdraw(@Validated @RequestBody WithdrawDTO withdrawDTO, BindingResult bindingResult) {
        new WithdrawDTO().validate(withdrawDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Long customerId =Long.parseLong(withdrawDTO.getCustomerId());
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new DataInputException("ID khách hàng không hợp lệ");
        }
        Customer customer = customerOptional.get();
        BigDecimal balance = customer.getBalance();
        BigDecimal transactionAmount = new BigDecimal(withdrawDTO.getTransactionAmount());

        if (balance.compareTo(transactionAmount) < 0) {
            throw new DataInputException("Số tiền muốn rút lớn hơn số tiền hiện có trong tài khoản");
        }
        if (transactionAmount.compareTo(new BigDecimal(100)) <= 0) {
            throw new DataInputException("Số tiền muốn rút ít nhất là 100 VND");
        }

        if (transactionAmount.compareTo(new BigDecimal(1000000000)) >= 0) {
            throw new DataInputException("Số tiền muốn rút nhiều nhất là 1.000.000.000 VND");
        }

        Withdraw withdraw = new Withdraw();
        withdraw.setTransactionAmount(transactionAmount);
        withdraw.setCustomer(customer);

        Customer newCustomer = customerService.withdraw(customer, withdraw);
        return new ResponseEntity<>(newCustomer.toCustomerDTO(), HttpStatus.CREATED);
    }
}