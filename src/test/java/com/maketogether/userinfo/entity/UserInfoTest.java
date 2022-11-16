package com.maketogether.userinfo.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.maketogether.membership.userinfo.entity.Address;
import com.maketogether.membership.userinfo.entity.Skill;
import com.maketogether.membership.userinfo.entity.UserInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class UserInfoTest {

    @PersistenceUnit
    EntityManagerFactory emf;

    EntityManager em;

    EntityTransaction tx;

    @BeforeEach
    void getEntityManagerAndTransaction() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @AfterEach
    void closeEntityMangerAfterTesting() {
        em.close();
    }

    @DisplayName("UserInfo manages life cycle of Skill.")
    @Test
    void testLifeCycleUserInfoWithSkill() {
        // Given
        tx.begin();

        UserInfo userInfo = getUserInfoInstanceWithoutSkills();

        int numberOfSkills = 2;
        List<Skill> skillInstances = SkillTest.getSkillInstances(numberOfSkills);
        skillInstances.forEach(skill -> skill.addSkillToUserInfo(userInfo));

        // When
        em.persist(userInfo);
        em.clear();

        // Then
        UserInfo userInfoEntity = em.find(UserInfo.class, userInfo.getId());
        assertThat(userInfoEntity.getSkillList().size()).isEqualTo(numberOfSkills);

        // When
        userInfoEntity.getSkillList().clear();
        em.flush();
        em.clear();

        // Then
        Set<Skill> skillList = em.find(UserInfo.class, userInfoEntity.getId()).getSkillList();
        assertThat(skillList.size()).isEqualTo(0);

        tx.rollback();
    }

    public static UserInfo getUserInfoInstanceWithoutSkills() {
        Address address = Address.builder()
            .zipcode("11155")
            .city("서울시")
            .street("테헤란로 150")
            .build();

        return UserInfo.builder()
            .name("테스트이름")
            .phoneNumber("010-1234-5678")
            .major("컴퓨터공학과")
            .address(address)
            .build();
    }

    public static UserInfo getUserInfoInstanceWithSkills() {
        UserInfo userInfo = getUserInfoInstanceWithoutSkills();

        List<Skill> skillInstances = SkillTest.getSkillInstances(5);
        skillInstances.forEach(skill -> skill.addSkillToUserInfo(userInfo));

        return userInfo;
    }

    public static List<UserInfo> getUserInfoInstancesWithSkills(int numberOfInstances) {
        List<UserInfo> userInfoList = new ArrayList<>();
        for (int i = 0; i < numberOfInstances; i++) {
            UserInfo userInfo = getUserInfoInstanceWithoutSkills();
            List<Skill> skillInstances = SkillTest.getSkillInstances(5);
            skillInstances.forEach(skill -> skill.addSkillToUserInfo(userInfo));

            userInfoList.add(userInfo);
        }

        return userInfoList;
    }

}
