package net.pottercraft.ollivanders2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.UUID;

import net.pottercraft.ollivanders2.effect.O2EffectType;
import net.pottercraft.ollivanders2.player.O2Player;
import net.pottercraft.ollivanders2.spell.GEMINIO;
import net.pottercraft.ollivanders2.spell.O2Spell;
import net.pottercraft.ollivanders2.stationaryspell.StationarySpellObj;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.pottercraft.ollivanders2.stationaryspell.REPELLO_MUGGLETON;
import net.pottercraft.ollivanders2.spell.O2SpellType;
import org.jetbrains.annotations.NotNull;

/**
 * Scheduler for Ollivanders2
 *
 * @author lownes
 */
class OllivandersSchedule implements Runnable
{
   final private Ollivanders2 p;
   private int counter = 0;
   final private static Set<UUID> flying = new HashSet<>();
   final private Set<UUID> onBroom = new HashSet<>();

   OllivandersSchedule (@NotNull Ollivanders2 plugin)
   {
      p = plugin;
   }

   public void run ()
   {
      try
      {
         projectileSched();
         oeffectSched();
         Ollivanders2API.getStationarySpells(p).upkeep();
         Ollivanders2API.getProphecies(p).upkeep();
         broomSched();
         teleportSched();
      }
      catch (Exception e)
      {
         if (Ollivanders2.debug)
            e.printStackTrace();
      }

      if (counter % Ollivanders2Common.ticksPerSecond == 0)
      {
         itemCurseSched();
      }
      if (counter % Ollivanders2Common.ticksPerSecond == 1)
      {
         invisPlayer();
      }

      counter = (counter + 1) % Ollivanders2Common.ticksPerSecond;
   }

   /**
    * Scheduling method that calls checkEffect() on all SpellProjectile objects
    * and removes those that have kill set to true.
    */
   private void projectileSched ()
   {
      List<O2Spell> projectiles = p.getProjectiles();
      List<O2Spell> projectiles2 = new ArrayList<>(projectiles);
      if (projectiles2.size() > 0)
      {
         for (O2Spell proj : projectiles2)
         {
            proj.checkEffect();
            if (proj.isKilled())
            {
               p.removeProjectile(proj);
            }
         }
      }
   }

   /**
    * Scheduling method that calls checkEffect on all OEffect objects associated with every online player
    * and removes those that have kill set to true.
    */
   private void oeffectSched ()
   {
      List<Player> onlinePlayers = new ArrayList<>();

      for (World world : p.getServer().getWorlds())
      {
         onlinePlayers.addAll(world.getPlayers());
      }

      for (Player player : onlinePlayers)
      {
         UUID pid = player.getUniqueId();

         Ollivanders2API.getPlayers(p).playerEffects.upkeep(pid);
      }
   }

   /**
    * Scheduling method that checks for any curses on items in player inventories and
    * performs their effect.
    */
   private void itemCurseSched ()
   {
      for (World world : p.getServer().getWorlds())
      {
         for (Player player : world.getPlayers())
         {
            List<ItemStack> geminioIS = new ArrayList<>();
            ListIterator<ItemStack> invIt = player.getInventory().iterator();
            while (invIt.hasNext())
            {
               ItemStack item = invIt.next();
               if (item != null)
               {
                  ItemMeta meta = item.getItemMeta();
                  if (meta == null)
                     continue;

                  if (meta.hasLore())
                  {
                     List<String> itemLore = meta.getLore();
                     if (itemLore == null)
                        continue;

                     for (String lore : itemLore)
                     {
                        if (lore.contains(GEMINIO.geminio))
                        {
                           geminioIS.add(geminio(item.clone()));
                           invIt.set(null);
                        }
                     }
                  }
               }
            }
            HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(geminioIS.toArray(new ItemStack[geminioIS.size()]));
            for (ItemStack item : leftover.values())
            {
               player.getWorld().dropItem(player.getLocation(), item);
            }
         }
      }
   }

   /**
    * Enacts the geminio duplicating effect on an itemstack
    *
    * @param item - item with geminio curse on it
    * @return Duplicated itemstacks
    * @assumes item stack being passed is a Geminio
    */
   @NotNull
   private ItemStack geminio (@NotNull ItemStack item)
   {
      int stackSize = item.getAmount();
      ItemMeta meta = item.getItemMeta();
      if (meta == null)
      {
         p.getLogger().warning("Ollivanders2Schedule.geminio: item meta is null");
         return item;
      }

      List<String> lore = meta.getLore();
      if (lore == null)
      {
         // this should not happen if the item stack being sent is a Geminio
         return item;
      }

      ArrayList<String> newLore = new ArrayList<>();
      for (String l : lore)
      {
         if (l.contains(GEMINIO.geminio))
         {
            String[] loreParts = l.split(" ");
            int magnitude = Integer.parseInt(loreParts[1]);
            if (magnitude > 1)
            {
               magnitude--;
               newLore.add(GEMINIO.geminio + " " + magnitude);
            }
            stackSize = stackSize * 2;
         }
         else
         {
            newLore.add(l);
         }
      }
      meta.setLore(newLore);
      item.setItemMeta(meta);
      item.setAmount(stackSize);
      return item;
   }

