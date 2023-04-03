package com.example.warehouseapp.controllers;

import com.example.warehouseapp.model.Socks;
import com.example.warehouseapp.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/socks")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @Operation(summary = "Adds new socks to the DB or fixes the income of existing ones at the warehouse, " +
            "increasing the quantity.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Added socks",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Socks.class))}),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Income successfully added to the database",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Socks.class))}
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "Query parameters are missing or are not in the correct format"
                    ),
                    @ApiResponse(responseCode = "500",
                            description = "An error occurred that is not dependent on the caller"
                    )
            })
    @PostMapping("/income")
    public ResponseEntity<Socks> addSocks(@RequestBody Socks socks) {
        Socks addedSocks = socksService.addSocks(socks);
        if (addedSocks == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(addedSocks);
    }

    @Operation(summary = "Fixes the outcome of socks from the warehouse, reducing their quantity in the DB.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Socks that are released from warehouse",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Socks.class))}),
            responses = {
                    @ApiResponse(
                    responseCode = "200",
                    description = "Outcome has been successfully implemented and " +
                            "the quantity of socks has been reduced in the DB",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Socks.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Query parameters are missing or are not in the correct format"
            ),
            @ApiResponse(responseCode = "500",
                    description = "An error occurred that is not dependent on the caller"
            )
    })
    @PutMapping("/outcome")
    public ResponseEntity<Socks> outcomeSocks(@RequestBody Socks socks) {
        Socks editedSocks = socksService.outcomeSocks(socks);
        if (editedSocks == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editedSocks);
    }

    @Operation(summary = "Finds socks by id in the DB.",
            responses = {
                    @ApiResponse(
                    responseCode = "200",
                    description = "Return Socks with the given id",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Socks.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Query parameters are missing or are not in the correct format"
            ),
            @ApiResponse(responseCode = "500",
                    description = "An error occurred that is not dependent on the caller"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Socks> findById(@Parameter(description = "id in DB") @PathVariable Long id) {
        Socks socks = socksService.findById(id);
        if (socks == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(socks);
    }

    @Operation(summary = "Finds the quantity of socks matching the parameters in the DB.",
            responses = {
                    @ApiResponse(
                    responseCode = "200",
                    description = "Successfully returns the total number of socks in DB " +
                            "that match the query criteria passed in the parameters",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Socks.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Query parameters are missing or are not in the correct format"
            ),
            @ApiResponse(responseCode = "500",
                    description = "An error occurred that is not dependent on the caller"
            )
    })
    @GetMapping
    public ResponseEntity<Integer> findQuantityOfSocksByColorAndCottonPart(
            @Parameter(description = "Socks color, String")
            @RequestParam String color,
            @Parameter(description = "Compares the composition of socks. " +
                    "Possible options: 'moreThan', 'lessThan' or 'equal'")
            @RequestParam String operation,
            @Parameter(description = "% cotton content in socks (from 0 to 100)")
            @RequestParam int cottonPart) {
        Integer socksQuantity =
                socksService.findQuantityOfSocksByColorAndCottonPart(color, operation, cottonPart);
        if (socksQuantity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(socksQuantity);
    }

    @Operation(summary = "Finds the collection of socks matching the parameters in the DB.",
            responses = {
                    @ApiResponse(
                    responseCode = "200",
                    description = "Successfully returns the collection of socks in DB" +
                            "that match the query criteria passed in the parameters",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Socks.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Query parameters are missing or are not in the correct format"
            ),
            @ApiResponse(responseCode = "500",
                    description = "An error occurred that is not dependent on the caller"
            )
    })
    @GetMapping("/list")
    public ResponseEntity<List<Socks>> findSocksByColorAndCottonPart(
            @Parameter(description = "Socks color, String")
            @RequestParam String color,
            @Parameter(description = "Compares the composition of socks. " +
                    "Possible options: 'moreThan', 'lessThan' or 'equal'")
            @RequestParam String operation,
            @Parameter(description = "% cotton content in socks (from 0 to 100)")
            @RequestParam int cottonPart) {
        List<Socks> socksList = socksService.findByColorAndCottonPart(color, operation, cottonPart);
        if (socksList == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(socksList);
    }
}
