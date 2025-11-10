package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;
import mage.game.GameException;

/**
 * Match class for Speed Commander duels.
 */
public class SpeedCommanderDuelMatch extends MatchImpl {

    public SpeedCommanderDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        int startHandSize = 8;
        // Determine mulligan type and number of free mulligans
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        // Allow custom start life and hand size if enabled in options
        startLife = options.isCustomStartLifeEnabled() ? options.getCustomStartLife() : startLife;
        startHandSize = options.isCustomStartHandSizeEnabled() ? options.getCustomStartHandSize() : startHandSize;
        // Create the Speed Commander game with specified parameters
        SpeedCommanderDuel game = new SpeedCommanderDuel(
                options.getAttackOption(), options.getRange(),
                mulligan, startLife, startHandSize
        );
        // Speed Commander still uses commander damage rule
        game.setCheckCommanderDamage(true);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }
}
