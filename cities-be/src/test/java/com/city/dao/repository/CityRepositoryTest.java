package com.city.dao.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.city.dao.entity.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
class CityRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CityRepository repository;

    @Test
    void contextLoads() {
        assertNotNull(em);
    }

    @Test
    void persistCity() {

        City city = createCity();
        Long id = city.getId();

        assertNotNull(id);
        em.merge(city);
        assertEquals(id, city.getId());

    }

    @Test
    void verifyRepositoryByPersistingCity() {

        City city = createCity();
        Long id = city.getId();

        assertNotNull(id);
        repository.save(city);
        assertEquals(id, city.getId());
        assertNotNull(city.getName());
        assertNotNull(city.getPhoto());
    }

    @Test
    void findWalletById() {

        //given
        City city = repository.save(createCity());

        //when
        Optional<City> walletByUsername = repository.findById(city.getId());

        //then
        assertEquals(city, walletByUsername.orElseThrow());
    }

    private City createCity() {
        return new City(
                1L,
                "city name",
                "city url"
        );
    }
}
