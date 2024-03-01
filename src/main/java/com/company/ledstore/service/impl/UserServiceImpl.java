package com.company.ledstore.service.impl;

import com.company.ledstore.data.entity.User;
import com.company.ledstore.data.repository.UserRepository;
import com.company.ledstore.service.UserService;
import com.company.ledstore.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        mapper.map(userDTO, user);
        User updatedUser = userRepository.save(user);
        return mapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return mapper.map(user, UserDTO.class);
    }
}

