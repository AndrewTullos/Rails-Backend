package org.rails.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.rails.data.FollowRepository;
import org.rails.models.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.rails.TestHelper.makeFollow;
import static org.rails.TestHelper.makeUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FollowServiceTest {

    @MockBean
    FollowRepository repository;

    @Autowired
    FollowService service;

    @Nested
    class find{
        @Test
        void findAllFollowersByUserId() {
            Follow expected = makeFollow();

            when(repository.findAllFollowersByUserId(1))
                    .thenReturn(List.of(makeFollow()));

            List<Follow> actual = service.findAllFollowersByUserId(1).getPayload();

            assertEquals(List.of(expected.getId()).size(), actual.size());
            assertEquals(expected.getFollowing().getUserId(), actual.get(0).getFollowing().getUserId());
        }

        @Test
        void findAllFolloweesByFollowerId() {
            Follow expected = makeFollow();

            when(repository.findAllFolloweesByFollowerId(1))
                    .thenReturn(List.of(makeFollow()));

            List<Follow> actual = service.findAllFolloweesByFollowerId(1).getPayload();

            assertEquals(List.of(expected.getId()).size(), actual.size());
            assertEquals(expected.getFollower().getUserId(), List.of(actual.get(0).getFollower().getUserId()).get(0));
        }
    }

    @Test
    void create() {
        Follow expected = makeFollow();

        when(repository.create(expected))
                .thenReturn(makeFollow());

        Follow actual = service.create(expected).getPayload();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFollower().getUserId(), actual.getFollower().getUserId());
    }

    @Test
    void shouldNotCreate() {
        Follow follow = makeFollow();
        follow.setFollower(makeUser());
        follow.setFollowing(makeUser());

        Result<Follow> result = service.create(follow);

        assertFalse(result.isSuccess());
    }

    @Test
    void deleteById() {
    }
}