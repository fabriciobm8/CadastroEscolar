package com.multiversa.escola.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "nota")
@Data
public class Nota {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "aluno_id", nullable = false)
  private Aluno aluno;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "disciplina_id", nullable = false)
  private Disciplina disciplina;

  @Column(name = "valor", nullable = false)
  private Double valor;

  @Column(name = "data_avaliacao", nullable = false)
  private LocalDate dataAvaliacao;
}
