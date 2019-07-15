package net.pottercraft.Ollivanders2.Spell;

import net.pottercraft.Ollivanders2.StationarySpell.StationarySpellObj;
import org.bukkit.entity.Player;

import net.pottercraft.Ollivanders2.Ollivanders2;
import net.pottercraft.Ollivanders2.StationarySpell.O2StationarySpellType;

/**
 * Creates an Anti-apparition spell object.
 *
 * @author lownes
 * @author Azami7
 */
public final class NULLUM_APPAREBIT extends StationarySpellSuper
{
   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public NULLUM_APPAREBIT ()
   {
      super();

      spellType = O2SpellType.NULLUM_APPAREBIT;
      text = "Nullum apparebit creates a stationary spell which will not allow apparition into it.";
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public NULLUM_APPAREBIT (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      spellType = O2SpellType.NULLUM_APPAREBIT;
      setUsesModifier();

      baseDurationInSeconds = 300;
      durationModifierInSeconds = 15;
      baseRadius = 5;
      radiusModifier = 1;
      flairSize = 10;
      centerOnCaster = true;
   }

   @Override
   protected StationarySpellObj createStationarySpell ()
   {
      return new net.pottercraft.Ollivanders2.StationarySpell.NULLUM_APPAREBIT(p, player.getUniqueId(), location, O2StationarySpellType.NULLUM_APPAREBIT, radius, duration);
   }
}