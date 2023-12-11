package com.zerobase.fastlms.admin.dto;


import com.zerobase.fastlms.admin.entity.Banner;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@Data
public class BannerInput {

    private long bannerId;
    private String bannerName;
    private MultipartFile imageFile;
    private String imageFileName;
    private String imageLink;
    private boolean openYn;
    private int order;
    private String newWindow;
    private LocalDate registerDate;

    public Banner toBanner(String originalFileName){
        return Banner.builder()
                .bannerName(this.bannerName)
                .imageFileName(originalFileName)
                .imageLink(this.imageLink)
                .openYn(this.openYn)
                .bannerOrder(this.order)
                .newWindow(this.newWindow)
                .registerDate(LocalDate.now())
                .build();
    }
}
