package com.zerobase.fastlms.member.entity;

import com.zerobase.fastlms.configuration.JpaAuditingConfiguration;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private LocalDateTime loginDate;

    private String connectIp;
    private String userAgent;

}
