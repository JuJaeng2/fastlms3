package com.zerobase.fastlms.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class RequestUtils {
    public String getUserAgent(HttpServletRequest request){
        return request.getHeader("User-Agent");
    }

    public String getClientIP(HttpServletRequest request){
        return request.getRemoteAddr();
    }
}
