package com.zerobase.fastlms.admin.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bannerName; // banner alter text
    private String imageFileName; // file directory
    private String imageLink; // banner link url
    private boolean openYn; // 공개 여부
    private int bannerOrder;
    private String newWindow; // 새 창인지(true) 현재 창인지(false)
    private LocalDate registerDate;


}
