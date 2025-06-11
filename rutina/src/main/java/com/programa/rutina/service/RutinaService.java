package com.programa.rutina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programa.rutina.models.RutinaModel;
import com.programa.rutina.models.UsuarioModel;
import com.programa.rutina.repository.RutinaRepository;



@Service
public class RutinaService {

    @Autowired
    private RutinaRepository rutinaRepository;

    public RutinaModel findByUsuario(UsuarioModel usuario) {
        return rutinaRepository.findByUsuario(usuario).orElse(null);
    }

    public List<RutinaModel> findAllByUsuarioId(Long usuarioId) {
    return rutinaRepository.findAllByUsuarioId(usuarioId);
}

    public RutinaModel save(RutinaModel rutina) {
        return rutinaRepository.save(rutina);
    }

    public Optional<RutinaModel> findById(Long id) {
        return rutinaRepository.findById(id);
    }

    public void deleteById(Long id) {
        rutinaRepository.deleteById(id);
    }
}
