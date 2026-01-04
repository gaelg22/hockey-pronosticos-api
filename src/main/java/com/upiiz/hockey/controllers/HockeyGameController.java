package com.upiiz.hockey.controllers;

import com.upiiz.hockey.HockeyGame;
import com.upiiz.hockey.services.HockeyGameService;
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
@RequestMapping("/api/games")
@CrossOrigin
@Tag(name = "Hockey Games", description = "CRUD operations and queries for hockey games")
public class HockeyGameController {

    @Autowired
    private HockeyGameService service;

    // GET ALL
    @Operation(
            summary = "Get all hockey games",
            description = "Retrieve a list of all registered hockey games.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of games retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyGame.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public List<HockeyGame> getAll() {
        return service.findAll();
    }

    // GET BY ID
    @Operation(
            summary = "Get hockey game by ID",
            description = "Retrieve a specific hockey game by its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyGame.class))),
                    @ApiResponse(responseCode = "404", description = "Game not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public Optional<HockeyGame> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // CREATE
    @Operation(
            summary = "Create a new hockey game",
            description = "Add a new hockey game record. The 'id' field should not be provided (read-only).",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Game created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyGame.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete data"),
                    @ApiResponse(responseCode = "409", description = "Conflict or duplicate entry"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    public HockeyGame create(@Valid @RequestBody HockeyGame game) {
        return service.save(game);
    }

    // UPDATE
    @Operation(
            summary = "Update an existing hockey game",
            description = "Modify an existing hockey game record by providing its ID and the new data.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Game updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyGame.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "404", description = "Game not found"),
                    @ApiResponse(responseCode = "409", description = "Conflict or duplicate entry"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public HockeyGame update(@PathVariable Long id, @Valid @RequestBody HockeyGame game) {
        game.setId(id);
        return service.save(game);
    }

    // DELETE
    @Operation(
            summary = "Delete a hockey game",
            description = "Delete a hockey game record by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Game deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Game not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // FILTER BY ROUND
    @Operation(
            summary = "Find games by playoff round",
            description = "Retrieve all hockey games that belong to a specific playoff round using the round ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of games in the specified round retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyGame.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid round ID"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/round/{roundId}")
    public List<HockeyGame> findByRound(@PathVariable Long roundId) {
        return service.findAll().stream()
                .filter(g -> roundId.equals(g.getRoundId()))
                .toList();
    }
}
