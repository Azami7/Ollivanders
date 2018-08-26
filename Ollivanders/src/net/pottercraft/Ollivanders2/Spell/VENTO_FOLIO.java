package net.pottercraft.Ollivanders2.Spell;

import net.pottercraft.Ollivanders2.*;
import net.pottercraft.Ollivanders2.Effect.FLYING;
import net.pottercraft.Ollivanders2.Effect.O2EffectType;
import net.pottercraft.Ollivanders2.Player.O2Player;
import org.bukkit.entity.Player;

/**
 * Give the caster the ability to fly. Tom Riddle is the first wizard known to achieve unassisted flying and
 * only one other wizard learned it from him, Severus Snape. Unassisted flying is against magical law.
 *
 * @author Azami7
 * @since 2.2.8
 */
public final class VENTO_FOLIO extends Charms
{
   /**
    * The percent chance this spell will succeed each casting.
    */
   protected int successRate = 0;

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public VENTO_FOLIO ()
   {
      super();

      text = "Vento Folio gives the caster the ability to fly unassisted for an amount of time.";
      flavorText.add("\"And then Harry saw him. Voldemort was flying like smoke on the wind, without broomstick or thestral to hold him, his snake-like face gleaming out of the blackness, his white fingers raising his wand again —\"");
      flavorText.add("\"Remus, he can -\"\n\"Fly, I saw him too, he came after Hagrid and me.\" -Kingsley Shacklebolt and Harry Potter");

      branch = O2MagicBranch.DARK_ARTS;
   }

   /**
    * Constructor for casting the spell.
    *
    * @param plugin
    * @param player
    * @param name
    * @param rightWand
    */
   public VENTO_FOLIO (Ollivanders2 plugin, Player player, Spells name, Double rightWand)
   {
      super(plugin, player, name, rightWand);

      setSuccessRate();
   }

   /**
    * Set the successRate for this spell. If the player is not already an Animagus the spell needs to be
    * recited at the correct time of day. If they are already an Animagus, their success will depend
    * on their experience.
    */
   private void setSuccessRate ()
   {
      // set success rate based on their experience
      int uses = (int)(usesModifier * 10);

      if (uses >= 200)
         successRate = 100;
      else if (uses >= 100)
         successRate = uses / 2;
      else if (uses >= 50)
         successRate = 25;
      else if (uses >= 25)
         successRate = 10;
      else
         successRate = 5;
   }

   @Override
   public void checkEffect ()
   {
      kill();

      O2Player o2p = p.getO2Player(player);

      int rand = Math.abs(Ollivanders2.random.nextInt() % 100);
      int duration;

      int uses = (int)(usesModifier * 100);
      if (uses >= 100)
      {
         // > 30 seconds
         duration = uses + 300;
      }
      else if (uses >= 50)
      {
         // 30 seconds
         duration = 300;
      }
      else if (uses >= 10)
      {
         // 10 seconds
         duration = 200;
      }
      else // < 10
      {
         // 5 seconds
         duration = 100;
      }

      if (rand < successRate)
      {
         o2p.addEffect(new FLYING(p, O2EffectType.FLYING, duration));

         if (Ollivanders2.debug)
            p.getLogger().info("Adding effect ");
      }
   }
}
