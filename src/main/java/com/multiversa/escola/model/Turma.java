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

  @OneToMany(mappedBy = "turma", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  private List<Aluno> alunos;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "turma_disciplina",
      joinColumns = @JoinColumn(name = "turma_id"),
      inverseJoinColumns = @JoinColumn(name = "disciplina_id")
  )
  private List<Disciplina> disciplinas;
}
