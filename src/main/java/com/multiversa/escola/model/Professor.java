package com.multiversa.escola.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "professor")
@Data
public class Professor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "email")
  private String email;

  @Column(name = "disciplina", nullable = false)
  private String disciplinaPrincipal;

  @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Disciplina> disciplinas;

  @ManyToMany(mappedBy = "professores")
  private List<Turma> turmas;

}
