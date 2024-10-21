package com.multiversa.escola.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "aluno")
@Data
public class Aluno {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", nullable = false )
  private String nome;

  @Column(name = "matricula", nullable = false)
  private String matricula;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "dataNascimento")
  private LocalDate dataNascimento;

  @ManyToOne
  @JoinColumn(name = "turma_id")
  private Turma turma;

  @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Nota> notas;

}
