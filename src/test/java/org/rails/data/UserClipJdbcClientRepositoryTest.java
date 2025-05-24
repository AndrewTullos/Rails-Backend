package org.rails.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rails.TestHelper;
import org.rails.models.UserClip;
import org.rails.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserClipJdbcClientRepositoryTest {


    @Autowired
    JdbcClient client;

    @Autowired
    UserClipJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }


    @Test
    void findById() {
        UserClip actual = repository.findById(1 );
        UserClip expected = TestHelper.makeClip();

        assertNotNull(actual);
        assertEquals(expected.getClipId(), actual.getClipId());
    }

    @Test
    void findAllByUserId() {
        List<UserClip> actual = repository.findAllClipsByUserId(1);
        UserClip expected = TestHelper.makeClip();

        assertNotNull(actual);
        assertEquals(expected.getClipId(), actual.get(0).getClipId());
    }

//    Two clip inserts in test schema
//    Checks size
    @Test
    void findAllClipsByFollowees() {
        List<UserClip> actual = repository.findAllClipsByFollowees(1);

        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    // Todo: Implement test
    @Test
    void findRecentClips() {

    }

    @Test
    void create() {
        UserClip clipToAdd = new UserClip();
        clipToAdd.setClipId(1);
        clipToAdd.setUserProfile(TestHelper.makeUser());
        clipToAdd.setMediaUrl("testClip1URL.com");
        clipToAdd.setCaption("Skateboard");

        UserClip actual = repository.create(clipToAdd);

        UserClip expected = TestHelper.makeClip();

        assertNotNull(actual);
        assertNotNull(actual.getClipId());
        assertEquals(clipToAdd.getUserProfile(), actual.getUserProfile());
        assertEquals("testClip1URL.com", actual.getMediaUrl());
        assertEquals("Skateboard", actual.getCaption());
    }

    // Todo: Implement test
    @Test
    void update() {
    }

    @Test
    void deleteById() {
        UserClip clip = new UserClip();
        clip.setUserProfile(TestHelper.makeUser());
        clip.setMediaUrl("testClipURL.com");
        clip.setCaption("Test Clip");
        UserClip createdClip = repository.create(clip);

        boolean actual = repository.deleteById(createdClip.getClipId());

        assertTrue(actual, "Delete should return true on successful deletion");
        UserClip deleted = repository.findById(createdClip.getClipId());
        assertNull(deleted, "Clip should no longer exist after deletion");
    }
}