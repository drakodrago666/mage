package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author drako
 */
public class SpeedCommanderFreeForAllMatch extends MatchImpl {

    private static final int DEFAULT_STARTING_LIFE = 40;
    private static final int SPEED_COMMANDER_OPENING_HAND_SIZE = 10;

    public SpeedCommanderFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = DEFAULT_STARTING_LIFE;

        if (options.getDeckType().equals("Variant Magic - Duel Commander")) {
            startLife = 30;
        }

        Mulligan mulligan = options
                .getMulliganType()
                .getMulligan(options.getFreeMulligans());

        startLife = options.isCustomStartLifeEnabled()
                ? options.getCustomStartLife()
                : startLife;

        SpeedCommanderFreeForAll game = new SpeedCommanderFreeForAll(
                options.getAttackOption(),
                options.getRange(),
                mulligan,
                startLife,
                SPEED_COMMANDER_OPENING_HAND_SIZE
        );

        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }
}