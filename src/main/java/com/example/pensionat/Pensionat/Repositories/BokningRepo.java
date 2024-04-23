package com.example.pensionat.Pensionat.Repositories;

import com.example.pensionat.Pensionat.Models.Bokning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BokningRepo extends JpaRepository<Bokning,Long> {
}
