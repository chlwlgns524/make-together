package com.maketogether.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.maketogether.membership.user.entity.User;
import com.maketogether.membership.userinfo.entity.UserInfo;
import com.maketogether.userinfo.entity.UserInfoTest;
import java.util.ArrayList;
import java.util.List;
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
//@AutoConfigureTestDatabase(replace = Replace.NONE) // I won't use the default-configured embeded H2 because I want to use my tcp-based H2
@DataJpaTest
class UserTest {

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

    @DisplayName("User inherits properties of BaseTimeEntity and the functionality of JPA auditing is applied to User well.")
    @Test
    void testUserEntityExtendedWithBaseTimeEntityAndJpaAuditing() throws InterruptedException {
        tx.begin();

        //Given
        User user = getUserInstance();

        //When
        em.persist(user);
        em.clear();

        User userEntity = em.createQuery("select u from User u where u.email=:email", User.class)
            .setParameter("email", user.getEmail())
            .getSingleResult();

        Thread.sleep(1000); // intended for the time gap between createdAt and updatedAt

        userEntity.increaseNumberOfApplication();
        em.flush();
        em.clear();

        userEntity = em.createQuery("select u from User u where u.email=:email", User.class)
            .setParameter("email", user.getEmail())
            .getSingleResult();

        //Then
        assertThat(userEntity.getCreatedAt()).isNotNull();
        assertThat(userEntity.getUpdatedAt()).isNotNull();
        assertThat(userEntity.getCreatedAt().getSecond())
            .isNotEqualTo(userEntity.getUpdatedAt().getSecond());
        assertThat(userEntity.getExpiredAt()).isNull();

        tx.rollback();
    }

    @DisplayName("User is mapped by UserInfo uni-directly.")
    @Test
    void testOneToOneMappingFromUserToUserInfo() {
        tx.begin();

        // Given
        UserInfo userInfo = UserInfoTest.getUserInfoInstanceWithSkills();
        User user = getUserInstance();
        user.appendUserInfo(userInfo);

        // When
        em.persist(user);
        em.clear();

        User userEntity = em.createQuery("select u from User u where u.email=:email", User.class)
            .setParameter("email", user.getEmail())
            .getSingleResult();

        String name = userEntity.getUserInfo().getName();

        // Then
        assertThat(name).isEqualTo(userInfo.getName());
        tx.rollback();
    }

    public static User getUserInstance() {
        String testEmail = "test@gmail.com";
        return User.builder()
            .email(testEmail)
            .password("1234")
            .build();
    }

    public static List<User> getUserInstances(int numberOfInstances) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < numberOfInstances; i++) {
            userList.add(User.builder()
                .email("test" + i + "@gmail.com")
                .password("1234")
                .build());
        }
        return userList;
    }

}