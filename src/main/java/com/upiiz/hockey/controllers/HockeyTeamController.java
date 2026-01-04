package com.upiiz.hockey.controllers;

import com.upiiz.hockey.HockeyTeam;
import com.upiiz.hockey.services.HockeyTeamService;
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
@RequestMapping("/api/teams")
@CrossOrigin
@Tag(name = "Hockey Teams", description = "CRUD operations and data retrieval for hockey teams")
public class HockeyTeamController {

    @Autowired
    private HockeyTeamService service;

    // GET ALL
    @Operation(
            summary = "Get all hockey teams",
            description = "Retrieve a list of all registered hockey teams.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of teams retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyTeam.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public List<HockeyTeam> getAll() {
        return service.findAll();
    }

    // GET BY ID
    @Operation(
            summary = "Get hockey team by ID",
            description = "Retrieve detailed information of a specific hockey team by its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Team found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyTeam.class))),
                    @ApiResponse(responseCode = "404", description = "Team not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public Optional<HockeyTeam> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // CREATE
    @Operation(
            summary = "Create a new hockey team",
            description = "Add a new hockey team record. The 'id' field should not be provided (read-only).",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Team created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyTeam.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete data"),
                    @ApiResponse(responseCode = "409", description = "Conflict or duplicate entry"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    public HockeyTeam create(@Valid @RequestBody HockeyTeam team) {
        return service.save(team);
    }

    // UPDATE
    @Operation(
            summary = "Update an existing hockey team",
            description = "Modify an existing hockey team record by providing its ID and updated data.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Team updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HockeyTeam.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "404", description = "Team not found"),
                    @ApiResponse(responseCode = "409", description = "Conflict or duplicate entry"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public HockeyTeam update(@PathVariable Long id, @Valid @RequestBody HockeyTeam team) {
        team.setId(id);
        return service.save(team);
    }

    // DELETE
    @Operation(
            summary = "Delete a hockey team",
            description = "Delete a hockey team record by its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Team deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Team not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
