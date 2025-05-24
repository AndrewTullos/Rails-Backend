package org.rails.domain;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.rails.data.UserProfileRepository;
import org.rails.models.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final int BCRYPT_COST = 12;


    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public Result<UserProfile> findById(int id) {
        Result<UserProfile> result = new Result<>();
        UserProfile userProfile = userProfileRepository.findById(id);
        if (userProfile == null) {
            result.addErrorMessage("User profile not found.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(userProfile);
        }
        return result;
    }

    public Result<UserProfile> findByUsername(String username){
        Result<UserProfile> result = new Result<>();
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        if (userProfile == null) {
            result.addErrorMessage("Username or password incorrect / found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(userProfile);
        }

        return result;
    }

    public Result<UserProfile> findByEmail(String email) {
        Result<UserProfile> result = new Result<>();
        UserProfile userEmail = userProfileRepository.findByEmail(email);

        if (userEmail == null) {
            result.addErrorMessage("User email not found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(userEmail);
        }
        return result;
    }

    public Result<List<UserProfile>> findAll() {
        Result<List<UserProfile>> result = new Result<>();
        List<UserProfile> userProfiles = userProfileRepository.findAll();

        if (userProfiles.isEmpty()) {
            result.addErrorMessage("No user profiles found.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(userProfiles);
        }
        return result;
    }

    public Result<List<UserProfile>> findAllByCity(String city) {
        Result<List<UserProfile>> result = new Result<>();
        List<UserProfile> userProfiles = userProfileRepository.findAllByCity(city);

        if (userProfiles.isEmpty()) {
            result.addErrorMessage("No user profiles found in city.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(userProfiles);
        }
        return result;
    }


    public Result<UserProfile> create(UserProfile userProfile) {
        Result<UserProfile> result = validate(userProfile);
        if (!result.isSuccess()) {
            return result;
        }
        if (result.isSuccess()) {
            String hashedPassword = BCrypt.withDefaults().hashToString(BCRYPT_COST, userProfile.getPassword().toCharArray());

            userProfile.setPassword(hashedPassword);
            UserProfile createdUser = userProfileRepository.create(userProfile);
            result.setPayload(createdUser);
        }

        return result;

    }

    public Result<UserProfile> update(UserProfile userProfile) {
        Result<UserProfile> result = validate(userProfile);

        if (userProfile != null && userProfile.getUserId() <= 0) {
            result.addErrorMessage("SolarPanel `id` should be set.", ResultType.INVALID);
        }

        if (result.isSuccess()) {
            boolean success = userProfileRepository.update(userProfile);
            // If the solar panel couldn't be found and updated:
            if (!success) {
                result.addErrorMessage("User profile not found.", ResultType.NOT_FOUND);
            }
        }

        return result;
    }

    public Result<UserProfile> deleteById(int id) {
        Result<UserProfile> result = new Result<>();
        boolean success = userProfileRepository.deleteById(id);
        if (!success) {
            result.addErrorMessage(String.format("Could not delete user profile.", id), ResultType.NOT_FOUND);
        }
        return result;
    }

//

    private Result<UserProfile> validate(UserProfile userProfile) {
        Result<UserProfile>  result = new Result<>();

        if (userProfile.getUsername() == null || userProfile.getUsername().isBlank()) {
            result.addErrorMessage("Username cannot be blank.", ResultType.INVALID);
            return result;
        }

        if (userProfileRepository.findByUsername(userProfile.getUsername()) != null) {
            result.addErrorMessage("Username is already taken.", ResultType.INVALID);
        }

        if (userProfile.getEmail() == null || userProfile.getEmail().isBlank()) {
            result.addErrorMessage("Email cannot be blank.", ResultType.INVALID);
        }

        if (userProfileRepository.findByEmail(userProfile.getEmail()) != null) {
            result.addErrorMessage("Email is already taken.", ResultType.INVALID);
        }

        // regex email validation
        if (!userProfile.getPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[#?!@$%^&*-]).{8,}$")) {
            result.addErrorMessage("Password does not meet complexity requirements.", ResultType.INVALID);
        }


        if (userProfile.getPassword() == null || userProfile.getPassword().isBlank()) {
            result.addErrorMessage("Password cannot be blank.", ResultType.INVALID);
        }

        return result;
    }

}
