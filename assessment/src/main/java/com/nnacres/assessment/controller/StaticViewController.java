package com.nnacres.assessment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author anand
 * @since 0.0.1
 */
@Controller
@Slf4j
@RequestMapping("/static/views")
public class StaticViewController {

    @GetMapping("")
    public ModelAndView view(@RequestParam String path) {
        log.info("serving view request {}", path);
        return new ModelAndView(path);
    }
}
