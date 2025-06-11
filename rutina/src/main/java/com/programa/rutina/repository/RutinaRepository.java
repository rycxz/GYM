/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.programa.rutina.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programa.rutina.models.RutinaModel;
import com.programa.rutina.models.UsuarioModel;


/**
 *
 * @author 6003194
 */
@Repository
public interface RutinaRepository  extends JpaRepository <RutinaModel, Long> {
    List<RutinaModel> findByNombre(String nombre);
     List<RutinaModel> findByUsuarioId(Long usuarioId);
      List<RutinaModel> findByNombreContainingIgnoreCase(String nombreParcial);
      void deleteByUsuarioId(Long usuarioId);
      Optional<RutinaModel> findByUsuario(UsuarioModel usuario);
      List<RutinaModel> findAllByUsuarioId(Long usuarioId);

}
