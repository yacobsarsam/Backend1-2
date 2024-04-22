package com.example.pensionat.Repo;

import com.example.pensionat.models.Bokning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BokingRepo extends JpaRepository<Bokning,Long> {
}
