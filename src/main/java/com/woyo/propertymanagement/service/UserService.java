package com.woyo.propertymanagement.service;

import com.woyo.propertymanagement.dto.UserDTO;
import com.woyo.propertymanagement.exception.BusinessException;

public interface UserService {

    UserDTO register(UserDTO userDTO);
    UserDTO login(String email, String password) throws BusinessException;
}
