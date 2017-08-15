package com.webtoonscorp.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, path = "/hello")
    public ModelAndView hello(@RequestParam("hi") String message) {

        logger.info("Hi : {}", message);

        ModelAndView mav = new ModelAndView();

        mav.addObject("title", "Hello");
        mav.setViewName("hello");

        return mav;
    }
}
