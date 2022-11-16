package com.maketogether.membership.userinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "skills")
@Entity
public class Skill {

    @Column(name = "skill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 30)
    private String name;

    @JoinColumn(name = "user_info_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserInfo userInfo;

    @Builder
    public Skill(String name) {
        this.name = name;
    }

    public void addSkillToUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        this.userInfo.getSkillList().add(this);
    }

}
