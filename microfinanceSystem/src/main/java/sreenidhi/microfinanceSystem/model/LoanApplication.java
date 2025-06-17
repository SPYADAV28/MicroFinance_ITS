package sreenidhi.microfinanceSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Personal Information
	private String name;
	private String gender;
	private String maritalStatus;
	private String phone;
	private String email;
	private String address;
	private String district;
	private String state;
	private String pincode;

	private LocalDate dob;

	private String aadharNumber;
	private String panNumber;

	// Loan Information
	private Double loanAmount;
	private String loanPurpose;
	private Boolean hasExistingLoan;
	private Double existingLoanAmount;
	private String existingLoanProvider;

	private LocalDate existingLoanEndDate;

	// Business Information
	private String businessName;
	private String businessType;
	private String businessAddress;
	private String businessProof;
	private String addressProof;

	// Terms
	private Boolean termsAccepted;

	private LocalDate submissionDate;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Guarantor> guarantors;

	@OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LoanFile> loanFiles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public Boolean getHasExistingLoan() {
		return hasExistingLoan;
	}

	public void setHasExistingLoan(Boolean hasExistingLoan) {
		this.hasExistingLoan = hasExistingLoan;
	}

	public Double getExistingLoanAmount() {
		return existingLoanAmount;
	}

	public void setExistingLoanAmount(Double existingLoanAmount) {
		this.existingLoanAmount = existingLoanAmount;
	}

	public String getExistingLoanProvider() {
		return existingLoanProvider;
	}

	public void setExistingLoanProvider(String existingLoanProvider) {
		this.existingLoanProvider = existingLoanProvider;
	}

	public LocalDate getExistingLoanEndDate() {
		return existingLoanEndDate;
	}

	public void setExistingLoanEndDate(LocalDate existingLoanEndDate) {
		this.existingLoanEndDate = existingLoanEndDate;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessProof() {
		return businessProof;
	}

	public void setBusinessProof(String bussinessProof) {
		this.businessProof = bussinessProof;
	}

	public String getAddressProof() {
		return addressProof;
	}

	public void setAddressProof(String addressProof) {
		this.addressProof = addressProof;
	}

	public Boolean getTermsAccepted() {
		return termsAccepted;
	}

	public void setTermsAccepted(Boolean termsAccepted) {
		this.termsAccepted = termsAccepted;
	}

	public LocalDate getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(LocalDate submissionDate) {
		this.submissionDate = submissionDate;
	}

	public List<Guarantor> getGuarantors() {
		return guarantors;
	}

	public void setGuarantors(List<Guarantor> guarantors) {
		this.guarantors = guarantors;
	}

	public List<LoanFile> getLoanFiles() {
		return loanFiles;
	}

	public void setLoanFiles(List<LoanFile> loanFiles) {
		this.loanFiles = loanFiles;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
