package com.multiversa.escola.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "nota")
@Data
public class Nota {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Aluno aluno;

  @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Disciplina disciplina;

  @Column(name = "valor", nullable = false)
  private double valor;

  @Column(name = "data_avaliacao", nullable = false)
  private LocalDate dataAvaliacao;
}
