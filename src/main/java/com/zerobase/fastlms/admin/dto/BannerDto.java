package com.zerobase.fastlms.admin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BannerDto {

    private String bannerName;
    private String image;
    private LocalDate registerDate;
    private int order;

}
