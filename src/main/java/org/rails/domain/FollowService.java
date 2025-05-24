package org.rails.domain;

import org.rails.data.FollowRepository;
import org.rails.models.Follow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public Result<List<Follow>> findAllFollowersByUserId(int userId) {
        Result<List<Follow>> result = new Result<>();
        List<Follow> followers = followRepository.findAllFollowersByUserId(userId);
        if (followers == null) {
            result.addErrorMessage("Followers not found.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(followers);
        }
        return result;
    }

    public Result<List<Follow>> findAllFolloweesByFollowerId(int userId) {
        Result<List<Follow>> result = new Result<>();
        List<Follow> followees = followRepository.findAllFolloweesByFollowerId(userId);

        if (followees == null) {
            result.addErrorMessage("Followees not found.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(followees);
        }
        return result;
    }

    public Result<Follow> create(Follow follow) {
        Result<Follow> result = validate(follow);
        if (!result.isSuccess()) {
            return result;
        }
        result.setPayload(followRepository.create(follow));
        return result;
    }

    public Result<Boolean> deleteById(int followId) {
        Result<Boolean> result = new Result<>();
        result.setPayload(followRepository.deleteById(followId));
        return result;
    }

    private Result<Follow> validate(Follow follow) {
        Result<Follow> result = new Result<>();
        if (follow == null) {
            result.addErrorMessage("Follow cannot be null.", ResultType.INVALID);
            return result;
        }

        if (follow.getFollower().getUserId() <= 0) {
            result.addErrorMessage("Follower ID is required.", ResultType.INVALID);
        }

        if (follow.getFollowing().getUserId() <= 0) {
            result.addErrorMessage("Followee ID is required.", ResultType.INVALID);
        }

        if (follow.getFollower().getUserId() == follow.getFollowing().getUserId()) {
            result.addErrorMessage("Chill bro, you cant follow yourself.", ResultType.INVALID);
        }

        if (findFollowRelationship(follow.getFollower().getUserId(), follow.getFollowing().getUserId()) != null) {
            result.addErrorMessage("You are already following this user.", ResultType.INVALID);
        }

        return result;
    }

    private Follow findFollowRelationship(int followerId, int followingId) {
        List<Follow> follows = followRepository.findAllFollowersByUserId(followingId);

        for (Follow follow : follows) {
            if (follow.getFollowing().getUserId() == followingId) {
                return follow;
            }
        }

        return null;
    }

}
