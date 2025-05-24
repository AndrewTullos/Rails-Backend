package org.rails.data;

import org.rails.models.Follow;

import java.util.List;

public interface FollowRepository {

    public List<Follow> findAllFollowersByUserId(int userId);

    public List<Follow> findAllFolloweesByFollowerId(int userId);

    public Follow create(Follow follow);

    public boolean deleteById(int followId);

}
