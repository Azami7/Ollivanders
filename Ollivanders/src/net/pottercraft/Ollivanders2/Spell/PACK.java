package net.pottercraft.Ollivanders2.Spell;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import net.pottercraft.Ollivanders2.Ollivanders2API;
import net.pottercraft.Ollivanders2.Ollivanders2Common;
import net.pottercraft.Ollivanders2.StationarySpell.COLLOPORTUS;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.pottercraft.Ollivanders2.Ollivanders2;
import net.pottercraft.Ollivanders2.StationarySpell.StationarySpellObj;

import java.util.ArrayList;

/**
 * Pack is the incantation of a spell used to make items pack themselves into a trunk.
 *
 * @author cakenggt
 * @author Azami7
 */
public final class PACK extends Charms
{
   private int radius;
   private static int maxRadius = 20;

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public PACK ()
   {
      super();

      spellType = O2SpellType.PACK;

      flavorText = new ArrayList<String>() {{
         add("Books, clothes, telescope and scales all soared into the air and flew pell-mell into the trunk.");
         add("The Packing Charm");
      }};

      text = "When this hits a chest, it will suck any items nearby into it.";
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public PACK (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      spellType = O2SpellType.PACK;
      setUsesModifier();

      // world guard flags
      worldGuardFlags.add(DefaultFlag.CHEST_ACCESS);

      // remove chests from material blacklist
      for (Material chest : Ollivanders2Common.chests)
      {
         materialBlackList.remove(chest);
      }

      radius = ((int) usesModifier / 10) + 1;
      if (radius > maxRadius)
      {
         radius = maxRadius;
      }
   }

   @Override
   protected void doCheckEffect ()
   {
      if (!hasHitTarget())
      {
         return;
      }

      Block block = getTargetBlock();
      if (Ollivanders2Common.chests.contains(block.getType()))
      {
         for (StationarySpellObj stat : Ollivanders2API.getStationarySpells().getActiveStationarySpells())
         {
            if (stat instanceof COLLOPORTUS)
            {
               stat.flair(10);

               kill();
               return;
            }
         }

         Inventory inv = null;

         try
         {
            Chest c = (Chest) block.getState();
            inv = c.getInventory();
            if (inv.getHolder() instanceof DoubleChest)
            {
               inv = inv.getHolder().getInventory();
            }
         }
         catch (Exception e)
         {
            kill();
            return;
         }

         for (Item item : getItems(radius))
         {
            if (inv.addItem(item.getItemStack()).size() == 0)
            {
               item.remove();
            }
         }
      }

      kill();
   }
}