package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.LoginHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistoryDto {

    private LocalDateTime loginDate;
    private String clientIp;
    private String userAgent;

    public LoginHistoryDto of(LoginHistory loginHistory){
        return LoginHistoryDto.builder()
                .loginDate(loginHistory.getLoginDate())
                .clientIp(loginHistory.getConnectIp())
                .userAgent(loginHistory.getUserAgent())
                .build();
    }
}
