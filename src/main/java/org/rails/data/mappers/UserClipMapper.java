package org.rails.data.mappers;

import org.rails.models.UserClip;
import org.rails.models.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserClipMapper implements RowMapper<UserClip> {
    @Override
    public UserClip mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserClip userClip = new UserClip();
        userClip.setClipId(rs.getInt("clip_id"));
        userClip.setMediaUrl(rs.getString("media_url"));
        userClip.setCaption(rs.getString("caption"));
        userClip.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        UserProfileMapper userMapper = new UserProfileMapper();
        UserProfile user = userMapper.mapRow(rs, rowNum);
        userClip.setUserProfile(user);

        return userClip;
    }
}