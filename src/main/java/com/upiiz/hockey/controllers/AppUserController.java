package com.upiiz.hockey.controllers;

import com.upiiz.hockey.AppUser;
import com.upiiz.hockey.services.AppUserService;
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
@RequestMapping("/api/users")
@CrossOrigin
@Tag(name = "Users", description = "CRUD operations for managing application users")
public class AppUserController {

    @Autowired
    private AppUserService service;


    @Operation(
            summary = "Get all users",
            description = "Retrieve a list of all users registered in the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of users successfully retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppUser.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public List<AppUser> getAll() {
        return service.findAll();
    }


    @Operation(
            summary = "Get user by ID",
            description = "Retrieve detailed information about a specific user by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User found successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppUser.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public Optional<AppUser> getById(@PathVariable Long id) {
        return service.findById(id);
    }


    @Operation(
            summary = "Create a new user",
            description = "Register a new user in the system. Requires valid input data.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppUser.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete data"),
                    @ApiResponse(responseCode = "409", description = "Conflict: duplicate entry"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    public AppUser create(@Valid @RequestBody AppUser user) {
        return service.save(user);
    }


    @Operation(
            summary = "Update user information",
            description = "Update an existing user by specifying its ID and providing valid data.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppUser.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid data or missing fields"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "409", description = "Conflict: duplicate entry"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public AppUser update(@PathVariable Long id, @Valid @RequestBody AppUser user) {
        user.setId(id);
        return service.save(user);
    }


    @Operation(
            summary = "Delete a user",
            description = "Remove an existing user from the system by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
