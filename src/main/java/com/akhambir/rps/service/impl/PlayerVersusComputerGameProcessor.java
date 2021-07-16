package com.akhambir.rps.service.impl;

import com.akhambir.rps.model.Action;
import com.akhambir.rps.model.ActionHistory;
import com.akhambir.rps.model.GameHistory;
import com.akhambir.rps.model.GameResult;
import com.akhambir.rps.service.GameProcessor;
import com.akhambir.rps.util.collections.Tuple;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.akhambir.rps.model.GameResult.DEFEAT;
import static com.akhambir.rps.model.GameResult.DRAW;
import static com.akhambir.rps.model.GameResult.WIN;
import static com.akhambir.rps.model.Participant.COMPUTER;
import static com.akhambir.rps.model.Participant.PLAYER;
import static com.akhambir.rps.util.BaseUtils.getSqlTimestamp;

@Service
@Builder
public class PlayerVersusComputerGameProcessor implements GameProcessor {

  @Autowired
  private Map<Action, Action> counterPickMap;
  @Autowired
  private Map<Integer, Action> actionsMap;
  private static final Integer MAX_ACTION_COUNT = 3;
  private static final Integer MIN_ACTION_COUNT = 1;

  @Override
  public Optional<GameHistory> process(Action playersAction) {
    var computersAction = getRandomAction();
    return Optional.of(buildGameState(playersAction, computersAction));
  }

  private Action getRandomAction() {
    var random = new Random();
    var randomInt = random
        .ints(MIN_ACTION_COUNT, MAX_ACTION_COUNT + MIN_ACTION_COUNT)
        .findFirst()
        .getAsInt();

    return actionsMap.get(randomInt);
  }

  private GameHistory buildGameState(Action a1, Action a2) {
    var gameResult = computeResult(a1, a2);

    var playersAction = ActionHistory.builder()
        .participant(PLAYER)
        .action(a1)
        .gameResult(gameResult.getV1())
        .build();

    var computersAction = ActionHistory.builder()
        .participant(COMPUTER)
        .action(a2)
        .gameResult(gameResult.getV2())
        .build();

    var actions = new ArrayList<ActionHistory>();
    actions.add(playersAction);
    actions.add(computersAction);

    return GameHistory.builder()
        .createdAt(getSqlTimestamp())
        .actionHistory(actions)
        .build();
  }

  private Tuple<GameResult> computeResult(Action a1, Action a2) {
    return a1 == a2
        ? Tuple.of(DRAW, DRAW)
        : counterPickMap.get(a1) == a2 ? Tuple.of(WIN, DEFEAT) : Tuple.of(DEFEAT, WIN);
  }
}
