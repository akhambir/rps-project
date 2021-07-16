package com.akhambir.rps.service.impl;

import com.akhambir.rps.model.Action;
import com.akhambir.rps.model.ActionHistory;
import com.akhambir.rps.model.GameResult;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.akhambir.rps.model.Action.PAPER;
import static com.akhambir.rps.model.Action.ROCK;
import static com.akhambir.rps.model.Action.SCISSORS;
import static com.akhambir.rps.model.GameResult.DEFEAT;
import static com.akhambir.rps.model.GameResult.DRAW;
import static com.akhambir.rps.model.GameResult.WIN;
import static com.akhambir.rps.model.Participant.COMPUTER;
import static com.akhambir.rps.model.Participant.PLAYER;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
class PlayerVersusComputerGameProcessorTest {

  private static final Map<Action, Action> counterPickMap = new HashMap<>();
  private static final Map<Integer, Action> actionsMap = new HashMap<>();
  private PlayerVersusComputerGameProcessor processor;

  @BeforeAll
  static void setupTest() {
    counterPickMap.put(Action.ROCK, Action.SCISSORS);
    counterPickMap.put(Action.PAPER, Action.ROCK);
    counterPickMap.put(Action.SCISSORS, Action.PAPER);
  }

  @ParameterizedTest
  @MethodSource("mockCombinations")
  public void processor_shouldReturnValidResultOnDifferentCombinations(Action playersAction, Action computersAction, GameResult gameResult) {
    actionsMap.put(1, computersAction);
    actionsMap.put(2, computersAction);
    actionsMap.put(3, computersAction);

    processor = PlayerVersusComputerGameProcessor.builder()
        .counterPickMap(counterPickMap)
        .actionsMap(actionsMap)
        .build();

    var result = processor.process(playersAction).get();

    var expectedActionsSize = 2;
    var expectedParticipants = new HashSet<>();
    expectedParticipants.add(PLAYER);
    expectedParticipants.add(COMPUTER);

    var actualParticipants = result.getActionHistory().stream()
        .map(ActionHistory::getParticipant)
        .collect(toSet());

    var playerAction = result.getActionHistory().stream()
        .filter(a -> a.getParticipant().equals(PLAYER))
        .findFirst()
        .get();

    assertEquals(expectedActionsSize, result.getActionHistory().size());
    assertEquals(expectedParticipants, actualParticipants);

    assertEquals(gameResult, playerAction.getGameResult());
  }

  private static Stream<Arguments> mockCombinations() {
    return Stream.of(
        Arguments.of(ROCK, SCISSORS, WIN),
        Arguments.of(SCISSORS, PAPER, WIN),
        Arguments.of(PAPER, ROCK, WIN),

        Arguments.of(PAPER, PAPER, DRAW),
        Arguments.of(ROCK, ROCK, DRAW),
        Arguments.of(SCISSORS, SCISSORS, DRAW),

        Arguments.of(SCISSORS, ROCK, DEFEAT),
        Arguments.of(PAPER, SCISSORS, DEFEAT),
        Arguments.of(ROCK, PAPER, DEFEAT)
    );
  }
}