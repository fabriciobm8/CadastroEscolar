package com.multiversa.escola.controller;

import com.multiversa.escola.model.Nota;
import com.multiversa.escola.service.NotaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nota")
public class NotaController {

  @Autowired
  NotaService notaService;

  @PostMapping
  public ResponseEntity<Nota> saveNota(@RequestBody Nota nota) {
    Nota savedNota = notaService.saveNota(nota);
    return ResponseEntity.ok(savedNota);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Nota> getNotaById(@PathVariable long id) {
    Nota nota = notaService.getNota(id);
    return ResponseEntity.ok(nota);
  }

  @GetMapping("/notas")
  public List<Nota> getAllNotas() {
    return notaService.getNotas();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Nota> updateNota(@PathVariable long id, @RequestBody Nota nota) {
    Nota updatedNota = notaService.updateNota(id, nota);
    return ResponseEntity.ok(updatedNota);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNota(@PathVariable long id) {
    notaService.deleteNota(id);
    return ResponseEntity.noContent().build();
  }

}
