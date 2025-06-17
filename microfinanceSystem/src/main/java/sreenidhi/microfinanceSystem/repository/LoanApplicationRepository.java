package sreenidhi.microfinanceSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sreenidhi.microfinanceSystem.model.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
}
