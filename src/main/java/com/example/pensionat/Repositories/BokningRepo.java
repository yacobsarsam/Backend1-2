package com.example.pensionat.Repositories;

import com.example.pensionat.Models.Bokning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BokningRepo extends JpaRepository<Bokning,Long> {
}
