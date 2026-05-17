package com.wilomski.ligarodzynkow.repository;

import com.wilomski.ligarodzynkow.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Optional<Player> findByNameIgnoreCase(String name);

    List<Player> findAllByOrderByNameAsc();
    boolean existsByName(String name);

    boolean existsByNameIgnoreCase(String name);
}