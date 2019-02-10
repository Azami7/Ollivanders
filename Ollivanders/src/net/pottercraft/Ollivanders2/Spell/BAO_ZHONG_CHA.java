package net.pottercraft.Ollivanders2.Spell;

import net.pottercraft.Ollivanders2.Divination.O2DivinationType;
import net.pottercraft.Ollivanders2.Item.O2ItemType;
import net.pottercraft.Ollivanders2.Ollivanders2;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Tasseomancy is the art of reading tea leaves to predict events in the future.
 * http://harrypotter.wikia.com/wiki/Tessomancy
 *
 * @author Azami7
 * @since 2.2.9
 */
public class BAO_ZHONG_CHA extends Divination
{
   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public BAO_ZHONG_CHA ()
   {
      super();

      spellType = O2SpellType.BAO_ZHONG_CHA;
      divinationType = O2DivinationType.TASSEOMANCY;

      flavorText = new ArrayList<String>()
      {{
         add("\"Now, I want you all to divide into pairs. Collect a teacup from the shelf, come to me, and I will fill it. Then sit down and drink; drink until only the dregs remain. Swirl these around the cup three times with the left hand, then turn the cup upside-down on its saucer; wait for the last of the tea to drain away, then give your cup to your partner to read.\" -Sybill Trelawney");
         add("\"Oh, and dear, after you’ve broken your first cup, would you be so kind as to select one of the blue patterned ones? I’m rather attached to the pink.\" -Sybill Trelawney");
         add("Everyone was staring, transfixed at Professor Trelawney, who gave the cup a final turn, gasped, and then screamed.");
      }};

      text = "The ancient Chinese practice of studying tea leaves will allow one with the gift of sight to understand the future.";
   }

   /**
    * Constructor.
    *
    * @param plugin    a callback to the MC plugin
    * @param player    the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public BAO_ZHONG_CHA (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      spellType = O2SpellType.BAO_ZHONG_CHA;
      divinationType = O2DivinationType.TASSEOMANCY;

      facingBlock = Material.CAULDRON;
      facingBlockString = "a cauldron";

      itemHeld = O2ItemType.TEA_LEAVES;
      itemHeldString = "tea leaves";
      consumeHeld = true;

      setUsesModifier();
   }
}
