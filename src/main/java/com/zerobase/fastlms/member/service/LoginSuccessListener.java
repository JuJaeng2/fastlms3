package com.zerobase.fastlms.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class LoginSuccessListener
        implements ApplicationListener<AuthenticationSuccessEvent> {

    private final HistoryService historyService;
    private final HttpServletRequest request;


    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // 로그인 성공 이벤트가 발생할 때 실행되는 로직
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        String userId = userDetails.getUsername();

        String ipAddress = getClientIp();
        String userAgent = request.getHeader("User-Agent");

        System.out.println("히스토리가 저장되었습니다.");

        // 로그인 히스토리 저장
        historyService.saveLoginHistory(userId, ipAddress, userAgent);
    }

    private String getClientIp(){
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null) {
            return xForwardedFor;
        }
        return request.getRemoteAddr();
    }
}