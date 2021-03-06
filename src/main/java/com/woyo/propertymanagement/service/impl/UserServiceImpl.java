package com.woyo.propertymanagement.service.impl;

import com.woyo.propertymanagement.converter.UserConverter;
import com.woyo.propertymanagement.dto.UserDTO;
import com.woyo.propertymanagement.entity.UserEntity;
import com.woyo.propertymanagement.exception.BusinessException;
import com.woyo.propertymanagement.exception.ErrorModel;
import com.woyo.propertymanagement.repository.UserRepository;
import com.woyo.propertymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;

    @Override
    public UserDTO register(UserDTO userDTO) {
        Optional<UserEntity> user = userRepository.findByOwnerEmail(userDTO.getOwnerEmail());

        if (user.isPresent()) {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("EMAIL_ALREADY_EXIST");
            errorModel.setMessage("The email you are trying to register already exist");
            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);
        }

        UserEntity userEntity = userConverter.convertDTOtoEntity(userDTO);
        userEntity = userRepository.save(userEntity);
        userDTO = userConverter.convertEntityToDTO(userEntity);

        return userDTO;
    }

    @Override
    public UserDTO login(String email, String password) throws BusinessException {
        UserDTO userDTO = null;
        Optional<UserEntity> userEntity = userRepository.findByOwnerEmailAndPassword(email, password);

        if (userEntity.isPresent()) {
            userDTO = userConverter.convertEntityToDTO(userEntity.get());
        } else {
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("INVALID_LOGIN");
            errorModel.setMessage("Incorrect Email or Password");
            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);
        }

        return userDTO;
    }
}
