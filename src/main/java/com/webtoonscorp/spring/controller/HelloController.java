package com.webtoonscorp.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, path = "/hello")
    public ModelAndView hello() {

        ModelAndView mav = new ModelAndView();

        mav.addObject("title", "Hello");
        mav.setViewName("hello");

        return mav;
    }
}
