package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardsEqualToDifferenceEffect;
import mage.abilities.effects.common.continuous.LandsPerTurnControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Emblem;

public class SpeedCommanderEmblem extends Emblem {

    public SpeedCommanderEmblem() {
        super("Emblem Speed Commander");
        Ability ability1 = new SimpleStaticAbility(Zone.COMMAND,
                new LandsPerTurnControllerEffect(2, Duration.WhileOnBattlefield));
        this.getAbilities().add(ability1);
        Ability ability2 = new SimpleStaticAbility(Zone.COMMAND,
                new MaximumHandSizeControllerEffect(8, Duration.WhileOnBattlefield, HandSizeModification.SET));
        this.getAbilities().add(ability2);
        this.getAbilities().add(new BeginningOfDrawTriggeredAbility(Zone.COMMAND,
                new DrawCardsEqualToDifferenceEffect(8), TargetController.YOU, false));
    }

    private SpeedCommanderEmblem(final SpeedCommanderEmblem card) {
        super(card);
    }

    @Override
    public SpeedCommanderEmblem copy() {
        return new SpeedCommanderEmblem(this);
    }
}
