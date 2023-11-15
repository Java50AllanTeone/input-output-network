package telran.games.controller;

import telran.games.dto.MoveResult;
import telran.games.service.BullsCows;
import telran.games.service.BullsCowsImpl;
import telran.view.*;

import java.lang.invoke.StringConcatFactory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

public class BullsCowsAppl {

    private static final long SECRET_LENGTH = 4;

    public static void main(String[] args) {
        Menu menu = new Menu("Bulls & Cows game", getGameItem(), Item.exit());
        menu.perform(new SystemInputOutput());
    }

    private static Item getGameItem() {
        return Item.of("Start game", BullsCowsAppl::gameProcessing);
    }

    private static void gameProcessing(InputOutput io) {
        BullsCows game = new BullsCowsImpl(getSecretStr());
        boolean running = true;
        int count = -1;

        while (running) {
            game.getHistory()
                    .stream()
                    .map(mr -> String.format("guess: %s, bulls: %d, cows: %d", mr.guessStr(), mr.bullsCows()[0], mr.bullsCows()[1]))
                    .forEach(io::writeLine);
            String guessStr = io.readString("Enter sequence of 4 unique digits",
                    "Wrong guess: must be 4 nonrepeated digits", BullsCowsAppl::checkGuessStr);
            MoveResult res = game.move(guessStr);
            Integer[] bullsCows = res.bullsCows();
            io.writeLine(String.format("Bulls: %d, Cows: %d", bullsCows[0], bullsCows[1]));
            count = game.gameOver();
            running = count < 0;

        }
        io.writeLine("You win :) number of moves is " + count);
    }


    private static boolean checkGuessStr(String str) {
        boolean result = false;

        if (str.matches("\\d{4}")) {
            char[] ar = str.toCharArray();
            HashSet<Character> set = new HashSet<>();
            for (char sym: ar) {
                set.add(sym);
            }
            result = set.size() == SECRET_LENGTH;
        }
        return result;
    }


    private static String getSecretStr() {
        return new Random()
                .ints('0', '9' + 1)
                .distinct()
                .limit(SECRET_LENGTH)
                .mapToObj(n -> "" + (char)n)
                .collect(Collectors.joining());
    }


}
