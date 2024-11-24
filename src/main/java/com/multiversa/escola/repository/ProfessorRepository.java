package com.multiversa.escola.repository;

import com.multiversa.escola.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

  boolean existsByEmail(String email);
  boolean existsByEmailAndIdNot(String email, Long id);

}
