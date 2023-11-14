package telran.games.controller;

import telran.games.service.BullsCows;
import telran.games.service.BullsCowsImpl;
import telran.view.*;

public class BullsCowsAppl {

    public static void main(String[] args) {
        Menu menu = new Menu("Bulls & Cows game", getGameItem(), Item.exit());
        menu.perform(new SystemInputOutput());
    }

    private static Item getGameItem() {
        return Item.of("Start game", BullsCowsAppl::gameProcessing);
    }

    private static void gameProcessing(InputOutput inputOutput) {
        BullsCows game = new BullsCowsImpl(getSecretStr());
    }


}
