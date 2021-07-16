package com.akhambir.rps.controller;

import com.akhambir.rps.model.Action;
import com.akhambir.rps.model.ActionHistory;
import com.akhambir.rps.model.GameHistory;
import com.akhambir.rps.model.GameResult;
import com.akhambir.rps.service.RpsService;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.akhambir.rps.model.Participant.COMPUTER;
import static com.akhambir.rps.model.Participant.PLAYER;
import static com.akhambir.rps.util.BaseUtils.getSqlTimestamp;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RpsController.class)
public class RpsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RpsService rpsService;

  @Test
  public void postActions_shouldReturnCreatedWithResponseBody() throws Exception {
    when(rpsService.doTurn(any()))
        .thenReturn(Optional.ofNullable(getGameHistory()));

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/api/actions")
        .content("{\"action\":\"PAPER\"}")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder)
        .andExpect(status().isCreated())
        .andExpect(content()
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.actionsState", hasSize(2)));
  }

  private GameHistory getGameHistory() {
    var playersAction = ActionHistory.builder()
        .participant(PLAYER)
        .action(Action.ROCK)
        .gameResult(GameResult.WIN)
        .build();

    var computersAction = ActionHistory.builder()
        .participant(COMPUTER)
        .action(Action.SCISSORS)
        .gameResult(GameResult.DEFEAT)
        .build();

    var actions = new ArrayList<ActionHistory>();
    actions.add(playersAction);
    actions.add(computersAction);

    return GameHistory.builder()
        .createdAt(getSqlTimestamp())
        .actionHistory(actions)
        .build();
  }
}