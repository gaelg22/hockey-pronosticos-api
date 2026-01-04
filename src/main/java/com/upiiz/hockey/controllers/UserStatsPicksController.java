package com.upiiz.hockey.controllers;

import com.upiiz.hockey.UserStatsPicks;
import com.upiiz.hockey.services.UserStatsPicksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statspicks")
@CrossOrigin
@Tag(
        name = "User Stats Picks Controller",
        description = "Manages CRUD operations and queries related to user statistical predictions for hockey players or teams."
)
public class UserStatsPicksController {

    @Autowired
    private UserStatsPicksService service;

    @Operation(
            summary = "Get all stats picks",
            description = "Retrieves a list of all user statistical prediction records stored in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all stats picks")
    })
    @GetMapping
    public List<UserStatsPicks> getAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Get stats pick by ID",
            description = "Fetches a specific stats pick record using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stats pick found"),
            @ApiResponse(responseCode = "404", description = "Stats pick not found")
    })
    @GetMapping("/{id}")
    public Optional<UserStatsPicks> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Create a new stats pick",
            description = "Creates a new user statistical prediction record associated with hockey statistics."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Stats pick successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid or incomplete data provided")
    })
    @PostMapping
    public UserStatsPicks create(@Valid @RequestBody UserStatsPicks pick) {
        return service.save(pick);
    }

    @Operation(
            summary = "Update an existing stats pick",
            description = "Updates an existing statistical prediction record identified by its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stats pick successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "404", description = "Stats pick not found")
    })
    @PutMapping("/{id}")
    public UserStatsPicks update(@PathVariable Long id, @Valid @RequestBody UserStatsPicks pick) {
        pick.setId(id);
        return service.save(pick);
    }

    @Operation(
            summary = "Delete stats pick by ID",
            description = "Deletes a specific user stats prediction record from the system using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Stats pick successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Stats pick not found")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(
            summary = "Get stats picks by user ID",
            description = "Retrieves all statistical prediction records associated with a specific user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stats picks by user ID"),
            @ApiResponse(responseCode = "404", description = "No stats picks found for the specified user ID")
    })
    @GetMapping("/user/{userId}")
    public List<UserStatsPicks> getByUser(@PathVariable Long userId) {
        return service.findAll()
                .stream()
                .filter(p -> userId.equals(p.getUserId()))
                .toList();
    }
}
