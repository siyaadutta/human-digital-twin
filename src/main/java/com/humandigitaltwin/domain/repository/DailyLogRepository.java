package com.humandigitaltwin.domain.repository;

import com.humandigitaltwin.domain.model.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
    List<DailyLog> findAllByOrderByLogDateDesc();
    List<DailyLog> findTop7ByOrderByLogDateDesc();
    Optional<DailyLog> findByLogDate(LocalDate logDate);
}
