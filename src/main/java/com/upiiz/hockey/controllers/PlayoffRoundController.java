package com.upiiz.hockey.controllers;

import com.upiiz.hockey.PlayoffRound;
import com.upiiz.hockey.services.PlayoffRoundService;
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
@RequestMapping("/api/playoffrounds")
@CrossOrigin
@Tag(name = "Playoff Round Controller", description = "Manages the CRUD operations for playoff rounds, including creation, retrieval, update, and deletion.")
public class PlayoffRoundController {

    @Autowired
    private PlayoffRoundService service;

    @Operation(
            summary = "Get all playoff rounds",
            description = "Retrieves a list of all playoff rounds available in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of playoff rounds")
    })
    @GetMapping
    public List<PlayoffRound> getAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Get playoff round by ID",
            description = "Retrieves a specific playoff round by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Playoff round found"),
            @ApiResponse(responseCode = "404", description = "Playoff round not found")
    })
    @GetMapping("/{id}")
    public Optional<PlayoffRound> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Create a new playoff round",
            description = "Creates and saves a new playoff round with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Playoff round successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public PlayoffRound create(@Valid @RequestBody PlayoffRound round) {
        return service.save(round);
    }

    @Operation(
            summary = "Update an existing playoff round",
            description = "Updates the details of an existing playoff round by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Playoff round successfully updated"),
            @ApiResponse(responseCode = "404", description = "Playoff round not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public PlayoffRound update(@PathVariable Long id, @Valid @RequestBody PlayoffRound round) {
        round.setId(id);
        return service.save(round);
    }

    @Operation(
            summary = "Delete a playoff round",
            description = "Deletes a playoff round by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Playoff round successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Playoff round not found")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
