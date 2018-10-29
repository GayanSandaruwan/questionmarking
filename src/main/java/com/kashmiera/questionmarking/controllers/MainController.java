package com.kashmiera.questionmarking.controllers;

import com.kashmiera.questionmarking.questionPaperMarking.questionMarking;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/")
    @ResponseBody
    String  home(){

        return "Hello";
    }

    @RequestMapping(value = "/sim/score", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    SimilarityScore calcScore(){
        questionMarking am = new questionMarking();
        return am.paperMarking(1);

    }
}
