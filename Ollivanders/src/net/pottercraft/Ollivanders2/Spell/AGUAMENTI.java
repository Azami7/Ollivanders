package net.pottercraft.Ollivanders2.Spell;

import net.pottercraft.Ollivanders2.O2MagicBranch;
import net.pottercraft.Ollivanders2.Ollivanders2;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Spell which places a block of water against the targeted block.
 *
 * @since 2.2.4
 * @version Ollivanders2
 * @author Azami7
 */
public final class AGUAMENTI extends BlockTransfigurationSuper
{
   public O2SpellType spellType = O2SpellType.AGUAMENTI;

   protected ArrayList<String> flavorText = new ArrayList<String>() {{
      add("The Water-Making Spell conjures clean, drinkable water from the end of the wand.");
      add("The Water-Making Spell");
   }};

   protected String text = "Aguamenti will cause water to erupt against the surface you cast it on.";

   protected O2MagicBranch branch = O2MagicBranch.CHARMS;

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public AGUAMENTI () { }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public AGUAMENTI (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      transfigureType = Material.WATER;
      spellDuration = (int)(1200 * usesModifier);
      permanent = false;
      radius = 1;

      materialWhitelist.add(Material.AIR);
   }

   @Override
   protected Block getTargetBlock ()
   {
      Block center = getBlock();

      if (center.getType() != Material.AIR)
      {
         if (Ollivanders2.debug)
            p.getLogger().info("finding aguamenti block");

         // we want the air block before this block
         return location.subtract(super.vector).getBlock();
      }
      else
      {
         return null;
      }
   }
}
