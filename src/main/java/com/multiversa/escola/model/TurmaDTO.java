package com.multiversa.escola.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class TurmaDTO {

  @NotBlank(message = "O nome é obrigatório.")
  @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
  private String nome;

  private int ano;

  private List<Long> alunoIds;

  private List<Long> disciplinaIds;
}
