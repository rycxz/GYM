/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.programa.rutina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programa.rutina.models.DiaModel;
import com.programa.rutina.utils.DiaSemana;



/**
 *
 * @author 6003194
 */
public interface DiaRepository extends JpaRepository<DiaModel, Long> {

    List<DiaModel> findByRutinaId(Long rutinaId);

    // Buscar por día de la semana
    List<DiaModel> findByDiaSemana(DiaSemana diaSemana);

    // Buscar por rutina y día de la semana (para evitar duplicados, por ejemplo)
    DiaModel findByRutinaIdAndDiaSemana(Long rutinaId, DiaSemana diaSemana);

    // Buscar por grupo muscular
    List<DiaModel> findByGrupoMuscularIgnoreCase(String grupoMuscular);

    // Eliminar todos los días de una rutina
    void deleteByRutinaId(Long rutinaId);

}
