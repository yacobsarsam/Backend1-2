package com.example.pensionat.Repo;

import com.example.pensionat.models.Bokning;
import com.example.pensionat.models.Rum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RumRepo extends JpaRepository<Rum,Long> {
}
