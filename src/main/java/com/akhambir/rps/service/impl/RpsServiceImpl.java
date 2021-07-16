package com.akhambir.rps.service.impl;

import com.akhambir.rps.dao.GameResultDao;
import com.akhambir.rps.model.Action;
import com.akhambir.rps.model.GameHistory;
import com.akhambir.rps.service.GameProcessor;
import com.akhambir.rps.service.RpsService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class RpsServiceImpl implements RpsService {

  private final GameProcessor gameProcessor;
  private final GameResultDao gameResultDao;

  @Override
  public Optional<GameHistory> doTurn(Action playersAction) {
    return gameProcessor.process(playersAction)
        .map(gameResultDao::save);
  }
}
