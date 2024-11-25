package com.multiversa.escola.repository;

import com.multiversa.escola.model.Aluno;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
  boolean existsByEmail(String email);
  boolean existsByEmailAndIdNot(String email, Long id);
  Optional<Aluno> findByEmail(String email);
  Optional<Aluno> findByMatricula(String matricula);
}
