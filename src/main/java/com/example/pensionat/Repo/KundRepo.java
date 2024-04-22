package com.example.pensionat.Repo;

import com.example.pensionat.models.Bokning;
import com.example.pensionat.models.Kund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KundRepo extends JpaRepository<Kund,Long> {
}
