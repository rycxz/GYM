package com.programa.rutina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programa.rutina.models.EjercicioRealizadoModel;

@Repository
public interface EjercicioRealizadoRepository extends JpaRepository<EjercicioRealizadoModel, Long> {

    // Obtener todos los ejercicios de un día específico
    List<EjercicioRealizadoModel> findByDiaId(Long diaId);

    // Buscar por nombre de ejercicio dentro de un día
    List<EjercicioRealizadoModel> findByDiaIdAndNombreEjercicioContainingIgnoreCase(Long diaId, String nombreEjercicio);

    // Eliminar todos los ejercicios de un día
    void deleteByDiaId(Long diaId);
}
