package com.company.ledstore.service;

import com.company.ledstore.service.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends AbstractService<UserDTO, Long>{
    UserDTO findByUsername(String username);
}
