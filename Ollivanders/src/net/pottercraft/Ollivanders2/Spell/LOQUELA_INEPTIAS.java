package net.pottercraft.Ollivanders2.Spell;

import net.pottercraft.Ollivanders2.Effect.BABBLING;
import net.pottercraft.Ollivanders2.Effect.O2EffectType;
import net.pottercraft.Ollivanders2.O2MagicBranch;
import net.pottercraft.Ollivanders2.Ollivanders2API;
import net.pottercraft.Ollivanders2.Ollivanders2;
import net.pottercraft.Ollivanders2.Ollivanders2Common;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Babbling curse
 *
 * @since 2.2.7
 * @author Azami7
 */
public class LOQUELA_INEPTIAS extends Charms
{
   O2EffectType effect = O2EffectType.BABBLING;

   private static int maxDurationInSeconds = 300;

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public LOQUELA_INEPTIAS ()
   {
      super();

      branch = O2MagicBranch.DARK_ARTS;
      spellType = O2SpellType.LOQUELA_INEPTIAS;

      flavorText = new ArrayList<String>() {{
         add("\"He usually picked Harry to help him with these reconstructions; so far, Harry had been forced to play a simple Transylvanian villager whom Lockhart had cured of a Babbling Curse, a yeti with a head cold, and a vampire who had been unable to eat anything except lettuce since Lockhart had dealt with him.\"");
         add("The Babbling Curse");
      }};

      text = "Causes your target to speak nonsense for a period of time.";
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public LOQUELA_INEPTIAS (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      branch = O2MagicBranch.DARK_ARTS;
      spellType = O2SpellType.LOQUELA_INEPTIAS;
      setUsesModifier();

      // pass-through materials
      projectilePassThrough.remove(Material.WATER);
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

            BABBLING effect = new BABBLING(p, durationInSeconds * Ollivanders2Common.ticksPerSecond, player.getUniqueId());
            Ollivanders2API.getPlayers().playerEffects.addEffect(effect);

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