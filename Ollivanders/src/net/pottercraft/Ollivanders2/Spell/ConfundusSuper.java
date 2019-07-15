package net.pottercraft.Ollivanders2.Spell;

import net.pottercraft.Ollivanders2.Ollivanders2;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

/**
 * Confundus Charm super class which causes confusion in the target
 *
 * @author Azami7
 * @author lownes
 */
public abstract class ConfundusSuper extends PotionEffectSuper
{
   int durationMultiplier = 1;

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   ConfundusSuper ()
   {
      super();
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   ConfundusSuper (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      effectTypes.add(PotionEffectType.CONFUSION);

      strengthModifier = 1;
      minDurationInSeconds = 15;
      maxDurationInSeconds = 120;
   }

   @Override
   void doInitSpell ()
   {
      durationInSeconds = (int) usesModifier * durationMultiplier;
      if (durationInSeconds < minDurationInSeconds)
      {
         durationInSeconds = minDurationInSeconds;
      }
      else if (durationInSeconds > maxDurationInSeconds)
      {
         durationInSeconds = maxDurationInSeconds;
      }
   }
}
