package com.upiiz.hockey.controllers;

import com.upiiz.hockey.GameScores;
import com.upiiz.hockey.services.GameScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

// Swagger/OpenAPI imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/gamescores")
@CrossOrigin
@Tag(name = "Game Scores", description = "CRUD operations and queries for game scores")
public class GameScoresController {

    @Autowired
    private GameScoresService service;

    // GET ALL
    @Operation(
            summary = "Get all game scores",
            description = "Retrieve a list of all game score records.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of game scores retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GameScores.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public List<GameScores> getAll() {
        return service.findAll();
    }

    // GET BY ID
    @Operation(
            summary = "Get game score by ID",
            description = "Retrieve a single game score record by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game score found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GameScores.class))),
                    @ApiResponse(responseCode = "404", description = "Game score not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public Optional<GameScores> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // CREATE
    @Operation(
            summary = "Create a new game score",
            description = "Create a new game score record. The 'id' field should not be provided (read-only).",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Game score created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GameScores.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete data"),
                    @ApiResponse(responseCode = "409", description = "Conflict: duplicate or constraint violation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    public GameScores create(@Valid @RequestBody GameScores scores) {
        return service.save(scores);
    }

    // UPDATE
    @Operation(
            summary = "Update an existing game score",
            description = "Update a game score record by ID. Provide the updated fields in the request body.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game score updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GameScores.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "404", description = "Game score not found"),
                    @ApiResponse(responseCode = "409", description = "Conflict: duplicate or constraint violation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public GameScores update(@PathVariable Long id, @Valid @RequestBody GameScores scores) {
        scores.setId(id);
        return service.save(scores);
    }

    // DELETE
    @Operation(
            summary = "Delete a game score",
            description = "Delete a game score record by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Game score deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Game score not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // FILTER: get scores by gameId
    @Operation(
            summary = "Get scores by game ID",
            description = "Retrieve all score records that belong to a specific hockey game.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Scores for the game retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GameScores.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid game ID"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/game/{gameId}")
    public List<GameScores> getByGame(@PathVariable Long gameId) {
        return service.findAll().stream().filter(s -> gameId.equals(s.getHockeyGameId())).toList();
    }
}
