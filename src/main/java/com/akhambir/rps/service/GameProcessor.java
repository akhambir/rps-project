package com.akhambir.rps.service;

import com.akhambir.rps.model.Action;
import com.akhambir.rps.model.GameHistory;
import java.util.Optional;

public interface GameProcessor {

  Optional<GameHistory> process(Action a1);

}
