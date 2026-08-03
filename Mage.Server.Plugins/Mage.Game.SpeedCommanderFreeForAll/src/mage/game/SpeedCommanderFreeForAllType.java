package mage.game;

import mage.game.match.MatchType;

/**
 * @author drako
 */
public class SpeedCommanderFreeForAllType extends MatchType {

    public SpeedCommanderFreeForAllType() {
        this.name = "Speed Commander Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = false;
    }

    protected SpeedCommanderFreeForAllType(
            final SpeedCommanderFreeForAllType matchType
    ) {
        super(matchType);
    }

    @Override
    public SpeedCommanderFreeForAllType copy() {
        return new SpeedCommanderFreeForAllType(this);
    }
}