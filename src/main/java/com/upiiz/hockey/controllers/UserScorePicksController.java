package com.upiiz.hockey.controllers;

import com.upiiz.hockey.UserScorePicks;
import com.upiiz.hockey.services.UserScorePicksService;
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
@RequestMapping("/api/scorepicks")
@CrossOrigin
@Tag(
        name = "User Score Picks Controller",
        description = "Handles CRUD operations and queries related to user score predictions for hockey games."
)
public class UserScorePicksController {

    @Autowired
    private UserScorePicksService service;

    @Operation(
            summary = "Get all score picks",
            description = "Retrieves a complete list of all user score predictions registered in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all score picks")
    })
    @GetMapping
    public List<UserScorePicks> getAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Get score pick by ID",
            description = "Retrieves a specific score pick by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Score pick found"),
            @ApiResponse(responseCode = "404", description = "Score pick not found")
    })
    @GetMapping("/{id}")
    public Optional<UserScorePicks> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Create a new score pick",
            description = "Creates a new user score prediction record for a specific hockey game."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Score pick successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid or incomplete data provided")
    })
    @PostMapping
    public UserScorePicks create(@Valid @RequestBody UserScorePicks pick) {
        return service.save(pick);
    }

    @Operation(
            summary = "Update existing score pick",
            description = "Updates an existing score prediction record identified by its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Score pick successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Score pick not found")
    })
    @PutMapping("/{id}")
    public UserScorePicks update(@PathVariable Long id, @Valid @RequestBody UserScorePicks pick) {
        pick.setId(id);
        return service.save(pick);
    }

    @Operation(
            summary = "Delete score pick by ID",
            description = "Deletes a specific score prediction record from the system by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Score pick successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Score pick not found")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(
            summary = "Get score picks by user ID",
            description = "Retrieves all score prediction records associated with a specific user ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved score picks by user ID"),
            @ApiResponse(responseCode = "404", description = "No score picks found for the specified user ID")
    })
    @GetMapping("/user/{userId}")
    public List<UserScorePicks> getByUser(@PathVariable Long userId) {
        return service.findAll()
                .stream()
                .filter(p -> userId.equals(p.getUserId()))
                .toList();
    }

    @Operation(
            summary = "Get score picks by game ID",
            description = "Retrieves all user score predictions related to a specific hockey game."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved score picks by game ID"),
            @ApiResponse(responseCode = "404", description = "No score picks found for the specified game ID")
    })
    @GetMapping("/game/{gameId}")
    public List<UserScorePicks> getByGame(@PathVariable Long gameId) {
        return service.findAll()
                .stream()
                .filter(p -> gameId.equals(p.getHockeyGameId()))
                .toList();
    }
}
