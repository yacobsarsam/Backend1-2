package com.example.pensionat.Repositories;

import com.example.pensionat.Models.Kund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KundRepo extends JpaRepository<Kund,Long> {
}
