package com.multiversa.escola.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "disciplina")
@Data
public class Disciplina {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "carga_horaria", nullable = false)
  private int cargaHoraria;

  @ManyToOne
  @JoinColumn(name = "professor_id")
  private Professor professor;

  @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Nota> notas;

  @ManyToMany(mappedBy = "disciplinas")
  private List<Turma> turmas;

}
