package com.nnacres.assessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class WelcomeController {

  @GetMapping(value = "")
  public String index() {
    return "index";
  }

}
