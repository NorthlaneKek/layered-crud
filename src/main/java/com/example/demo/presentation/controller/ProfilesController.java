package com.example.demo.presentation.controller;

import com.example.demo.domain.EmailValidator;
import com.example.demo.domain.Profile;
import com.example.demo.domain.exception.EmailAlreadyExistsException;
import com.example.demo.domain.exception.InvalidEmailException;
import com.example.demo.domain.exception.ProfileNotFoundException;
import com.example.demo.domain.repository.ProfileRepository;
import com.example.demo.domain.repository.ServiceErrorRepository;
import com.example.demo.presentation.presenter.UserCreated;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@SuppressWarnings("unchecked")
@RequestMapping("/profiles")
@Api(value = "/profiles", description = "Взаимодействие с профилями")
public class ProfilesController {

    private final ProfileRepository repository;

    private final Gson gson = new GsonBuilder().create();

    public ProfilesController(ProfileRepository repository, ServiceErrorRepository errorRepository) {
        this.repository = repository;
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header"),
    })
    @ApiOperation(value = "Creates new user")
    @PostMapping("/set")
    public ResponseEntity<UserCreated> create(@RequestBody Profile profile) throws Throwable {
        if (repository.findOneByEmailIgnoreCase(profile.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        if (!EmailValidator.isValid(profile.getEmail())) {
            throw new InvalidEmailException();
        }
        repository.save(profile);
        return new ResponseEntity<>(new UserCreated(profile.getId()), HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header"),
    })
    @ApiOperation(value = "Returns last created user")
    @GetMapping("/last")
    public Profile last() throws ProfileNotFoundException {
        Optional<Profile> last = repository.findFirstByOrderByIdDesc();
        if (last.isEmpty()) {
            throw new ProfileNotFoundException();
        }
        return last.get();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header"),
    })
    @ApiOperation(value = "Returns all users in DB")
    @GetMapping
    public List<Profile> all() {
        return repository.findAll();
    }

    @ApiOperation(value = "Searches users by ID")
    @GetMapping("/{id}")
    public Profile getById(@PathVariable Long id) throws ProfileNotFoundException {
        return repository.findOneById(id).orElseThrow(ProfileNotFoundException::new);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header"),
    })
    @ApiOperation(value = "Searches users by email")
    @PostMapping("/get")
    public Profile getByEmail(@RequestBody String body) throws Throwable {
        String email = ((Map<String, String>) gson.fromJson(body, Map.class)).get("email");
        if (!EmailValidator.isValid(email)) {
            throw new InvalidEmailException();
        }
        Optional<Profile> profile = repository.findOneByEmailIgnoreCase(email);
        if (profile.isEmpty()) {
            throw new ProfileNotFoundException();
        }
        return profile.get();
    }

}
