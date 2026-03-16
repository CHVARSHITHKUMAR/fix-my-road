package com.fixmyroad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fixmyroad.model.Complaint;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByUserEmail(String userEmail);

    long countByStatus(String status);

}