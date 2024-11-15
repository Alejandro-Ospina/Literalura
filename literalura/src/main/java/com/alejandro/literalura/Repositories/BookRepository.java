package com.alejandro.literalura.Repositories;

import com.alejandro.literalura.Entities.Models.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {

    @Query ("SELECT b.titulo FROM Books b")
    List<String> findAllTitulo();

    Optional<Books> findByTitulo(String titulo);

    @Query ("SELECT COUNT(b) FROM Books b WHERE :idioma MEMBER OF b.idiomas")
    Long countByIdioma(String idioma);

}
