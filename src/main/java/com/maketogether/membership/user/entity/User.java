package com.maketogether.membership.user.entity;

import com.maketogether.common.BaseTimeEntity;
import com.maketogether.membership.userinfo.entity.UserInfo;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {

    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, updatable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    private boolean posted;

    private int numberOfApplication;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JoinColumn(name = "user_info_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
        orphanRemoval = true)
    private UserInfo userInfo;

    @Builder
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public void appendUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void increaseNumberOfApplication() {
        if (numberOfApplication == 3)
            throw new RuntimeException("더이상 지원할 수 없습니다.");
        numberOfApplication++;
        evaluatePosted();
    }

    public void decreaseNumberOfApplication() {
        if (numberOfApplication == 0)
            throw new RuntimeException("프로젝트 지원 수가 현재 0개 입니다.");
        numberOfApplication--;
        evaluatePosted();
    }

    private void evaluatePosted() {
        posted = numberOfApplication > 0;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

}
