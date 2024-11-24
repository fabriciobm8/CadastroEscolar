package com.multiversa.escola.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "professor")
@Data
public class Professor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", nullable = false)
  @NotBlank(message = "O nome é obrigatório.")
  @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
  private String nome;

  @Column(name = "email")
  @NotBlank(message = "O e-mail é obrigatório.")
  @Email(message = "Formato de e-mail inválido.")
  private String email;

  @Column(name = "disciplina_principal", nullable = false)
  @NotBlank(message = "O nome da disciplina principal é obrigatório.")
  @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
  private String disciplinaPrincipal;

  @OneToMany(mappedBy = "professor", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
  @JsonIgnore
  private List<Disciplina> disciplinas;

}
