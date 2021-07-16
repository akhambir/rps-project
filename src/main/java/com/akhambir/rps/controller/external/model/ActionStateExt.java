package com.akhambir.rps.controller.external.model;

import com.akhambir.rps.model.Action;
import com.akhambir.rps.model.ActionHistory;
import com.akhambir.rps.model.GameResult;
import com.akhambir.rps.model.Participant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionStateExt {

  private Participant participant;
  private Action action;
  private GameResult gameResult;

  public static ActionStateExt of(ActionHistory actionHistory) {
    return ActionStateExt.builder()
        .participant(actionHistory.getParticipant())
        .action(actionHistory.getAction())
        .gameResult(actionHistory.getGameResult())
        .build();
  }
}
