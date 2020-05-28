package com.nyaxs.shypicture.controller;

import com.nyaxs.shypicture.bean.Picture;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 荡漾
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {
        Picture p = new Picture();
        p.setId(2333333);
        model.addAttribute("picture", p);
        return "hello";
    }

}
