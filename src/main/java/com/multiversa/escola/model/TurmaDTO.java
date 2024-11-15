package com.multiversa.escola.model;

import java.util.List;
import lombok.Data;

@Data
public class TurmaDTO {
  private String nome;
  private int ano;
  private List<Long> alunoIds;
  private List<Long> disciplinaIds;
}
