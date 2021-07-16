package com.akhambir.rps.service;

import com.akhambir.rps.model.Action;
import com.akhambir.rps.model.GameHistory;
import java.util.Optional;

public interface RpsService {

  Optional<GameHistory> doTurn(Action action);

}
