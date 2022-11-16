package com.maketogether.membership.userinfo.entity;

import com.maketogether.common.BaseTimeEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_info")
@Entity
public class UserInfo extends BaseTimeEntity {

    @Column(name = "user_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 30)
    private String major;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Skill> skillList = new HashSet<>();

    @Builder
    public UserInfo(String name, String phoneNumber, String major, Address address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.major = major;
        this.address = address;
    }

}
