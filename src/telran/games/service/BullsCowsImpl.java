package telran.games.service;

import telran.games.dto.MoveResult;

import java.util.*;

public class BullsCowsImpl implements BullsCows {
    private String secretStr;
    private List<MoveResult> history;
    private int count;

    public BullsCowsImpl(String secretStr) {
        this.secretStr = secretStr;
        history = new ArrayList<>();
    }


    @Override
    public MoveResult move(String guessStr) {
        Integer[] bullsCows = getIntegers(guessStr);
        MoveResult res = new MoveResult(bullsCows, guessStr);
        history.add(res);
        count++;
        return res;
    }

    private Integer[] getIntegers(String guessStr) {
        Integer[] bullsCows = {0, 0};
        char[] guessStrArr = guessStr.toCharArray();

        for (int i = 0; i < guessStrArr.length; i++) {
            int indexSecret = secretStr.indexOf(guessStrArr[i]);

            if (indexSecret > -1) {
                if (i == indexSecret) {
                    bullsCows[0]++;
                } else {
                    bullsCows[1]++;
                }
            }
        }
        return bullsCows;
    }

    @Override
    public List<MoveResult> getHistory() {
        return history;
    }

    @Override
    public Integer gameOver() {
        MoveResult lastResult = history.get(history.size() - 1);

        return lastResult.bullsCows()[0] == secretStr.length() ? secretStr.length() : -1;
    }
}
