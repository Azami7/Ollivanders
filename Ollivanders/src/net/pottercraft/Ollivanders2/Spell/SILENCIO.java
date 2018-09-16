package net.pottercraft.Ollivanders2.Spell;

import java.util.ArrayList;
import java.util.List;

import net.pottercraft.Ollivanders2.*;
import net.pottercraft.Ollivanders2.Effect.O2EffectType;
import net.pottercraft.Ollivanders2.Player.O2Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Silences a player for a duration depending on the spell's level. The target player can only use nonverbal spells.
 *
 * @author lownes
 * @author Azami7
 */
public final class SILENCIO extends Charms
{
   public O2SpellType spellType = O2SpellType.SILENCIO;

   protected ArrayList<String> flavorText = new ArrayList<String>() {{
      add("The raven continued to open and close its sharp beak, but no sound came out.");
      add("The Silencing Charm");
   }};

   protected String text = "Mutes the target for a time.";

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public SILENCIO () { }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public SILENCIO (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);
   }

   @Override
   public void checkEffect ()
   {
      move();
      List<LivingEntity> living = getLivingEntities(2);
      for (LivingEntity live : living)
      {
         if (live instanceof Player)
         {
            Player player = (Player) live;
            O2Player o2p = p.getO2Player(player);
            int dur = (int) (usesModifier * 1200);
            o2p.addEffect(new net.pottercraft.Ollivanders2.Effect.SILENCIO(p, O2EffectType.SILENCIO, dur, player));
            p.setO2Player(player, o2p);
            kill();
            return;
         }
      }
   }
}