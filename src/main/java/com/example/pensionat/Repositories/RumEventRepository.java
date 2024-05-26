package com.example.pensionat.Repositories;

import com.example.pensionat.Models.RumEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RumEventRepository extends JpaRepository<RumEvent, Long> {
    List<RumEvent> findByRum_Id(Long rumId);
    List<RumEvent> findAll();

}
