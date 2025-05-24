package org.rails.data.mappers;


import org.rails.models.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileMapper implements RowMapper<UserProfile> {
    @Override
    public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserProfile profile = new UserProfile();
        profile.setUserId(rs.getInt("user_id"));
        profile.setUsername(rs.getString("username"));
        profile.setEmail(rs.getString("email"));
        profile.setPassword(rs.getString("password"));
        profile.setProfilePicture(rs.getString("profile_picture"));
        profile.setFirstName(rs.getString("first_name"));
        profile.setLastName(rs.getString("last_name"));
        profile.setCity(rs.getString("city"));
        profile.setState(rs.getString("state"));
        profile.setPostalCode(rs.getString("postal_code"));
        profile.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return profile;

    }
}
