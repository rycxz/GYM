package com.programa.rutina.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programa.rutina.models.UsuarioModel;




public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

   // Buscar por email
    Optional<UsuarioModel> findByEmail(String email);


    // Buscar por nombre ignorando mayúsculas/minúsculas
    Optional<UsuarioModel> findByNombreIgnoreCase(String nombre);

    //buscar y decir si existe correo
    boolean existsByEmailIgnoreCase(String email);


    UsuarioModel findByNombre(String usernameOrEmail);









}
