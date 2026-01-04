package com.upiiz.hockey.controllers;

import com.upiiz.hockey.RoundStats;
import com.upiiz.hockey.services.RoundStatsService;
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
@RequestMapping("/api/roundstats")
@CrossOrigin
@Tag(name = "Round Stats Controller", description = "Handles all CRUD operations and queries related to playoff round statistics.")
public class RoundStatsController {

    @Autowired
    private RoundStatsService service;

    @Operation(
            summary = "Get all round statistics",
            description = "Retrieves a list of all playoff round statistics available in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of round statistics")
    })
    @GetMapping
    public List<RoundStats> getAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Get round statistics by ID",
            description = "Retrieves specific round statistics using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Round statistics found"),
            @ApiResponse(responseCode = "404", description = "Round statistics not found")
    })
    @GetMapping("/{id}")
    public Optional<RoundStats> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Create new round statistics",
            description = "Creates a new record of playoff round statistics using the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Round statistics successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public RoundStats create(@Valid @RequestBody RoundStats stats) {
        return service.save(stats);
    }

    @Operation(
            summary = "Update existing round statistics",
            description = "Updates the details of existing round statistics using its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Round statistics successfully updated"),
            @ApiResponse(responseCode = "404", description = "Round statistics not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public RoundStats update(@PathVariable Long id, @Valid @RequestBody RoundStats stats) {
        stats.setId(id);
        return service.save(stats);
    }

    @Operation(
            summary = "Delete round statistics",
            description = "Deletes a record of round statistics using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Round statistics successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Round statistics not found")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(
            summary = "Get statistics by playoff round ID",
            description = "Retrieves all round statistics associated with a specific playoff round ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved statistics for the given playoff round")
    })
    @GetMapping("/round/{roundId}")
    public List<RoundStats> getByRound(@PathVariable Long roundId) {
        return service.findAll().stream().filter(s -> roundId.equals(s.getPlayoffRoundId())).toList();
    }
}
