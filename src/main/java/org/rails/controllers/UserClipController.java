package org.rails.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.rails.domain.Result;
import org.rails.domain.UserClipService;
import org.rails.models.UserClip;
import org.rails.models.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/clip")
public class UserClipController {

    private UserClipService service;

    private SecretSigningKey secretSigningKey;

    public UserClipController(UserClipService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public UserClip findById(@PathVariable int id) {
        return service.findById(id).getPayload();
    }

    @GetMapping("/user/{userId}")
    public List<UserClip> findAllClipsByUserId(@PathVariable int userId) {
        return service.findAllClipsByUserId(userId).getPayload();
    }

    @GetMapping("/followed/{userId}")
    public List<UserClip> findAllClipsByUsersFollowed(@PathVariable int userId) {
        return service.findAllClipsByFollowees(userId).getPayload();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserClip clip) {
        Result<UserClip> result = service.create(clip);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    private Integer getUserIdFromHeaders(Map<String, String> headers) {
        if (headers.get("authorization") == null) {
            return null;
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretSigningKey.getKey())
                    .build().parseClaimsJws(headers.get("authorization"));
            return (Integer) claims.getBody().get("userId");
        } catch (Exception e) {
            return null;
        }
    }

}
