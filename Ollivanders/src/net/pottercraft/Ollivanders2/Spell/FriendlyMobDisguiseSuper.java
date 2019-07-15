package net.pottercraft.Ollivanders2.Spell;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import net.pottercraft.Ollivanders2.Ollivanders2;
import net.pottercraft.Ollivanders2.Ollivanders2Common;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Super class for transfiguring friendly mobs.
 *
 * @since 2.2.6
 * @author Azami7
 */
public abstract class FriendlyMobDisguiseSuper extends EntityDisguiseSuper
{
   int minDurationInSeconds = 15;
   int maxDurationInSeconds = 600; // 10 minutes

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public FriendlyMobDisguiseSuper ()
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
   public FriendlyMobDisguiseSuper (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      worldGuardFlags.add(DefaultFlag.DAMAGE_ANIMALS);
   }

   @Override
   void doInitSpell ()
   {
      // whitelist of entities that can be targeted by this spell
      entityWhitelist.addAll(Ollivanders2Common.smallFriendlyAnimals);

      int uses = (int) (usesModifier * 4);

      if (uses > 100)
      {
         entityWhitelist.addAll(Ollivanders2Common.mediumFriendlyAnimals);
      }

      if (uses > 200)
      {
         entityWhitelist.addAll(Ollivanders2Common.largeFriendlyAnimals);
      }

      // spell duration
      int durationInSeconds = (int) usesModifier;
      if (durationInSeconds < minDurationInSeconds)
      {
         durationInSeconds = minDurationInSeconds;
      }
      else if (durationInSeconds > maxDurationInSeconds)
      {
         durationInSeconds = maxDurationInSeconds;
      }

      spellDuration = durationInSeconds * Ollivanders2Common.ticksPerSecond;
   }
}
