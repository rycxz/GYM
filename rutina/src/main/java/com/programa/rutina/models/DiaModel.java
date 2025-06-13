/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.programa.rutina.models;

import java.util.ArrayList;
import java.util.List;

import com.programa.rutina.utils.DiaSemana;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 *
 * @author 6003194
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dias")
public class DiaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String grupoMuscular;
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;
    private String duracion;

    @ManyToOne
    @JoinColumn(name = "rutina_id")
    private RutinaModel rutina;

    @OneToMany(mappedBy = "dia", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EjercicioRealizadoModel> ejercicios = new ArrayList<>();
    @Override
public String toString() {
    return "DiaModel{" +
            "id=" + id +
            ", diaSemana=" + diaSemana +

            '}';
}

}

