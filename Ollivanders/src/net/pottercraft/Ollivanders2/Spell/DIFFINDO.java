package net.pottercraft.Ollivanders2.Spell;

import java.util.ArrayList;
import java.util.List;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import net.pottercraft.Ollivanders2.Ollivanders2;
import net.pottercraft.Ollivanders2.Ollivanders2API;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Drops random items from a player's inventory. Also cuts down trees.
 *
 * @author lownes
 * @author Azami7
 */
public final class DIFFINDO extends Charms
{
   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public DIFFINDO ()
   {
      super();

      spellType = O2SpellType.DIFFINDO;

      flavorText = new ArrayList<String>() {{
         add("The Severing Charm");
         add("With the Severing Charm, cutting or tearing objects is a simple matter of wand control.");
         add("The spell can be quite precise in skilled hands, and the Severing Charm is widely used in a variety of wizarding trades.");
      }};

      text = "Breaks logs in a radius or drops items from a player’s inventory.";
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public DIFFINDO (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);
      spellType = O2SpellType.DIFFINDO;

      // set up usage modifier, has to be done here to get the uses for this specific spell
      setUsesModifier();

      // world guard flags
      worldGuardFlags.add(DefaultFlag.PVP);
      worldGuardFlags.add(DefaultFlag.BUILD);
      worldGuardFlags.add(DefaultFlag.ITEM_DROP);
   }

   /**
    * Split a log or a player's inventory
    */
   @Override
   protected void doCheckEffect ()
   {
      // first check for players
      List<LivingEntity> livingEntities = getLivingEntities(1.5);

      if (livingEntities.size() > 0)
      {
         for (LivingEntity live : livingEntities)
         {
            if (live.getUniqueId() == player.getUniqueId())
               continue;

            if (live instanceof Player)
            {
               PlayerInventory inv = ((Player) live).getInventory();
               ArrayList<ItemStack> remStack = new ArrayList<>();
               for (ItemStack stack : inv.getContents())
               {
                  if (stack != null)
                  {
                     if (Math.random() * usesModifier > 1)
                     {
                        remStack.add(stack);
                     }
                  }
               }
               for (ItemStack rem : remStack)
               {
                  inv.remove(rem);
                  live.getWorld().dropItemNaturally(live.getLocation(), rem);
               }

               break;
            }
         }

         kill();
         return;
      }

      // next check for log
      if (hasHitTarget())
      {
         Block target = getTargetBlock();

         if (Ollivanders2API.common.isNaturalLog(target))
         {
            for (Block nearbyBlock : Ollivanders2API.common.getBlocksInRadius(location, usesModifier))
            {
               if (Ollivanders2API.common.isNaturalLog(nearbyBlock))
               {
                  nearbyBlock.breakNaturally();
               }
            }
         }

         kill();
      }
   }
}