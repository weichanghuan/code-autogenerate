package com.wch.code.generate.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitController.class);

    @RequestMapping(value={"/","/index"})
    public String index(Model model){
        model.addAttribute("name","ok");
        return "/sucess";
    }



}
