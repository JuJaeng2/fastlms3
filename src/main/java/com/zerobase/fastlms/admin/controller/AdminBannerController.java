package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerInput;

import com.zerobase.fastlms.admin.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/banner")
public class AdminBannerController {

    private final BannerService bannerService;

    @PostMapping(value = "/add.do")
    public String add(
            @RequestParam String bannerName,
            @RequestParam(required = false) MultipartFile imageFile,
            @RequestParam String imageLink,
            @RequestParam String newWindow,
            @RequestParam int order,
            @RequestParam(defaultValue = "false") boolean openYn) {

        BannerInput bannerInput = BannerInput.builder()
                .bannerName(bannerName)
                .imageFile(imageFile.isEmpty() ? null : imageFile)
                .imageLink(imageLink)
                .newWindow(newWindow)
                .order(order)
                .openYn(openYn)
                .build();

        bannerService.saveBanner(bannerInput);

        return "redirect:/admin/banner/list.do";

    }

    @GetMapping("/add.do")
    public String add() {

        return "admin/banner/add";
    }

    @GetMapping("/list.do")
    public String list(Model model){

        List<BannerInput> bannerInfoList = bannerService.list();
        model.addAttribute("bannerInfoList", bannerInfoList);
        System.out.println(bannerInfoList);

        return "/admin/banner/list";
    }

    @GetMapping("/list/delete.do")
    public String deleteList(@RequestParam() List<Long> deleteList){

        System.out.println("삭제할 목록 : " + deleteList);
        bannerService.delete(deleteList);

        return "redirect:/admin/banner/list.do";
    }

    @GetMapping("/detail.do")
    public String detail(Model model, @RequestParam long bannerId){

        BannerInput detail = bannerService.detail(bannerId);
        System.out.println("디테일 배너 ID : " + bannerId);
        model.addAttribute("bannerInfo", detail);

        return "/admin/banner/detail";
    }

    @PostMapping("/detail.do")
    public String update(
            @RequestParam long bannerId,
            @RequestParam String bannerName,
            @RequestParam(required = false) MultipartFile imageFile,
            @RequestParam String imageLink,
            @RequestParam String newWindow,
            @RequestParam int order,
            @RequestParam(defaultValue = "false") boolean openYn
    ){

        BannerInput bannerInput = BannerInput.builder()
                .bannerId(bannerId)
                .bannerName(bannerName)
                .imageFile(imageFile.isEmpty() ? null : imageFile)
                .imageLink(imageLink)
                .newWindow(newWindow)
                .order(order)
                .openYn(openYn)
                .build();

        bannerService.update(bannerInput);

        return "redirect:/admin/banner/list.do";
    }




}
