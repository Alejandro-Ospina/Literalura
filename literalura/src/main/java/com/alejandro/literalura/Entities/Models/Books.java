package com.alejandro.literalura.Entities.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Books implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idBook;
    private String titulo;

    @ElementCollection
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "idBook"))
    @Column(name = "language")
    private Set<String> idiomas;
    private Integer ejemplaresDescargados;

    @ManyToMany (cascade = CascadeType.PERSIST)
    @JoinTable (
            name = "author_books",
            joinColumns = @JoinColumn(name = "idBook"),
            inverseJoinColumns = @JoinColumn(name = "idAutor")
    )
    private Set<Author> autores;
}
