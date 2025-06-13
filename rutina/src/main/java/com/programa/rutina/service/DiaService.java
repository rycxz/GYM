package com.programa.rutina.service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
 public String calcularDiferenciaHoras(String horaInicio, String horaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

        try {
            LocalTime inicio = LocalTime.parse(horaInicio, formatter);
            LocalTime fin = LocalTime.parse(horaFin, formatter);
            // si la hora de fin es antes que la de inicio, se asume que es al dia siguiente
            if (fin.isBefore(inicio)) {
                fin = fin.plusHours(24);
            }

            Duration duracion = Duration.between(inicio, fin);
            long horas = duracion.toHours();
            long minutos = duracion.toMinutes() % 60;

            return horas + " horas " + minutos + " minutos";
        } catch (Exception e) {
            return "Error al calcular diferencia: " + e.getMessage();
        }
    }

}
