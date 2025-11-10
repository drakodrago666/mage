package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 * Continuous effect that allows the controller of the source to play a set number of lands each turn.
 */
public class LandsPerTurnControllerEffect extends ContinuousEffectImpl {

    private final int landsPerTurn;

    public LandsPerTurnControllerEffect(int landsPerTurn, Duration duration) {
        super(duration, Outcome.Benefit);
        this.landsPerTurn = landsPerTurn;
    }

    public LandsPerTurnControllerEffect(final LandsPerTurnControllerEffect effect) {
        super(effect);
        this.landsPerTurn = effect.landsPerTurn;
    }

    @Override
    public LandsPerTurnControllerEffect copy() {
        return new LandsPerTurnControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.setLandsPerTurn(landsPerTurn);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Ability source) {
        return "You may play " + landsPerTurn + " lands each turn";
    }

    @Override
    public Layer getLayer() {
        return Layer.PlayerEffects;
    }

    @Override
    public SubLayer getSubLayer() {
        return SubLayer.NA;
    }
}
