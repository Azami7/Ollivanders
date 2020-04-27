package net.pottercraft.ollivanders2.spell;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import net.pottercraft.ollivanders2.O2MagicBranch;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Parrot;

import net.pottercraft.ollivanders2.Ollivanders2;
import net.pottercraft.ollivanders2.Ollivanders2Common;

import java.util.ArrayList;

/**
 * Conjures a flock of birds from the tip of the wand.
 *
 * @author Azami7
 */
public final class AVIS extends O2Spell
{
   private int birdCount = 0;
   private int maxBirds = 2;

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public AVIS()
   {
      super();

      spellType = O2SpellType.AVIS;
      branch = O2MagicBranch.CHARMS;

      flavorText = new ArrayList<String>()
      {{
         add("The Bird-Conjuring Charm");
         add("Most of the class had already left, although several twittering yellow birds were still zooming around the room, all of Hermione's creation; nobody else had succeeded in conjuring so much as a feather from thin air.");
         add("\"Oh, hello, Harry ... I was just practicing.\" -Hermione Granger conjuring small golden birds just before sending them to attack Ron");
      }};

      text = "Causes one or more birds to fly out of the tip of your wand.";
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public AVIS (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);
      spellType = O2SpellType.AVIS;
      branch = O2MagicBranch.CHARMS;

      initSpell();

      // world guard flags
      worldGuardFlags.add(DefaultFlag.MOB_SPAWNING);
   }

   @Override
   void doInitSpell ()
   {
      if (usesModifier > 100)
         maxBirds += 10;
      else
         maxBirds += (int)usesModifier / 10;
   }

   /**
    * Shoot a stream of birds from the caster's wand
    */
   @Override
   public void checkEffect()
   {
      if (!checkSpellAllowed())
      {
         kill();
         return;
      }

      if (birdCount < maxBirds)
      {
         Parrot bird = (Parrot) location.getWorld().spawnEntity(location, EntityType.PARROT);

         int rand = Math.abs(Ollivanders2Common.random.nextInt() % 5);
         Parrot.Variant variant;
         if (rand == 0)
            variant = Parrot.Variant.CYAN;
         else if (rand == 1)
            variant = Parrot.Variant.GRAY;
         else if (rand == 2)
            variant = Parrot.Variant.BLUE;
         else if (rand == 3)
            variant = Parrot.Variant.GREEN;
         else
            variant = Parrot.Variant.RED;
         bird.setVariant(variant);

         birdCount++;
      }
      else
      {
         kill();
      }
   }
}