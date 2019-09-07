package com.nnacres.assessment.service;

import java.util.List;

import com.nnacres.assessment.dto.UserDTO;

public interface UsersService {

   List<UserDTO> getUserByUsername(final String username);

   UserDTO createUser(final UserDTO userDTO);


}
