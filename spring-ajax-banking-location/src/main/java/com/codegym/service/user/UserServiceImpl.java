package com.codegym.service.user;

import com.codegym.model.User;
import com.codegym.model.dto.userRoleDTO.UserRegisterDTO;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements IUserService{
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<UserRegisterDTO> findUserDTOUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Boolean existsByUsername(String email) {
        return null;
    }
}
