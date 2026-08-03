package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.events.GameEvent;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Commander Free For All with Speed Commander rules.
 *
 * Opening sequence:
 * 1. Complete normal mulligans.
 * 2. Each player chooses and discards three cards.
 *
 * Turn rules:
 * 1. Each player may play two lands per turn.
 * 2. During the draw step, the player draws normally.
 * 3. Players with a finite maximum hand size then draw up to eight cards.
 * 4. Players with no maximum hand size receive only the normal draw.
 */
public class SpeedCommanderFreeForAll extends GameCommanderImpl {

    private static final int OPENING_DISCARD_COUNT = 3;
    private static final int REFILL_HAND_SIZE = 8;

    private int numPlayers;

    public SpeedCommanderFreeForAll(
            MultiplayerAttackOption attackOption,
            RangeOfInfluence range,
            Mulligan mulligan,
            int startLife,
            int startHandSize
    ) {
        super(
                attackOption,
                range,
                mulligan,
                100,
                startLife,
                startHandSize
        );
    }

    public SpeedCommanderFreeForAll(
            final SpeedCommanderFreeForAll game
    ) {
        super(game);
        this.numPlayers = game.numPlayers;
    }

    /**
     * Complete the entire standard Commander initialization and mulligan
     * process first. Then perform the Speed Commander opening discard.
     *
     * This avoids placing another card-selection request inside XMage's
     * mulligan response handling.
     */
    @Override
    protected void init(UUID choosingPlayerId) {
        startingPlayerSkipsDraw = false;

        super.init(choosingPlayerId);

        if (hasEnded()) {
            return;
        }

        try {
            for (UUID playerId : getState().getPlayerList(startingPlayerId)) {
                Player player = getPlayer(playerId);

                if (player == null || !player.isInGame()) {
                    continue;
                }

                int amountToDiscard = Math.min(
                        OPENING_DISCARD_COUNT,
                        player.getHand().size()
                );

                if (amountToDiscard <= 0) {
                    continue;
                }

                getState().setChoosingPlayerId(playerId);

                informPlayers(
                        player.getLogName()
                                + " chooses "
                                + CardUtil.numberToText(amountToDiscard)
                                + " card"
                                + (amountToDiscard == 1 ? "" : "s")
                                + " to discard for Speed Commander"
                );

                player.discard(
                        amountToDiscard,
                        false,
                        false,
                        null,
                        this
                );
            }
        } finally {
            getState().setChoosingPlayerId(null);
        }
    }

    /**
     * XMage resets lands-per-turn to one before recalculating continuous
     * effects. Add the format's additional land play afterward.
     */
    @Override
    public synchronized void applyEffects() {
        super.applyEffects();

        for (Player player : getPlayers().values()) {
            if (player != null && player.isInGame()) {
                player.setLandsPerTurn(
                        CardUtil.overflowInc(
                                player.getLandsPerTurn(),
                                1
                        )
                );
            }
        }
    }

    /**
     * DRAW_STEP is fired after the normal draw-step card has been drawn.
     */
    @Override
    public void fireEvent(GameEvent event) {
        if (event != null
                && event.getType() == GameEvent.EventType.DRAW_STEP) {

            Player player = getPlayer(event.getPlayerId());

            if (player != null
                    && player.isInGame()
                    && player.getMaxHandSize() != Integer.MAX_VALUE
                    && player.getHand().size() < REFILL_HAND_SIZE) {

                int cardsToDraw =
                        REFILL_HAND_SIZE - player.getHand().size();

                player.drawCards(
                        cardsToDraw,
                        null,
                        this
                );
            }
        }

        super.fireEvent(event);
    }

    @Override
    public MatchType getGameType() {
        return new SpeedCommanderFreeForAllType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public SpeedCommanderFreeForAll copy() {
        return new SpeedCommanderFreeForAll(this);
    }
}