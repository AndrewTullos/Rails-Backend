package org.rails.domain;

import org.junit.jupiter.api.Test;
import org.rails.data.UserClipRepository;
import org.rails.models.UserClip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.rails.TestHelper.makeClip;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserClipServiceTest {

    @MockBean
    UserClipRepository repository;

    @Autowired
    UserClipService service;

    @Test
    void findById() {
        UserClip expected = makeClip();

        when(repository.findById(1))
                .thenReturn(makeClip());

        UserClip actual = service.findById(1).getPayload();
        assertEquals(expected.getClipId(), actual.getClipId());
        assertEquals(expected.getUserProfile().getUserId(), actual.getUserProfile().getUserId());
    }

    @Test
    void findAllClipsByUserId() {
        UserClip expected = makeClip();

        when(repository.findAllClipsByUserId(1))
                .thenReturn(List.of(makeClip()));

        List<UserClip> actual = service.findAllClipsByUserId(1).getPayload();
        assertEquals(expected.getClipId(), actual.get(0).getClipId());

    }

    @Test
    void findAllClipsByFollowees() {
        UserClip expected = makeClip();

        when(repository.findAllClipsByFollowees(1))
                .thenReturn(List.of(makeClip()));

        List<UserClip> actual = service.findAllClipsByFollowees(1).getPayload();
        assertEquals(expected.getClipId(), actual.get(0).getClipId());
    }

    @Test
    void create() {
        UserClip expected = makeClip();

        when(repository.create(makeClip()))
                .thenReturn(makeClip());

        UserClip actual = service.create(makeClip()).getPayload();
        assertEquals(expected.getClipId(), actual.getClipId());
    }

    @Test
    void delete() {
        when(repository.deleteById(1))
                .thenReturn(true);

        assertTrue(service.delete(1));
    }
}