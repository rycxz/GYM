/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.programa.rutina.controllers;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.programa.rutina.models.UsuarioModel;
import com.programa.rutina.repository.UsuarioRepository;
import com.programa.rutina.service.CorreoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author 6003194
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    /*----------Variables------ */
        private final CorreoService emailService;
        private final UsuarioRepository usuarioRepository;
    /*----------Metodos------ */
    /**
     * Muestra la página de inicio de sesión
     * @return vista de inicio de sesión
     */
    @GetMapping("/login")
    public String login() {
            return "login";
    }
    /**
     * Muestra la página de registro
     * @return vista de registro
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    /**
     * Procesa el registro del usuario
     * @param request
     * @param session
     * @param model
     * @return vista de verificación
     */
    @PostMapping("/register")
    public String registerUser( HttpServletRequest request, HttpSession session, Model model) {
        // Obtener el nombre de usuario del formulario POST
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        if (username == null || username.isEmpty()) {
            return "redirect:/auth/register";
        }


        UsuarioModel usuario = new UsuarioModel();
        usuario.setNombre(username);
        usuario.setEmail(email);

        session.setAttribute("usuario", usuario);

        // Generar código
        String codigo = String.format("%06d", new Random().nextInt(999999));
        session.setAttribute("codigoVerificacion", codigo);

        emailService.enviarCodigo(usuario.getEmail(), codigo);
        return "verificacion";
    }

    /**
     * Procesa el código de verificación enviado por el usuario
     * @param codigo
     * @param session
     * @param redirectAttributes
     * @param model
     * @return redirección
     */
     @PostMapping("/register/verify")
        public String procesarCodigoVerificacion(@RequestParam("verificationCode") String codigo,
                                                HttpSession session,
                                                RedirectAttributes redirectAttributes ,Model model) {
            UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");
            if (usuario == null) return "redirect:/auth/login";

            String codigoGuardado = (String) session.getAttribute("codigoVerificacion");

            if (codigo.equals(codigoGuardado)) {
                usuario.setVerificado(true);
                usuarioRepository.save(usuario);
                session.setAttribute("usuario", usuario);
                redirectAttributes.addFlashAttribute("success", "Cuenta creada correctamente.");
                return "redirect:/home";
            } else {
                redirectAttributes.addFlashAttribute("error", "Código incorrecto. Intenta de nuevo.");
                return "redirect:/auth/register";
            }
        }

    @PostMapping("/login")
    public String loginUser(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        String usernameOrEmail = request.getParameter("username");
        if (usernameOrEmail == null || usernameOrEmail.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Debes ingresar tu nombre de usuario o correo.");
            return "redirect:/auth/login";
        }

        Optional<UsuarioModel> usuario = Optional.of(usuarioRepository.findByNombre(usernameOrEmail));
        if (usuario == null) {
            usuario = usuarioRepository.findByEmail(usernameOrEmail);
        }

        if (usuario.isPresent() && usuario.get().isVerificado()) {
            session.setAttribute("usuario", usuario.get());
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado o no verificado.");
            return "redirect:/auth/login";
        }
    }

}
