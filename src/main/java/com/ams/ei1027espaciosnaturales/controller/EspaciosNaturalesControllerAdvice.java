package com.ams.ei1027espaciosnaturales.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class EspaciosNaturalesControllerAdvice {

    @ExceptionHandler(value = EspaciosNaturalesException.class)
    public ModelAndView handleClubException(EspaciosNaturalesException ex){

        ModelAndView mav = new ModelAndView("error/exceptionError");
        mav.addObject("message", ex.getMessage());
        mav.addObject("errorName", ex.getErrorName());
        mav.addObject("path", ex.getPath());
        return mav;
    }
}
