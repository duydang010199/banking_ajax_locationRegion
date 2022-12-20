package com.codegym.service.user;

import com.codegym.model.User;
import com.codegym.model.dto.userRoleDTO.UserRegisterDTO;
import com.codegym.service.IGeneralService;

import java.util.Optional;


public interface IUserService extends IGeneralService<User> {
    User getByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<UserRegisterDTO> findUserDTOUsername(String username);

    Boolean existsByUsername(String email);

}
