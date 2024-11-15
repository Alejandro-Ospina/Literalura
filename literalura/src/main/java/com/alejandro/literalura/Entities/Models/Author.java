package com.alejandro.literalura.Entities.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Author implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idAutor;

    @Column (unique = true, nullable = false)
    private String nombreAutor;

    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    @ManyToMany (mappedBy = "autores", cascade = CascadeType.PERSIST)
    private Set<Books> books;
}
