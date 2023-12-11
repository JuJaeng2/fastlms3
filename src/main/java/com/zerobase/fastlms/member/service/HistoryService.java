package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HistoryService {

    private final LoginHistoryRepository loginHistoryRepository;


    public void saveLoginHistory(String userId, String ipAddress, String userAgent) {
        LoginHistory loginHistory = LoginHistory.builder()
                .userId(userId)
                .connectIp(ipAddress)
                .userAgent(userAgent)
                .loginDate(LocalDateTime.now())
                .build();

        this.loginHistoryRepository.save(loginHistory);
    }

    public List<LoginHistoryDto> getLoginHistory(String userId) {
        List<LoginHistory> loginHistories = loginHistoryRepository.findByUserId(userId);

        List<LoginHistoryDto> loginHistoryDtoList = loginHistories.stream()
                .map(loginHistory -> new LoginHistoryDto().of(loginHistory))
                .collect(Collectors.toList());

        return loginHistoryDtoList;
    }
}
