package com.multiversa.escola.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
  @NotBlank(message = "O nome é obrigatório.")
  @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
  private String nome;

  @Column(name = "carga_horaria", nullable = false)
  private int cargaHoraria;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Professor professor;

  @OneToMany(mappedBy = "disciplina", cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JsonIgnore
  private List<Nota> notas;

}
