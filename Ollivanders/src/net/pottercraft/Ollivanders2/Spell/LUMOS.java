package net.pottercraft.Ollivanders2.Spell;

import net.pottercraft.Ollivanders2.Effect.O2EffectType;
import net.pottercraft.Ollivanders2.Ollivanders2;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

/**
 * Gives night vision for an amount of time depending on the player's spell level.
 *
 * @author lownes
 * @author Azami7
 */
public final class LUMOS extends O2EffectOnSelf
{
   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public LUMOS ()
   {
      super();

      spellType = O2SpellType.LUMOS;

      flavorText = new ArrayList<String>() {{
         add("If in any doubt about your abilities you would do better to buy yourself a magic lantern.");
         add("The Wand-Lighting Charm");
         add("\"Ron, where are you? Oh this is stupid - lumos!\"  She illuminated her wand and directed its narrow beam across the path. Ron was lying sprawled on the ground.");
         add("The Wand-Lighting Charm is simple, but requires concentration.  Take care not to accidentally set your wand alight as damage of this kind can be permanent.");
      }};

      text = "Gives night vision.";
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public LUMOS (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      spellType = O2SpellType.LUMOS;
      setUsesModifier();

      effectsToAdd.add(O2EffectType.NIGHT_VISION);
      strengthModifier = 1;
      minDurationInSeconds = 30;

      durationInSeconds = (int) usesModifier;
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