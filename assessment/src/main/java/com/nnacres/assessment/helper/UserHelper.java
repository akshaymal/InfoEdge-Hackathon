package com.nnacres.assessment.helper;

import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.nnacres.assessment.dto.UserDTO;

public class UserHelper {

   public static UserDTO signInUser(final List<UserDTO> users,
                                    final UserDTO userRequest){
      if (CollectionUtils.isEmpty(users) || Objects.isNull(userRequest))
         return null;

      for (final UserDTO user : users){
         if (user.getUsername().equals(userRequest.getUsername()) &&
                 user.getPassword().equals(userRequest.getPassword())){
            user.setPassword(null);
            return user;
         }
      }
      return null;
   }
}
