package com.maketogether.userinfo.entity;

import com.maketogether.membership.userinfo.entity.Skill;
import java.util.ArrayList;
import java.util.List;

public class SkillTest {

    public static Skill getSkillInstance() {
        return Skill.builder()
            .name("Java")
            .build();
    }

    public static List<Skill> getSkillInstances(int numberOfSkills) {
        List<Skill> skillList = new ArrayList<>();
        for (int i = 0; i < numberOfSkills; i++) {
            skillList.add(
                Skill.builder()
                    .name("Java" + i)
                    .build()
            );
        }
        return skillList;
    }

}
