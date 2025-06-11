package com.programa.rutina.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programa.rutina.models.DiaModel;
import com.programa.rutina.repository.DiaRepository;
import com.programa.rutina.utils.DiaSemana;

@Service
public class DiaService {

    @Autowired
    private DiaRepository diaRepository;

    public DiaModel findByRutinaIdAndDiaSemana(Long rutinaId, DiaSemana diaSemana) {
        Optional<DiaModel> diaOptional = Optional.ofNullable(diaRepository.findByRutinaIdAndDiaSemana(rutinaId, diaSemana));
        return diaOptional.orElse(null);
    }

    public DiaModel save(DiaModel dia) {
        return diaRepository.save(dia);
    }

    public void deleteById(Long id) {
        diaRepository.deleteById(id);
    }

    public Optional<DiaModel> findById(Long id) {
        return diaRepository.findById(id);
    }
    public DiaSemana mapEnglishToDiaSemana(String englishDay) {
    return switch (englishDay.toUpperCase()) {
        case "MONDAY"    -> DiaSemana.LUNES;
        case "TUESDAY"   -> DiaSemana.MARTES;
        case "WEDNESDAY" -> DiaSemana.MIERCOLES;
        case "THURSDAY"  -> DiaSemana.JUEVES;
        case "FRIDAY"    -> DiaSemana.VIERNES;
        case "SATURDAY"  -> DiaSemana.SABADO;
        case "SUNDAY"    -> DiaSemana.DOMINGO;
        default -> throw new IllegalArgumentException("Día no válido: " + englishDay);
    };
}

}
