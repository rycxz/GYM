/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.programa.rutina.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.programa.rutina.models.DiaModel;
import com.programa.rutina.models.EjercicioRealizadoModel;
import com.programa.rutina.models.RutinaModel;
import com.programa.rutina.models.UsuarioModel;
import com.programa.rutina.repository.DiaRepository;
import com.programa.rutina.repository.EjercicioRealizadoRepository;
import com.programa.rutina.repository.RutinaRepository;
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
    private final RutinaRepository rutinaRepository;
    private final DiaRepository diaRepository;
    private final EjercicioRealizadoRepository ejercicioRepository;

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
            if (rutina != null) {
                  DiaModel diaDeHoy = rutina.getDias().stream()
                .filter(dia -> dia.getDiaSemana() == diaEnum)
                .findFirst()
                .orElse(null);
                 model.addAttribute("rutinaHoy", diaDeHoy);
            } else {
                model.addAttribute("rutinaHoy", null);
            }

        }

        return "home";
    }

//sigo hiaendolo en este controller por que me da palo hacer otro xDD
    @GetMapping("/crear-rutina")
    public String crearRutina(HttpSession session, Model model) {
        // Recuperar el usuario de la sesión
        Object usuario = session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
        return "crear-rutina";
    }
@PostMapping("/crear-rutina")
public String crearRutinaPost(HttpSession session, @ModelAttribute RutinaModel rutina, RedirectAttributes redirectAttributes) {
    UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");
    if (usuario == null) {
        return "redirect:/auth/login";
    }
    System.out.println("Rutina recibida//////////////////////////////////////////////////////////////////////////////: " + rutina);
    // Validaciones de campos obligatorios
    if (rutina.getNombre() == null || rutina.getNombre().isEmpty()) {
        redirectAttributes.addFlashAttribute("error", "El nombre de la rutina es obligatorio.");
        return "redirect:/crear-rutina";
    }

    if (rutina.getFechaInicio() == null || rutina.getFechaFin() == null) {
        redirectAttributes.addFlashAttribute("error", "Debes especificar las fechas de inicio y fin.");
        return "redirect:/crear-rutina";
    }
        // Desactivar rutina activa anterior si existe
        Optional<RutinaModel> rutinaActiva = rutinaRepository.findRutinaActivaPorUsuario(usuario);
        rutinaActiva.ifPresent(r -> {
            r.setActiva(false);
            rutinaRepository.save(r);
        });

        // Asignar usuario y activar la nueva rutina
        rutina.setUsuario(usuario);
        rutina.setActiva(true);
        rutinaRepository.save(rutina);

    if (rutina.getDias() != null) {

        for (DiaModel dia : rutina.getDias()) {
             System.out.println("DIA RECIBIDO -> " + dia.getDiaSemana());
            dia.setRutina(rutina);
            diaRepository.save(dia);


            if (dia.getEjercicios() != null) {
                for (EjercicioRealizadoModel ejercicio : dia.getEjercicios()) {
                    ejercicio.setDia(dia);
                    ejercicioRepository.save(ejercicio);
                }
            }
        }
    }




    redirectAttributes.addFlashAttribute("success", "¡Rutina creada exitosamente!");
    return "redirect:/home";
}


}
