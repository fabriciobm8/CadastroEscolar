package com.multiversa.escola.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "turma")
@Data
public class Turma {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "ano", nullable = false)
  private int ano;

  @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Aluno> alunos;

  @ManyToMany
  @JoinTable(
      name = "turma_disciplina",
      joinColumns = @JoinColumn(name = "turma_id"),
      inverseJoinColumns = @JoinColumn(name = "disciplina_id")
  )
  private List<Disciplina> disciplinas;

  @ManyToMany
  @JoinTable(
      name = "turma_professor",
      joinColumns = @JoinColumn(name = "turma_id"),
      inverseJoinColumns = @JoinColumn(name = "professor_id")
  )
  private List<Professor> professores;
}
