package com.example.pensionat.Repositories;

import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Models.customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<customers,Long> {
    List<customers> findAll(Sort sort); //sorterar
    Page<customers> findAll(Pageable pageable); //skapar pages
}
