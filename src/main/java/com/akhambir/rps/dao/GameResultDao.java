package com.akhambir.rps.dao;

import com.akhambir.rps.model.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResultDao extends JpaRepository<GameHistory, Long> {
}
