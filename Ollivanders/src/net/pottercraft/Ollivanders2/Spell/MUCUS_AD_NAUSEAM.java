package net.pottercraft.Ollivanders2.Spell;

import java.util.ArrayList;
import java.util.List;

import net.pottercraft.Ollivanders2.Effect.MUCUS;
import net.pottercraft.Ollivanders2.Ollivanders2API;
import net.pottercraft.Ollivanders2.Ollivanders2Common;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.pottercraft.Ollivanders2.Ollivanders2;

/**
 * Adds a MUCUS_AD_NAUSEAM oeffect to the player
 *
 * @author lownes
 * @author Azami7
 */
public final class MUCUS_AD_NAUSEAM extends DarkArts
{
   private static int maxDurationInSeconds = 300;

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public MUCUS_AD_NAUSEAM ()
   {
      super();

      spellType = O2SpellType.MUCUS_AD_NAUSEAM;

      flavorText = new ArrayList<String>() {{
         add("The Curse of the Bogies");
      }};

      text = "Mucus Ad Nauseam will cause your opponent to drip with slime.";
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public MUCUS_AD_NAUSEAM (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      spellType = O2SpellType.MUCUS_AD_NAUSEAM;
      setUsesModifier();
   }

   @Override
   protected void doCheckEffect ()
   {
      List<LivingEntity> living = getLivingEntities(1.5);
      for (LivingEntity live : living)
      {
         if (live.getUniqueId() == player.getUniqueId())
            continue;

         if (live instanceof Player)
         {
            Player player = (Player) live;

            int durationInSeconds = ((int) usesModifier + 30);
            if (durationInSeconds > maxDurationInSeconds)
            {
               durationInSeconds = maxDurationInSeconds;
            }

            MUCUS effect = new MUCUS(p, durationInSeconds * Ollivanders2Common.ticksPerSecond, player.getUniqueId());
            Ollivanders2API.getPlayers().playerEffects.addEffect(effect);

            kill();
         }
      }

      if (hasHitTarget())
      {
         kill();
      }
   }
}