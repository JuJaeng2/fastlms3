package com.zerobase.fastlms.main.controller;


import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.member.service.HistoryService;
import com.zerobase.fastlms.member.service.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponents mailComponents;
    private final RequestUtils requestUtils;
    private final HistoryService historyService;
    private final BannerService bannerService;

    @RequestMapping("/")
    public String index(Model model) {

        List<String> imageDirList = bannerService.imageList();
        model.addAttribute("imageDirList", imageDirList);
        return "index";
//        String email = "jjy108@naver.com";
//        String subject = " 안녕하세요. 제로베이스 입니다. ";
//        String text = "<p>안녕하세요.</p><p>반갑습니다.</p>";
//
//        mailComponents.sendMail(email, subject, text);

        

    }
    
    
    
    @RequestMapping("/error/denied")
    public String errorDenied() {
        
        return "error/denied";
    }
    
    
    
}
