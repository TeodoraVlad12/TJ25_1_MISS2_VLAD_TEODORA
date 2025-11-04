package Students.example.Students.controller;

import Students.example.Students.dto.StudentPreferenceDTO;
import Students.example.Students.service.StudentPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/preferences")
@Tag(name = "Student Preferences", description = "API for managing student course preferences")
public class StudentPreferenceController {
    
    private final StudentPreferenceService preferenceService;
    
    public StudentPreferenceController(StudentPreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }
    
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get all student preferences", description = "Retrieves all student course preferences with support for JSON and XML")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved preferences",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StudentPreferenceDTO.class)),
                          @Content(mediaType = "application/xml", schema = @Schema(implementation = StudentPreferenceDTO.class))}),
        @ApiResponse(responseCode = "304", description = "Not modified - content hasn't changed")
    })
    public ResponseEntity<List<StudentPreferenceDTO>> getAllPreferences(
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        
        List<StudentPreferenceDTO> preferences = preferenceService.getAllPreferences();
        String etag = generateETag(preferences);
        
        if (ifNoneMatch != null && ifNoneMatch.equals(etag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        
        return ResponseEntity.ok()
                .eTag(etag)
                .body(preferences);
    }
    
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get preference by ID", description = "Retrieves a specific student preference by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved preference"),
        @ApiResponse(responseCode = "304", description = "Not modified"),
        @ApiResponse(responseCode = "404", description = "Preference not found")
    })
    public ResponseEntity<StudentPreferenceDTO> getPreferenceById(
            @Parameter(description = "ID of the preference to retrieve") @PathVariable Long id,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        
        StudentPreferenceDTO preference = preferenceService.getPreferenceById(id);
        String etag = generateETag(preference);
        
        if (ifNoneMatch != null && ifNoneMatch.equals(etag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        
        return ResponseEntity.ok()
                .eTag(etag)
                .body(preference);
    }
    
    @GetMapping(value = "/student/{studentId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get preferences by student ID", description = "Retrieves all preferences for a specific student")
    public ResponseEntity<List<StudentPreferenceDTO>> getPreferencesByStudentId(
            @Parameter(description = "ID of the student") @PathVariable Long studentId,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        
        List<StudentPreferenceDTO> preferences = preferenceService.getPreferencesByStudentId(studentId);
        String etag = generateETag(preferences);
        
        if (ifNoneMatch != null && ifNoneMatch.equals(etag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        
        return ResponseEntity.ok()
                .eTag(etag)
                .body(preferences);
    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Create a new preference", description = "Creates a new student course preference")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Preference created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<StudentPreferenceDTO> createPreference(
            @Parameter(description = "Preference data to create") @Valid @RequestBody StudentPreferenceDTO dto) {
        
        StudentPreferenceDTO created = preferenceService.createPreference(dto);
        String etag = generateETag(created);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .eTag(etag)
                .body(created);
    }
    
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update a preference", description = "Updates an existing student course preference")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Preference updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Preference not found")
    })
    public ResponseEntity<StudentPreferenceDTO> updatePreference(
            @Parameter(description = "ID of the preference to update") @PathVariable Long id,
            @Parameter(description = "Updated preference data") @Valid @RequestBody StudentPreferenceDTO dto) {
        
        StudentPreferenceDTO updated = preferenceService.updatePreference(id, dto);
        String etag = generateETag(updated);
        
        return ResponseEntity.ok()
                .eTag(etag)
                .body(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a preference", description = "Deletes a student course preference")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Preference deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Preference not found")
    })
    public ResponseEntity<Void> deletePreference(
            @Parameter(description = "ID of the preference to delete") @PathVariable Long id) {
        
        preferenceService.deletePreference(id);
        return ResponseEntity.noContent().build();
    }
    
    private String generateETag(Object object) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(object.toString().getBytes());
            return "\"" + Base64.getEncoder().encodeToString(hash) + "\"";
        } catch (NoSuchAlgorithmException e) {
            return "\"" + object.hashCode() + "\"";
        }
    }
    
    @ExceptionHandler(Students.example.Students.exception.PreferenceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePreferenceNotFound(Students.example.Students.exception.PreferenceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", java.time.LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
