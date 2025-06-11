/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.programa.rutina.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.programa.rutina.models.DiaModel;
import com.programa.rutina.models.RutinaModel;
import com.programa.rutina.models.UsuarioModel;
import com.programa.rutina.service.DiaService;
import com.programa.rutina.service.RutinaService;
import com.programa.rutina.utils.DiaSemana;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


/**
 *
 * @author 6003194
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
  /*----------Variables------ */
    private final  RutinaService rutinaService;
    private final DiaService diaServicio;

    /*----------Metodos------ */
 @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        // Recuperar el usuario de la sesión
        Object usuario = session.getAttribute("usuario");
        if (usuario == null) {
            // Si no hay usuario en la sesión, redirigir a la página de inicio de sesión
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);

        if (usuario instanceof UsuarioModel usuarioModel) {
            DayOfWeek hoy = LocalDate.now().getDayOfWeek();
            DiaSemana diaEnum = diaServicio.mapEnglishToDiaSemana(hoy.name());

            RutinaModel rutina = rutinaService.findByUsuario(usuarioModel);

            DiaModel diaDeHoy = rutina.getDias().stream()
                .filter(dia -> dia.getDiaSemana() == diaEnum)
                .findFirst()
                .orElse(null);

            model.addAttribute("rutinaHoy", diaDeHoy);
        }

        return "home";
    }

}
