package com.akhambir.rps.controller.external.model;

import com.akhambir.rps.model.GameHistory;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

import static java.util.stream.Collectors.toList;

@Data
@Builder
public class GameStateExt {

  private OffsetDateTime createdAt;
  private List<ActionStateExt> actionsState = new ArrayList<>();

  public static GameStateExt of(GameHistory gameHistory) {
    var actions = gameHistory.getActionHistory().stream()
        .map(ActionStateExt::of)
        .collect(toList());

    var createdAt = OffsetDateTime.ofInstant(gameHistory.getCreatedAt().toInstant(), ZoneOffset.UTC);

    return GameStateExt.builder()
        .actionsState(actions)
        .createdAt(createdAt)
        .build();
  }
}
