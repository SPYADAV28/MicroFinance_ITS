package sreenidhi.microfinanceSystem.service.impl;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sreenidhi.microfinanceSystem.dto.GuarantorDto;
import sreenidhi.microfinanceSystem.dto.LoanApplicationRequest;
import sreenidhi.microfinanceSystem.enumuration.FileType;
import sreenidhi.microfinanceSystem.model.Guarantor;
import sreenidhi.microfinanceSystem.model.LoanApplication;
import sreenidhi.microfinanceSystem.model.LoanFile;
import sreenidhi.microfinanceSystem.repository.LoanApplicationRepository;
import sreenidhi.microfinanceSystem.service.FileStorageService;
import sreenidhi.microfinanceSystem.service.LoanApplicationService;

@Service
// @RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    // Constructor injection
    public LoanApplicationServiceImpl(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    @Override
    public void saveLoanApplication(LoanApplicationRequest request, List<MultipartFile> files,
            List<FileType> fileTypes) throws Exception {
        try {
            System.out.println("=== Start saving loan application ===");

            if (request == null) {
                throw new IllegalArgumentException("LoanApplicationRequest is null.");
            }

            if (files == null || fileTypes == null || files.size() != fileTypes.size()) {
                throw new IllegalArgumentException("Files and fileTypes must be non-null and of equal size.");
            }

            // Convert request to LoanApplication entity
            LoanApplication loanApp = convertToLoanApplicationEntity(request);
            System.out.println("Converted LoanApplicationRequest to entity");

            // Map guarantors
            if (request.getGuarantors() != null && !request.getGuarantors().isEmpty()) {
                List<Guarantor> guarantors = request.getGuarantors().stream().map(dto -> {
                    Guarantor g = convertToGuarantorEntity(dto);
                    g.setLoanApplication(loanApp);
                    System.out.println("Added guarantor: " + g.getName() + ", Relation: " + g.getRelation());
                    return g;
                }).toList();
                loanApp.setGuarantors(guarantors);
                System.out.println("Total guarantors added: " + guarantors.size());
            } else {
                loanApp.setGuarantors(Collections.emptyList());
                System.out.println("No guarantors provided.");
            }

            // Map loan files
            List<LoanFile> loanFiles = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                MultipartFile multipartFile = files.get(i);
                FileType type = fileTypes.get(i);

                if (multipartFile == null || multipartFile.isEmpty()) {
                    System.out.println("Skipping empty file at index " + i);
                    continue;
                }

                LoanFile loanFile = new LoanFile();
                loanFile.setFileName(multipartFile.getOriginalFilename());
                loanFile.setContentType(multipartFile.getContentType());
                loanFile.setFileType(type);
                loanFile.setFileData(multipartFile.getBytes());
                loanFile.setLoanApplication(loanApp);
                loanFiles.add(loanFile);

                System.out.println("Attached file: " + loanFile.getFileName() + ", Type: " + loanFile.getFileType());
            }
            loanApp.setLoanFiles(loanFiles);
            System.out.println("Total files attached: " + loanFiles.size());

            // Save entity
            loanApplicationRepository.save(loanApp);
            System.out.println("Loan application saved successfully with ID: " + loanApp.getId());

        } catch (IllegalArgumentException ex) {
            System.err.println("Validation failed: " + ex.getMessage());
            throw ex;
        } catch (IOException ex) {
            System.err.println("File processing error: " + ex.getMessage());
            throw new RuntimeException("Failed to process uploaded files", ex);
        } catch (Exception ex) {
            System.err.println("Unexpected error during loan application submission: " + ex.getMessage());
            throw new RuntimeException("Failed to save loan application", ex);
        }
    }

    public LoanApplication convertToLoanApplicationEntity(LoanApplicationRequest request) {
        LoanApplication loanApp = new LoanApplication();
        loanApp.setName(request.getName());
        loanApp.setGender(request.getGender());
        loanApp.setMaritalStatus(request.getMaritalStatus());
        loanApp.setPhone(request.getPhone());
        loanApp.setEmail(request.getEmail());
        loanApp.setAddress(request.getAddress());
        loanApp.setDistrict(request.getDistrict());
        loanApp.setState(request.getState());
        loanApp.setPincode(request.getPincode());
        loanApp.setDob(request.getDob());
        loanApp.setAadharNumber(request.getAadharNumber());
        loanApp.setPanNumber(request.getPanNumber());
        loanApp.setLoanAmount(request.getLoanAmount());
        loanApp.setLoanPurpose(request.getLoanPurpose());
        loanApp.setHasExistingLoan(request.getHasExistingLoan());
        loanApp.setExistingLoanAmount(request.getExistingLoanAmount());
        loanApp.setExistingLoanProvider(request.getExistingLoanProvider());
        loanApp.setExistingLoanEndDate(request.getExistingLoanEndDate());
        loanApp.setBusinessName(request.getBusinessName());
        loanApp.setBusinessType(request.getBusinessType());
        loanApp.setBusinessAddress(request.getBusinessAddress());
        loanApp.setBusinessProof(request.getBusinessProof());
        loanApp.setAddressProof(request.getAddressProof());
        loanApp.setTermsAccepted(request.getTermsAccepted());
        loanApp.setSubmissionDate(request.getSubmissionDate());
        return loanApp;
    }

    public Guarantor convertToGuarantorEntity(GuarantorDto dto) {
        Guarantor guarantor = new Guarantor();
        guarantor.setName(dto.getName());
        guarantor.setRelation(dto.getRelation());
        guarantor.setPhone(dto.getPhone());
        guarantor.setAadhar(dto.getAadhar());
        return guarantor;
    }

    @Override
    public void saveLoanApplication(LoanApplicationRequest request, Map<String, MultipartFile> filesMap)
            throws Exception {
        try {
            System.out.println("=== Start saving loan application ===");

            if (request == null) {
                throw new IllegalArgumentException("LoanApplicationRequest is null.");
            }

            // Convert request to LoanApplication entity
            LoanApplication loanApp = convertToLoanApplicationEntity(request);
            System.out.println("Converted LoanApplicationRequest to entity");

            // Map guarantors
            if (request.getGuarantors() != null && !request.getGuarantors().isEmpty()) {
                List<Guarantor> guarantors = request.getGuarantors().stream().map(dto -> {
                    Guarantor g = convertToGuarantorEntity(dto);
                    g.setLoanApplication(loanApp);
                    System.out.println("Added guarantor: " + g.getName() + ", Relation: " + g.getRelation());
                    return g;
                }).toList();
                loanApp.setGuarantors(guarantors);
                System.out.println("Total guarantors added: " + guarantors.size());
            } else {
                loanApp.setGuarantors(Collections.emptyList());
                System.out.println("No guarantors provided.");
            }

            // Map loan files from filesMap
            List<LoanFile> loanFiles = new ArrayList<>();
            for (Map.Entry<String, MultipartFile> entry : filesMap.entrySet()) {
                String fileTypeStr = entry.getKey();
                MultipartFile multipartFile = entry.getValue();

                if (multipartFile == null || multipartFile.isEmpty()) {
                    System.out.println("Skipping empty file for type: " + fileTypeStr);
                    continue;
                }

                FileType fileType;

                fileType = FileType.valueOf(fileTypeStr.toUpperCase());

                LoanFile loanFile = new LoanFile();
                loanFile.setFileName(multipartFile.getOriginalFilename());
                loanFile.setContentType(multipartFile.getContentType());
                loanFile.setFileType(fileType);
                loanFile.setFileData(multipartFile.getBytes());
                loanFile.setLoanApplication(loanApp);
                loanFiles.add(loanFile);

                System.out.println("Attached file: " + loanFile.getFileName() + ", Type: " + loanFile.getFileType());
            }

            loanApp.setLoanFiles(loanFiles);
            System.out.println("Total files attached: " + loanFiles.size());

            // Save entity
            loanApplicationRepository.save(loanApp);
            System.out.println("Loan application saved successfully with ID: " + loanApp.getId());

        } catch (IllegalArgumentException ex) {
            System.err.println("Validation failed: " + ex.getMessage());
            throw ex;
        } catch (IOException ex) {
            System.err.println("File processing error: " + ex.getMessage());
            throw new RuntimeException("Failed to process uploaded files", ex);
        } catch (Exception ex) {
            System.err.println("Unexpected error during loan application submission: " + ex.getMessage());
            throw new RuntimeException("Failed to save loan application", ex);
        }
    }

}
