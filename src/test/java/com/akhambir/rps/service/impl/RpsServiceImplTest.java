package com.akhambir.rps.service.impl;

import com.akhambir.rps.dao.GameResultDao;
import com.akhambir.rps.model.Action;
import com.akhambir.rps.model.ActionHistory;
import com.akhambir.rps.model.GameHistory;
import com.akhambir.rps.model.GameResult;
import com.akhambir.rps.service.GameProcessor;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.akhambir.rps.model.Participant.COMPUTER;
import static com.akhambir.rps.model.Participant.PLAYER;
import static com.akhambir.rps.util.BaseUtils.getSqlTimestamp;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RpsServiceImplTest {

  @Mock
  private GameProcessor gameProcessor;
  @Mock
  private GameResultDao gameResultDao;
  @InjectMocks
  private RpsServiceImpl rpsService;

  @Test
  public void doTurn_shouldCallGameProcessorAndPersistResult() {
    when(gameResultDao.save(any())).thenReturn(getGameHistory());
    when(gameProcessor.process(any())).thenReturn(Optional.of(getGameHistory()));

    rpsService.doTurn(Action.ROCK);

    verify(gameProcessor, times(1)).process(any());
    verify(gameResultDao, times(1)).save(any());
  }

  private GameHistory getGameHistory() {
    var playersAction = ActionHistory.builder()
        .id(1L)
        .participant(PLAYER)
        .action(Action.ROCK)
        .gameResult(GameResult.WIN)
        .build();

    var computersAction = ActionHistory.builder()
        .id(2L)
        .participant(COMPUTER)
        .action(Action.SCISSORS)
        .gameResult(GameResult.DEFEAT)
        .build();

    var actions = new ArrayList<ActionHistory>();
    actions.add(playersAction);
    actions.add(computersAction);

    return GameHistory.builder()
        .id(1L)
        .createdAt(getSqlTimestamp())
        .actionHistory(actions)
        .build();
  }
}

