package com.upiiz.hockey.controllers;

import com.upiiz.hockey.HockeyTeamPlayer;
import com.upiiz.hockey.services.HockeyTeamPlayerService;
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
@RequestMapping("/api/teamplayers")
@CrossOrigin
@Tag(name = "Hockey Team Players", description = "CRUD operations and filters for hockey team players")
public class HockeyTeamPlayerController {

    @Autowired
    private HockeyTeamPlayerService service;

    // GET ALL
    @Operation(
            summary = "Get all hockey team players",
            description = "Retrieve a list of all registered players across all hockey teams.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of players retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyTeamPlayer.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public List<HockeyTeamPlayer> getAll() {
        return service.findAll();
    }

    // GET BY ID
    @Operation(
            summary = "Get hockey team player by ID",
            description = "Retrieve detailed information of a specific hockey player by their unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Player found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyTeamPlayer.class))),
                    @ApiResponse(responseCode = "404", description = "Player not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public Optional<HockeyTeamPlayer> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // CREATE
    @Operation(
            summary = "Create a new hockey team player",
            description = "Add a new player to a hockey team. The 'id' field should not be provided (read-only).",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Player created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyTeamPlayer.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete data"),
                    @ApiResponse(responseCode = "409", description = "Conflict or duplicate entry"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    public HockeyTeamPlayer create(@Valid @RequestBody HockeyTeamPlayer player) {
        return service.save(player);
    }

    // UPDATE
    @Operation(
            summary = "Update an existing hockey team player",
            description = "Modify a player's information by providing their ID and the updated data.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Player updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyTeamPlayer.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "404", description = "Player not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public HockeyTeamPlayer update(@PathVariable Long id, @Valid @RequestBody HockeyTeamPlayer player) {
        player.setId(id);
        return service.save(player);
    }

    // DELETE
    @Operation(
            summary = "Delete a hockey team player",
            description = "Remove a hockey player from the database by their unique ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Player deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Player not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // GET BY TEAM ID
    @Operation(
            summary = "Get all players by team ID",
            description = "Retrieve all players who belong to a specific hockey team using its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of players for the team retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyTeamPlayer.class))),
                    @ApiResponse(responseCode = "404", description = "No players found for the given team ID"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/team/{teamId}")
    public List<HockeyTeamPlayer> getByTeam(@PathVariable Long teamId) {
        return service.findAll().stream().filter(p -> teamId.equals(p.getHockeyTeamId())).toList();
    }
}
