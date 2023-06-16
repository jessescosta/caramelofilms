package br.com.jsc.screenmatch.domain.filme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FilmeRepository extends JpaRepository<Filme, Long> {
    @Query(value = "SELECT * FROM FILMES ORDER BY NOME",nativeQuery = true)
    List<Filme> findAllOrderByNome();

    @Query(value = "SELECT f.NOME FROM FILME f WHERE f.ANO = :ano",nativeQuery = true)
    List<Filme> findByYear(Integer ano);

    @Query("SELECT f FROM Filme f WHERE f.genero = :genero")
    List<Filme> findByGenero(@Param("genero") String genero);
}
