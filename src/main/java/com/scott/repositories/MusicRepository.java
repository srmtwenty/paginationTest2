package com.scott.repositories;

import com.scott.models.Music;
import com.scott.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Page<Music> findAll(Pageable pageable);
}
