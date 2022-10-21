package com.maketogether.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Builder
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

}
