package com.multiversa.escola.service;

import com.multiversa.escola.model.Nota;
import com.multiversa.escola.repository.NotaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotaService {

  @Autowired
  NotaRepository notaRepository;

  public Nota saveNota(Nota nota) {
    return notaRepository.save(nota);
  }

  public Nota getNota(long notaId) {
    return notaRepository.findById(notaId).orElseThrow(() -> new RuntimeException("Nota nao encontrada"));
  }

  public List<Nota> getNotas() {
    return notaRepository.findAll();
  }

  public Nota updateNota(long notaId, Nota nota) {
    Nota existingNota = notaRepository.findById(notaId).orElseThrow(() -> new
        RuntimeException("Nota não encontrada"));
    existingNota.setAluno(nota.getAluno());
    existingNota.setDisciplina(nota.getDisciplina());
    existingNota.setValor(nota.getValor());
    existingNota.setDataAvaliacao(nota.getDataAvaliacao());
    notaRepository.save(existingNota);
    return existingNota;
  }

  public Nota deleteNota(long notaId) {
    Nota existingNota = notaRepository.findById(notaId).orElseThrow(() -> new
        RuntimeException("Nota não encontrada"));
    notaRepository.deleteById(notaId);
    return existingNota;
  }

}
