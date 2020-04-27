package net.pottercraft.ollivanders2.spell;

import net.pottercraft.ollivanders2.effect.O2EffectType;
import net.pottercraft.ollivanders2.O2MagicBranch;
import net.pottercraft.ollivanders2.Ollivanders2;
import net.pottercraft.ollivanders2.Ollivanders2API;
import net.pottercraft.ollivanders2.Ollivanders2Common;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Reparifors is a healing spell that reverts minor magically-induced ailments, such as paralysis and poisoning.
 * http://harrypotter.wikia.com/wiki/Reparifors
 *
 * @author Azami7
 * @since 2.2.9
 */
public class REPARIFORS extends O2Spell
{
   public REPARIFORS()
   {
      super();

      spellType = O2SpellType.REPARIFORS;
      branch = O2MagicBranch.CHARMS;

      text = "A healing spell for minor ailments such as paralysis or poisoning.";
   }

   public REPARIFORS (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      spellType = O2SpellType.REPARIFORS;
      branch = O2MagicBranch.CHARMS;

      initSpell();
   }

   @Override
   protected void doCheckEffect ()
   {
      for (LivingEntity live : getLivingEntities(1.5))
      {
         if (live.getUniqueId() == player.getUniqueId())
         {
            continue;
         }

         if (live instanceof Player)
         {
            Player player = (Player) live;

            // if they are affected by immobilize, remove the effect
            if (Ollivanders2API.getPlayers().playerEffects.hasEffect(player.getUniqueId(), O2EffectType.IMMOBILIZE) && !(Ollivanders2API.getPlayers().playerEffects.hasEffect(player.getUniqueId(), O2EffectType.SUSPENSION)))
            {
               Ollivanders2API.getPlayers().playerEffects.ageEffectByPercent(player.getUniqueId(), O2EffectType.IMMOBILIZE, (int) (usesModifier / 20));

               kill();
               return;
            }

            // reduce duration of poison by half
            if (player.hasPotionEffect(PotionEffectType.POISON))
            {
               int duration = player.getPotionEffect(PotionEffectType.POISON).getDuration();
               player.removePotionEffect(PotionEffectType.POISON);
               player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (duration / 2), 1), true);

               kill();
               return;
            }

            // do a minor heal
            int duration = (((int) usesModifier / 10) * Ollivanders2Common.ticksPerSecond) + (15 * Ollivanders2Common.ticksPerSecond);
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, duration, 1), true);

            kill();
            return;
         }
      }

      if (hasHitTarget())
      {
         kill();
      }
   }
}