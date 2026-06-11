package org.example.project.repository;

import org.example.project.entity.Court;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourtRepository extends JpaRepository<Court, Long> {
    List<Court> findByNameContainingIgnoreCase(String keyword);
    Page<Court> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );
}
