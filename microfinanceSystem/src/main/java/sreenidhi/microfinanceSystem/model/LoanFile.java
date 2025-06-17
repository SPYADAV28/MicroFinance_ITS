package sreenidhi.microfinanceSystem.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import sreenidhi.microfinanceSystem.enumuration.FileType;

@Entity
@Table(name = "loan_files") 
public class LoanFile {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Enumerated(EnumType.STRING)
	    private FileType fileType;

	    private String fileName;

	    @Lob
	    private byte[] fileData;

	    private String contentType;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "loan_application_id")
	    private LoanApplication loanApplication;

	    @CreationTimestamp
	    private LocalDateTime uploadedAt;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public FileType getFileType() {
			return fileType;
		}

		public void setFileType(FileType fileType) {
			this.fileType = fileType;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public byte[] getFileData() {
			return fileData;
		}

		public void setFileData(byte[] fileData) {
			this.fileData = fileData;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public LoanApplication getLoanApplication() {
			return loanApplication;
		}

		public void setLoanApplication(LoanApplication loanApplication) {
			this.loanApplication = loanApplication;
		}

		public LocalDateTime getUploadedAt() {
			return uploadedAt;
		}

		public void setUploadedAt(LocalDateTime uploadedAt) {
			this.uploadedAt = uploadedAt;
		}


    
}

