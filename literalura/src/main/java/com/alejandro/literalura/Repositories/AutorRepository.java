package com.alejandro.literalura.Repositories;

import com.alejandro.literalura.Entities.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT a.nombreAutor FROM Author a")
    List<String> findAllNombreAutor();

    Optional<Author> findByNombreAutor(String nombreAutor);

    List<Author> findByFechaFallecimientoIsNullOrFechaFallecimientoAfter(Integer fecha);
}
