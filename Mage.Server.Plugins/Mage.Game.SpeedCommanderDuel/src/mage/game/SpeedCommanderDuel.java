package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.command.emblems.SpeedCommanderEmblem;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;
import mage.players.Player;

import java.util.UUID;

/**
 * Game implementation for the Speed Commander variant.  This variant follows
 * Commander rules but accelerates the game by allowing two land plays per
 * turn, draws up to eight cards each draw step, and increases the maximum
 * hand size to eight.  Each player receives a {@link SpeedCommanderEmblem}
 * when the game starts.
 */
public class SpeedCommanderDuel extends GameCommanderImpl {

    public SpeedCommanderDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range,
                              Mulligan mulligan, int startLife, int startHandSize) {
        super(attackOption, range, mulligan, 100, startLife, startHandSize);
    }

    public SpeedCommanderDuel(final SpeedCommanderDuel game) {
        super(game);
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        // perform standard commander initialization first
        super.init(choosingPlayerId);
        // After the base init finishes, adjust each player's game parameters
        for (UUID playerId : state.getPlayerList(choosingPlayerId)) {
            Player player = getPlayer(playerId);
            if (player != null) {
                // Two land plays per turn and eight card hand size
                player.setLandsPerTurn(2);
                player.setMaxHandSize(8);
                // Give each player the Speed Commander emblem
                this.addEmblem(new SpeedCommanderEmblem(), null, playerId);
            }
        }
    }

    @Override
    public MatchType getGameType() {
        return new SpeedCommanderDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public SpeedCommanderDuel copy() {
        return new SpeedCommanderDuel(this);
    }
}
