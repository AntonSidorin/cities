package com.city.dao.repository;

import static com.city.dao.entity.Role.ALLOW_EDIT;
import static com.city.dao.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.city.dao.entity.Role;
import com.city.dao.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;
import java.util.Set;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository repository;

    @Test
    void contextLoads() {
        assertNotNull(em);
    }

    @Test
    void persistAnUser() {
        User user = createUser(USER);
        assertNotNull(user.getUsername());
        String username = user.getUsername();

        em.persist(user);

        assertEquals(username, user.getUsername());
        assertNotNull(user.getFirstname());
        assertNotNull(user.getLastname());
        Assertions.assertTrue(user.getRoles().contains(USER));
    }

    @Test
    void verifyRepositoryByPersistingAnUser() {
        User user = createUser(ALLOW_EDIT);

        assertNotNull(user.getUsername());
        String username = user.getUsername();
        repository.save(user);

        assertEquals(username, user.getUsername());
        assertTrue(user.getRoles().contains(ALLOW_EDIT));
    }

    @Test
    void findAnUserByUsername() {

        //given
        User user = repository.save(createUser(USER));

        //when
        Optional<User> userByUsername = repository.findByUsername(user.getUsername());

        //then
        assertEquals(user, userByUsername.orElseThrow());
    }

    private User createUser(Role role){
        return new User(
                "username",
                "password",
                "firstname",
                "lastName",
                Set.of(role)
        );
    }
}
