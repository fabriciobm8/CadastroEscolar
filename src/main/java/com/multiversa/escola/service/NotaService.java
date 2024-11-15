package com.multiversa.escola.service;

import com.multiversa.escola.model.Nota;
import com.multiversa.escola.model.Aluno;
import com.multiversa.escola.model.Disciplina;
import com.multiversa.escola.repository.NotaRepository;
import com.multiversa.escola.repository.AlunoRepository;
import com.multiversa.escola.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotaService {

  @Autowired
  NotaRepository notaRepository;
  @Autowired
  AlunoRepository alunoRepository;
  @Autowired
  DisciplinaRepository disciplinaRepository;

  public Nota saveNota(Nota nota) {
    Aluno aluno = alunoRepository.findById(nota.getAluno().getId())
        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    Disciplina disciplina = disciplinaRepository.findById(nota.getDisciplina().getId())
        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    nota.setAluno(aluno);
    nota.setDisciplina(disciplina);
    return notaRepository.save(nota);
  }

  public Nota getNota(long notaId) {
    return notaRepository.findById(notaId).orElseThrow(() -> new RuntimeException("Nota não encontrada"));
  }

  public List<Nota> getNotas() {
    return notaRepository.findAll();
  }

  public Nota updateNota(long notaId, Nota nota) {
    Nota existingNota = notaRepository.findById(notaId).orElseThrow(() -> new RuntimeException("Nota não encontrada"));
    existingNota.setValor(nota.getValor());
    existingNota.setDataAvaliacao(nota.getDataAvaliacao());
    if (nota.getAluno() != null) {
      existingNota.setAluno(nota.getAluno());
    }
    if (nota.getDisciplina() != null) {
      existingNota.setDisciplina(nota.getDisciplina());
    }
    return notaRepository.save(existingNota);
  }

  public void deleteNota(long notaId) {
    Nota existingNota = notaRepository.findById(notaId).orElseThrow(() -> new RuntimeException("Nota não encontrada"));
    notaRepository.delete(existingNota);
  }
}
