package org.rails.data.mappers;

import org.rails.models.Follow;
import org.rails.models.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowMapper implements RowMapper<Follow> {
    @Override
    public Follow mapRow(ResultSet rs, int rowNum) throws SQLException {
        Follow follow = new Follow();
        follow.setId(rs.getInt("follow_id"));
        follow.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        UserProfile follower = new UserProfile();
        follower.setUserId(rs.getInt("follower_id"));
        follower.setUsername(rs.getString("follower_username"));
        follower.setEmail(rs.getString("follower_email"));
        follower.setPassword(rs.getString("follower_password"));
        follower.setProfilePicture(rs.getString("follower_profile_picture"));
        follower.setFirstName(rs.getString("follower_first_name"));
        follower.setLastName(rs.getString("follower_last_name"));
        follower.setCity(rs.getString("follower_city"));
        follower.setState(rs.getString("follower_state"));
        follower.setPostalCode(rs.getString("follower_postal_code"));
        follower.setCreatedAt(rs.getTimestamp("follower_created_at").toLocalDateTime());
        follow.setFollower(follower);

        UserProfile followee = new UserProfile();
        followee.setUserId(rs.getInt("followee_id"));
        followee.setUsername(rs.getString("followee_username"));
        followee.setEmail(rs.getString("followee_email"));
        followee.setPassword(rs.getString("followee_password"));
        followee.setProfilePicture(rs.getString("followee_profile_picture"));
        followee.setFirstName(rs.getString("followee_first_name"));
        followee.setLastName(rs.getString("followee_last_name"));
        followee.setCity(rs.getString("followee_city"));
        followee.setState(rs.getString("followee_state"));
        followee.setPostalCode(rs.getString("followee_postal_code"));
        followee.setCreatedAt(rs.getTimestamp("followee_created_at").toLocalDateTime());
        follow.setFollowing(followee);

        return follow;
    }
}