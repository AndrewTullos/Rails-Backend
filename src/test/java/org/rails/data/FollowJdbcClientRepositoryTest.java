package org.rails.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rails.TestHelper;

import org.rails.models.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FollowJdbcClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    FollowJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

    @Test
    void findAllFollowersByUserId() {
        List<Follow> actual = repository.findAllFollowersByUserId(1);
        List<Follow> expected = List.of(TestHelper.makeFollow());

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void findAllFolloweesByFollowerId() {
        List<Follow> actual = repository.findAllFolloweesByFollowerId(1);
        List<Follow> expected = List.of(TestHelper.makeFollow());

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }


    @Test
    void create() {
        Follow toFollow = TestHelper.makeFollow();
        toFollow.setId(0);

        Follow actual = repository.create(toFollow);
        assertNotNull(actual);
        assertNotEquals(0, actual.getId());
    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
    }
}