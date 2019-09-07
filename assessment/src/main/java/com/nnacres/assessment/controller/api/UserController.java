package com.nnacres.assessment.controller.api;


import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nnacres.assessment.dto.QuestionModel;
import com.nnacres.assessment.dto.UserDTO;
import com.nnacres.assessment.enums.ErrorCategory;
import com.nnacres.assessment.helper.UserHelper;
import com.nnacres.assessment.response.ResponseObject;
import com.nnacres.assessment.service.UsersService;

@RestController
@RequestMapping("${api.version}/users")
public class UserController {

   @Autowired
   private UsersService usersService;

   @RequestMapping(value = "/getUser/{username}", method = RequestMethod.GET)
   public ResponseEntity<ResponseObject<List<UserDTO>>> getUserByUsername(@PathVariable String username) {
      final List<UserDTO> users = usersService.getUserByUsername(username);
      ResponseObject<List<UserDTO>> responseObject = new ResponseObject<>(users);
      responseObject.setStatus(ErrorCategory.SUCCESS);
      return new ResponseEntity<>(responseObject, HttpStatus.OK);
   }

   @PostMapping(value = "/createUser")
   public ResponseEntity createUser(@RequestBody UserDTO userDTO) {
      final UserDTO userDTO1 = usersService.createUser(userDTO);
      ResponseObject<UserDTO> responseObject = new ResponseObject<UserDTO>(userDTO1);
      return new ResponseEntity<>(responseObject, HttpStatus.OK);
   }

   @PostMapping(value = "/signin")
   public ResponseEntity<ResponseObject<UserDTO>> signInUser(@RequestBody UserDTO userDTO) {
      final List<UserDTO> users = usersService.getUserByUsername(userDTO.getUsername());
      final UserDTO user = UserHelper.signInUser(users, userDTO);
      ResponseObject<UserDTO> responseObject = new ResponseObject<UserDTO>(user);
      if (Objects.isNull(user)){
         responseObject.setStatus(ErrorCategory.DATA_NOT_FOUND);
         return new ResponseEntity<>(responseObject, HttpStatus.NO_CONTENT);
      } else {
         responseObject.setStatus(ErrorCategory.SUCCESS);
         return new ResponseEntity<>(responseObject, HttpStatus.OK);
      }
   }

}
