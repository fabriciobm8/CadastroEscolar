package com.multiversa.escola.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
  @NotBlank(message = "O nome é obrigatório.")
  @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
  private String nome;

  @Column(name = "matricula", nullable = false)
  @NotBlank(message = "A matrícula é obrigatória.")
  private String matricula;

  @Column(name = "email", nullable = false)
  @NotBlank(message = "O e-mail é obrigatório.")
  @Email(message = "Formato de e-mail inválido.")
  private String email;

  @Column(name = "dataNascimento")
  private LocalDate dataNascimento;

  @ManyToOne
  @JoinColumn(name = "turma_id")
  @JsonIgnore
  private Turma turma;

  @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Nota> notas;

}
