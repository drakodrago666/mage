package mage.game;

import mage.game.match.MatchType;
import mage.game.match.Mulligan;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;

/**
 * Match type for Speed Commander.
 */
public class SpeedCommanderDuelType extends MatchType {

    public SpeedCommanderDuelType() {
        this.name = "Speed Commander";
        this.minPlayers = 2;
        this.maxPlayers = 2;
        this.numSeats = 2;
        // Use standard Commander attack option (attack only left opponent in duels)
        this.attackOption = MultiplayerAttackOption.LEFT;
        this.range = RangeOfInfluence.ALL;
        // Vancouver mulligan with one free mulligan (Commander default)
        this.mulligan = new Mulligan(1);
    }

    protected SpeedCommanderDuelType(final SpeedCommanderDuelType matchType) {
        super(matchType);
    }

    @Override
    public SpeedCommanderDuelType copy() {
        return new SpeedCommanderDuelType(this);
    }
}
