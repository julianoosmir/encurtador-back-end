package org.example.encurtador.repository;


import org.example.encurtador.entity.URLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URLEntity, Integer> {

    Optional<URLEntity> findByShortURL(String shortURL);

    boolean existsByShortURL(String shortURL);
}
