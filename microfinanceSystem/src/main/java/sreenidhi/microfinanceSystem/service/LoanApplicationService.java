package sreenidhi.microfinanceSystem.service;

import java.util.List;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import sreenidhi.microfinanceSystem.dto.LoanApplicationRequest;
import sreenidhi.microfinanceSystem.enumuration.FileType;

public interface LoanApplicationService {
   
    public void saveLoanApplication( LoanApplicationRequest request, List<MultipartFile> files, List<FileType> fileTypes)  throws Exception;
    
    public void saveLoanApplication( LoanApplicationRequest request, Map<String, MultipartFile> filesMap)  throws Exception;
}
