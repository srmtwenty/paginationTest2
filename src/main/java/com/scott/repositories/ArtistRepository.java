package com.scott.repositories;


import com.scott.models.Artist;
import com.scott.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Page<Artist> findAll(Pageable pageable);
}