   /**
    * Hides a player with the Cloak of Invisibility from other players.
    * Also hides players in Repello Muggleton from players not in that same spell.
    * Also sets any Creature targeting this player to have null target.
    */
   private void invisPlayer ()
   {
      Set<REPELLO_MUGGLETON> repelloMuggletons = new HashSet<>();
      for (StationarySpellObj stat : Ollivanders2API.getStationarySpells(p).getActiveStationarySpells())
      {
         if (stat instanceof REPELLO_MUGGLETON)
         {
            repelloMuggletons.add((REPELLO_MUGGLETON) stat);
         }
      }

      for (Player player : p.getServer().getOnlinePlayers())
      {
         O2Player o2p = p.getO2Player(player);
         if (o2p == null)
            continue;

         boolean alreadyInvis = o2p.isInvisible();
         boolean alreadyInRepelloMuggleton = o2p.isInRepelloMuggleton();
         boolean hasCloak = hasCloak(player);

         if (hasCloak)
         {
            if (!alreadyInvis)
            {
               o2p.setInvisible(true);
               player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
            }
         }

         boolean inRepelloMuggletons = false;
         for (StationarySpellObj stat : repelloMuggletons)
         {
            if (stat.isInside(player.getLocation()))
            {
               inRepelloMuggletons = true;
               if (!alreadyInRepelloMuggleton)
               {
                  o2p.setInRepelloMuggleton(true);
               }
               break;
            }
         }

         //TODO check the logic on this, I am not sure it is right
         if (hasCloak || inRepelloMuggletons)
         {
            for (Player player2 : p.getServer().getOnlinePlayers()) {
               if (player2.isPermissionSet("Ollivanders2.BYPASS") && player2.hasPermission("Ollivanders2.BYPASS")) {
                  continue;
               }

               O2Player viewer = p.getO2Player(player2);
               if (viewer == null)
                  continue;

               if (hasCloak)
               {
                  player2.hidePlayer(p, player);
                  System.out.println(player2.canSee(player));
               }
               else if (viewer.isMuggle())
               {
                  player2.hidePlayer(p, player);
               }
            }
         }
         else if (!hasCloak && alreadyInvis) {
            for (Player player2 : p.getServer().getOnlinePlayers())
            {
               O2Player viewer = Ollivanders2API.getPlayers(p).getPlayer(player2.getUniqueId());
               if (viewer == null)
                  continue;

               if (!inRepelloMuggletons && viewer.isMuggle()) {
                  player2.showPlayer(p, player);
               }
            }
            o2p.setInvisible(false);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
         }
         else if (!inRepelloMuggletons && alreadyInRepelloMuggleton) {
            if (!hasCloak) {
               for (Player player2 : p.getServer().getOnlinePlayers()) {
                  player2.showPlayer(p, player);
               }
            }
            o2p.setInRepelloMuggleton(false);
         }

         if (o2p.isInvisible()) {
            for (Entity entity : player.getWorld().getEntities()) {
               if (entity instanceof Creature) {
                  Creature creature = (Creature) entity;
                  if (creature.getTarget() == player) {
                     creature.setTarget(null);
                  }
               }
            }
         }

         p.setO2Player(player, o2p);
      }
   }

   /**
    * Does the player have the Cloak of Invisibility
    *
    * @param player - Player to be checked
    * @return - True if yes, false if no
    */
   private boolean hasCloak (@NotNull Player player)
   {
      ItemStack chestPlate = player.getInventory().getChestplate();
      if (chestPlate != null)
      {
         return Ollivanders2API.common.isInvisibilityCloak(chestPlate);
      }
      return false;
   }

   /**
    * Goes through all players and sets any holding a broom to flying
    */
   private void broomSched ()
   {
      playerIter:
      for (World world : p.getServer().getWorlds())
      {
         for (Player player : world.getPlayers())
         {
            if (Ollivanders2API.common.isBroom(player.getInventory().getItemInMainHand()) && p.isSpellTypeAllowed(player.getLocation(), O2SpellType.VOLATUS))
            {
               player.setAllowFlight(true);
               player.setFlying(true);
               this.onBroom.add(player.getUniqueId());
               if (flying.contains(player.getUniqueId()))
               {
                  Vector broomVec = player.getLocation().getDirection().clone();
                  broomVec.multiply(Math.sqrt(player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.PROTECTION_FALL)) / 40.0);
                  player.setVelocity(player.getVelocity().add(broomVec));
               }
            }
            else
            {
               if (player.getGameMode() == GameMode.SURVIVAL && (this.onBroom.contains(player.getUniqueId())))
               {
                  if (Ollivanders2API.getPlayers(p).playerEffects.hasEffect(player.getUniqueId(), O2EffectType.FLYING))
                  {
                     continue playerIter;
                  }
                  player.setFlying(false);
                  this.onBroom.remove(player.getUniqueId());
               }
            }
         }
      }
   }

   /**
    * Handle all teleport events.
    */
   private void teleportSched()
   {
      Ollivanders2TeleportEvents.O2TeleportEvent[] teleportEvents = p.getTeleportEvents();

      for (Ollivanders2TeleportEvents.O2TeleportEvent event : teleportEvents)
      {
         Player player = event.getPlayer();

         if (Ollivanders2.debug)
            p.getLogger().info("Teleporting " + player.getName());

         Location currentLocation = event.getFromLocation();
         Location destination = event.getToLocation();
         destination.setPitch(currentLocation.getPitch());
         destination.setYaw(currentLocation.getYaw());

         try
         {
            player.teleport(destination);

            World curWorld = currentLocation.getWorld();
            World destWorld = destination.getWorld();
            if (curWorld == null || destWorld == null)
            {
               p.getLogger().warning("OllvandersSchedule.teleportSched: world is null");
            }
            else
            {
               if (event.isExplosionOnTeleport())
               {
                  curWorld.createExplosion(currentLocation, 0);
                  destWorld.createExplosion(destination, 0);
               }
            }
         }
         catch (Exception e)
         {
            p.getLogger().warning("Failed to teleport player.");
            if (Ollivanders2.debug)
               e.printStackTrace();
         }

         p.removeTeleportEvent(event);
      }
   }

   @NotNull
   public static Set<UUID> getFlying()
   {
      return flying;
   }
}