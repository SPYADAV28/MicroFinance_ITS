package sreenidhi.microfinanceSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import sreenidhi.microfinanceSystem.dto.LoanApplicationRequest;
import sreenidhi.microfinanceSystem.enumuration.FileType;
import sreenidhi.microfinanceSystem.service.LoanApplicationService;

@RestController
@RequestMapping("/api/loan")
public class LoanApplicationController {

    private final ObjectMapper objectMapper;
    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService, ObjectMapper objectMapper) {
        this.loanApplicationService = loanApplicationService;
        this.objectMapper = objectMapper;

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> submitLoanApplication(
            @RequestPart("data") LoanApplicationRequest request,
            @RequestParam Map<String, MultipartFile> filesMap) throws Exception {

        System.out.println("=== Received loan application submission ===");

        System.out.println("Request Details: " + request);

        // Filter valid file types using FileType enum
        Map<String, MultipartFile> validFiles = new HashMap<>();
        for (Map.Entry<String, MultipartFile> entry : filesMap.entrySet()) {
            String key = entry.getKey();
            MultipartFile file = entry.getValue();
            try {
                FileType.valueOf(key); // Validate enum key
                validFiles.put(key, file);
                System.out.println("Accepted file: " + key + " -> " + file.getOriginalFilename());
            } catch (IllegalArgumentException e) {
                System.out.println("Rejected file: " + key + " (invalid FileType)");
            }
        }

        if (validFiles.isEmpty()) {
            return ResponseEntity.badRequest().body("No valid file types submitted.");
        }

        loanApplicationService.saveLoanApplication(request, validFiles);

        System.out.println("Loan application processing completed successfully.");
        return ResponseEntity.ok("Loan application submitted successfully");
    }

}
