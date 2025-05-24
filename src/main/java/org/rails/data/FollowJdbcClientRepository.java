package org.rails.data;

import org.rails.data.mappers.FollowMapper;
import org.rails.models.Follow;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FollowJdbcClientRepository implements FollowRepository {

    JdbcClient client;

    public FollowJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public List<Follow> findAllFollowersByUserId(int userId) {
        final String sql = """
            SELECT
                follow.id AS follow_id,
                follow.follower_id,
                follow.followee_id,
                follow.created_at,
                u1.id AS follower_id,
                u1.username AS follower_username,
                u1.email AS follower_email,
                u1.password AS follower_password,
                u1.profile_picture AS follower_profile_picture,
                u1.first_name AS follower_first_name,
                u1.last_name AS follower_last_name,
                u1.city AS follower_city,
                u1.state AS follower_state,
                u1.postal_code AS follower_postal_code,
                u1.created_at AS follower_created_at,
                u2.id AS followee_id,
                u2.username AS followee_username,
                u2.email AS followee_email,
                u2.password AS followee_password,
                u2.profile_picture AS followee_profile_picture,
                u2.first_name AS followee_first_name,
                u2.last_name AS followee_last_name,
                u2.city AS followee_city,
                u2.state AS followee_state,
                u2.postal_code AS followee_postal_code,
                u2.created_at AS followee_created_at
            FROM follow
            JOIN user_profile u1 ON follow.follower_id = u1.id
            JOIN user_profile u2 ON follow.followee_id = u2.id
            WHERE follow.followee_id = ?;
            """;

        return client.sql(sql)
                .param(userId)
                .query(new FollowMapper())
                .list();
    }


    @Override
    public List<Follow> findAllFolloweesByFollowerId(int userId) {
        final String sql = """
            SELECT
                follow.id AS follow_id,
                follow.follower_id,
                follow.followee_id,
                follow.created_at,
                u1.id AS follower_id,
                u1.username AS follower_username,
                u1.email AS follower_email,
                u1.password AS follower_password,
                u1.profile_picture AS follower_profile_picture,
                u1.first_name AS follower_first_name,
                u1.last_name AS follower_last_name,
                u1.city AS follower_city,
                u1.state AS follower_state,
                u1.postal_code AS follower_postal_code,
                u1.created_at AS follower_created_at,
                u2.id AS followee_id,
                u2.username AS followee_username,
                u2.email AS followee_email,
                u2.password AS followee_password,
                u2.profile_picture AS followee_profile_picture,
                u2.first_name AS followee_first_name,
                u2.last_name AS followee_last_name,
                u2.city AS followee_city,
                u2.state AS followee_state,
                u2.postal_code AS followee_postal_code,
                u2.created_at AS followee_created_at
            FROM follow
            JOIN user_profile u1 ON follow.follower_id = u1.id
            JOIN user_profile u2 ON follow.followee_id = u2.id
            WHERE follow.follower_id = ?;
            """;

        return client.sql(sql)
                .param(userId)
                .query(new FollowMapper())
                .list();
    }

    @Override
    public Follow create(Follow follow) {
        LocalDateTime currentTime = LocalDateTime.now();
        follow.setCreatedAt(currentTime);
        final String sql = """
            INSERT INTO follow (follower_id, followee_id, created_at)
            VALUES (:follower_id, :followee_id, :created_at);
            """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("follower_id", follow.getFollower().getUserId())
                .param("followee_id", follow.getFollowing().getUserId())
                .param("created_at", follow.getCreatedAt())
                .update(keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        follow.setId(keyHolder.getKey().intValue());
        return follow;
    }

    @Override
    public boolean deleteById(int followId) {
        final String sql = "DELETE FROM follow WHERE id = ?;";
        return client.sql(sql)
                .param(followId)
                .update() > 0;
    }
}
