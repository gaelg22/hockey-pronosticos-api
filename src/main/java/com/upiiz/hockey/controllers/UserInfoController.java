package com.upiiz.hockey.controllers;

import com.upiiz.hockey.UserInfo;
import com.upiiz.hockey.services.UserInfoService;
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
@RequestMapping("/api/userinfo")
@CrossOrigin
@Tag(
        name = "User Info Controller",
        description = "Manages all operations related to additional user information, including CRUD and lookup by user ID."
)
public class UserInfoController {

    @Autowired
    private UserInfoService service;

    @Operation(
            summary = "Get all user information records",
            description = "Retrieves a complete list of all user information stored in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of user information records")
    })
    @GetMapping
    public List<UserInfo> getAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Get user information by ID",
            description = "Retrieves a specific user information record by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User information found"),
            @ApiResponse(responseCode = "404", description = "User information not found")
    })
    @GetMapping("/{id}")
    public Optional<UserInfo> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Create a new user information record",
            description = "Creates a new record containing additional details for a user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User information successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public UserInfo create(@Valid @RequestBody UserInfo info) {
        return service.save(info);
    }

    @Operation(
            summary = "Update existing user information",
            description = "Updates an existing user information record based on its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User information successfully updated"),
            @ApiResponse(responseCode = "404", description = "User information not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public UserInfo update(@PathVariable Long id, @Valid @RequestBody UserInfo info) {
        info.setId(id);
        return service.save(info);
    }

    @Operation(
            summary = "Delete user information record",
            description = "Deletes a specific user information record using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User information successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User information not found")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(
            summary = "Get user information by user ID",
            description = "Retrieves user information associated with a specific application user ID. This is a one-to-one relationship."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user information by user ID"),
            @ApiResponse(responseCode = "404", description = "User information not found for the given user ID")
    })
    @GetMapping("/user/{userId}")
    public Optional<UserInfo> getByUserId(@PathVariable Long userId) {
        return service.findAll()
                .stream()
                .filter(i -> userId.equals(i.getUserId()))
                .findFirst();
    }
}
