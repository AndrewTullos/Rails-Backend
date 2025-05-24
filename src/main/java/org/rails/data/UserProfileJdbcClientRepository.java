package org.rails.data;

import org.rails.data.mappers.UserProfileMapper;
import org.rails.models.UserProfile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserProfileJdbcClientRepository implements UserProfileRepository {

    JdbcClient client;

    public UserProfileJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public UserProfile findById(int userId) {
        final String sql = """
                SELECT
                    id AS user_id,
                    username,
                    email,
                    password,
                    profile_picture,
                    first_name,
                    last_name,
                    city,
                    state,
                    postal_code,
                    created_at
                FROM `user_profile` WHERE id = ?;
                """;
        return client.sql(sql)
                .param(userId)
                .query(new UserProfileMapper())
                .optional().orElse(null);
    }

    @Override
    public List<UserProfile> findAll() {
        final String sql = """
                SELECT
                    id AS user_id,
                    username,
                    email,
                    password,
                    profile_picture,
                    first_name,
                    last_name,
                    city,
                    state,
                    postal_code,
                    created_at
                FROM `user_profile`;
                """;
        return client.sql(sql)
                .query(new UserProfileMapper())
                .list();
    }

    @Override
    public List<UserProfile> findAllByCity(String city) {
        final String sql = """
                SELECT
                    id AS user_id,
                    username,
                    email,
                    password,
                    profile_picture,
                    first_name,
                    last_name,
                    city,
                    state,
                    postal_code,
                    created_at
                FROM `user_profile` WHERE city = ?;
                """;
        return client.sql(sql)
                .param(city)
                .query(new UserProfileMapper())
                .list();
    }

    @Override
    public UserProfile findByUsername(String username) {
        final String sql = """
                SELECT
                    id AS user_id,
                    username,
                    email,
                    password,
                    profile_picture,
                    first_name,
                    last_name,
                    city,
                    state,
                    postal_code,
                    created_at
                FROM `user_profile` WHERE username = ?;
                """;

        return client.sql(sql)
                .param(username)
                .query(new UserProfileMapper())
                .optional().orElse(null);
    }

    @Override
    public UserProfile findByEmail(String email) {
        final String sql = """
                SELECT
                    id AS user_id,
                    username,
                    email,
                    password,
                    profile_picture,
                    first_name,
                    last_name,
                    city,
                    state,
                    postal_code,
                    created_at
                FROM `user_profile` WHERE email = ?;
                """;

        return client.sql(sql)
                .param(email)
                .query(new UserProfileMapper())
                .optional().orElse(null);
    }

    @Override
    public UserProfile create(UserProfile userProfile) {
        LocalDateTime currentTime = LocalDateTime.now();
        userProfile.setCreatedAt(currentTime);
        final String sql = """
                INSERT INTO `user_profile` (
                    username,
                    email,
                    password,
                    profile_picture,
                    first_name,
                    last_name,
                    city,
                    state,
                    postal_code,
                    created_at
                ) VALUES (
                    :username,
                    :email,
                    :password,
                    :profile_picture,
                    :first_name,
                    :last_name,
                    :city,
                    :state,
                    :postal_code,
                    :created_at
                );
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("username", userProfile.getUsername())
                .param("email", userProfile.getEmail())
                .param("password", userProfile.getPassword())
                .param("profile_picture", userProfile.getProfilePicture())
                .param("first_name", userProfile.getFirstName())
                .param("last_name", userProfile.getLastName())
                .param("city", userProfile.getCity())
                .param("state", userProfile.getState())
                .param("postal_code", userProfile.getPostalCode())
                .param("created_at", userProfile.setCreatedAt(currentTime))
                .update(keyHolder, "id");

        if (rowsAffected <= 0) {
            return null;
        }

        userProfile.setUserId(keyHolder.getKey().intValue());
        return userProfile;
    }

    @Override
    public boolean update(UserProfile userProfile) {
        final String sql = """
                UPDATE `user_profile` SET
                    username = :username,
                    email = :email,
                    password = :password,
                    profile_picture = :profile_picture,
                    first_name = :first_name,
                    last_name = :last_name,
                    city = :city,
                    state = :state,
                    postal_code = :postal_code
                WHERE id = :user_id;
                """;

        return client.sql(sql)
                .param("user_id", userProfile.getUserId())
                .param("username", userProfile.getUsername())
                .param("email", userProfile.getEmail())
                .param("password", userProfile.getPassword())
                .param("profile_picture", userProfile.getProfilePicture())
                .param("first_name", userProfile.getFirstName())
                .param("last_name", userProfile.getLastName())
                .param("city", userProfile.getCity())
                .param("state", userProfile.getState())
                .param("postal_code", userProfile.getPostalCode())
                .update() > 0;
    }

    @Override
    public boolean deleteById(int userId) {
        int rowsAffected = client.sql("DELETE FROM `user_profile` WHERE id = ?")
                .param(userId)
                .update();
        return rowsAffected > 0;
    }
}