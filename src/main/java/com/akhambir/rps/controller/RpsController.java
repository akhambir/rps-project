package com.akhambir.rps.controller;

import com.akhambir.rps.controller.external.model.GameStateExt;
import com.akhambir.rps.model.Action;
import com.akhambir.rps.service.RpsService;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@AllArgsConstructor
@RequestMapping("/api/actions")
public class RpsController {

  private final RpsService rpsService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<GameStateExt> doTurn(@RequestBody Turn turn) {
    return rpsService.doTurn(turn.getAction())
        .map(GameStateExt::of)
        .map(ResponseEntity.created(URI.create("not-supported-yet"))::body)
        .orElseGet(() -> new ResponseEntity<>(CONFLICT));
  }

  @Data
  static class Turn {
    Action action;
  }
}
