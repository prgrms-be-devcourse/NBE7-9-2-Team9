package com.backend.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class Member {

    //TODO: name

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // (식별자)

    @Column(nullable = false, unique = true, length = 30)
    private String memberId; // 로그인용 아이디

    @Column(nullable = false)
    private String password; // 암호화된 비밀번호

    @Column(nullable = false, unique = true, length = 50)
    private String email; // 중복 가입 방지

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public boolean isDeleted() {
        return deletedAt != null;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }
}
