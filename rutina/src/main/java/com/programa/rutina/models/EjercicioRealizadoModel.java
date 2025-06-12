/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.programa.rutina.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "ejercicios_realizados")
public class EjercicioRealizadoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombreEjercicio;
    @Column(nullable = false)
    private int series;
        @Column(nullable = false)
    private String repeticiones;
        @Column(nullable = true)
    private String pesoUsado;

    private int repeticionesRecamara;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "dia_id")
    private DiaModel dia;
     @ManyToOne
    @JoinColumn(name = "rutina_id")
    private RutinaModel rutina;

}
