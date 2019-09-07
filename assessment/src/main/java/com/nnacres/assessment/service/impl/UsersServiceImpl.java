package com.nnacres.assessment.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnacres.assessment.dto.UserDTO;
import com.nnacres.assessment.entity.Users;
import com.nnacres.assessment.exception.GenericException;
import com.nnacres.assessment.repository.UsersRepository;
import com.nnacres.assessment.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

   @Autowired
   private UsersRepository usersRepository;

   @Override
   @Transactional(rollbackFor = GenericException.class)
   public List<UserDTO> getUserByUsername(final String username) {
      final Optional<List<Users>> usersFromRepo = usersRepository.findByUsername(username);
      if (usersFromRepo.isPresent()){
         final List<Users> users = usersFromRepo.get();
         final List<UserDTO> userResult = new ArrayList<>();
         for (final Users user : users){
            final UserDTO userDTO = UserDTO.builder().id(user.getId())
                                                     .username(user.getUsername())
                                                     .password(user.getPassword())
                                                     .build();
            userResult.add(userDTO);
         }
         return userResult;
      } else {
         return new ArrayList<>();
      }
   }

   @Override
   public UserDTO createUser(UserDTO userDTO) {
      Users users = Users.builder().username(userDTO.getUsername())
                                   .password(userDTO.getPassword())
                                   .build();


      Timestamp timestamp = Timestamp.from(Instant.now());

      users.setCreatedDate(timestamp);
      users.setUpdatedDate(timestamp);
      users = usersRepository.save(users);
      return UserDTO.builder().username(userDTO.getUsername())
                              .password(userDTO.getPassword())
                              .build();
   }
}
