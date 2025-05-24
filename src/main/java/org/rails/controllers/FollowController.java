package org.rails.controllers;

import org.rails.domain.FollowService;
import org.rails.domain.Result;
import org.rails.models.Follow;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private FollowService service;

    public FollowController(FollowService service) {
        this.service = service;
    }

    @GetMapping("/followers/{userId}")
    public List<Follow> findAllFollowersByUserId(@PathVariable int userId) {
        return service.findAllFollowersByUserId(userId).getPayload();
    }

    @GetMapping("/following/{userId}")
    public List<Follow> findAllFolloweesByUserId(@PathVariable int userId) {
        return service.findAllFolloweesByFollowerId(userId).getPayload();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Follow follow) {
        Result<Follow> result = service.create(follow);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{followId}")
    public boolean deleteById(@PathVariable int followId) {
        return service.deleteById(followId).getPayload();
    }
}
