package org.rails.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.rails.TestHelper;
import org.rails.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserProfileJdbcClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    UserProfileJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

@Nested
public class Find {
    @Test
    void findById() {
        UserProfile actual = repository.findById(1);
        UserProfile expected = TestHelper.makeUser();

        assertNotNull(actual);
        assertEquals(expected.getUserId(), actual.getUserId());
    }

    @Test
    void shouldNotFindMissingId() {
        UserProfile actual = repository.findById(1000);
        assertNull(actual);
    }

    @Test
    void findByUsername() {
        UserProfile actual = repository.findByUsername(TestHelper.makeUser().getUsername());
        UserProfile expected = TestHelper.makeUser();

        assertNotNull(actual);
        assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    void shouldNotFindMissingUsername() {
        UserProfile actual = repository.findByUsername("missingUsername");
        assertNull(actual);
    }

    @Test
    void findByEmail() {
        UserProfile actual = repository.findByEmail(TestHelper.makeUser().getEmail());
        UserProfile expected = TestHelper.makeUser();

        assertNotNull(actual);
        assertEquals(expected.getEmail(), actual.getEmail());
    }

    @Test
    void shouldNotFindMissingEmail() {
        UserProfile actual = repository.findByEmail("missingEmail");
        assertNull(actual);
    }

    @Test
    void findAll() {
        List<UserProfile> actual = repository.findAll();

        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @Test
    void findAllByCity() {
        List<UserProfile> actual = repository.findAllByCity("San Francisco");

        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @Test
    void shouldNotFindAllByCity() {
        List<UserProfile> actual = repository.findAllByCity("Doesnt Exist");

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }
}

@Nested
public class Create {
   @Test
   void create() {
       UserProfile toAdd = TestHelper.makeUser();
       toAdd.setUsername("testUsername");
       toAdd.setEmail("testEmail");
       toAdd.setUserId(0);

       UserProfile actual = repository.create(toAdd);

       assertEquals(4, actual.getUserId());

   }

   @Test
   void shouldNotCreateDuplicateUsername() {
       UserProfile toAdd = TestHelper.makeUser();
       toAdd.setUserId(0);

       assertThrows(DuplicateKeyException.class, () -> repository.create(toAdd));
   }

//   @Test
//   void update() {
//       UserProfile toUpdate = TestHelper.makeUser();
//       String before = toUpdate.getUsername();
//
//       toUpdate.setUsername("testUsername");
//       String after = toUpdate.getUsername();
//
//       boolean actual = repository.update(toUpdate);
//
//       assertTrue(actual);
//       assertEquals(before, "JoeSchmoe");
//       assertEquals(after, "testUsername");
//   }

   @Test
   void shouldNotUpdate() {
       UserProfile toUpdate = TestHelper.makeUser();
       toUpdate.setUserId(1000);
       toUpdate.setUsername("testUsername");

       boolean actual = repository.update(toUpdate);

       assertFalse(actual);
   }

   @Test
   void deleteById() {

         boolean actual = repository.deleteById(1);
         assertTrue(actual);

         UserProfile deleted = repository.findById(1);

         assertNull(deleted);
   }
}
}