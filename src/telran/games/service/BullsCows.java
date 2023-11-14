package telran.games.service;

import telran.games.dto.MoveResult;
import java.util.List;

public interface BullsCows {

    MoveResult move(String guessStr);
    List<MoveResult> getHistory();
    Integer gameOver();
}
