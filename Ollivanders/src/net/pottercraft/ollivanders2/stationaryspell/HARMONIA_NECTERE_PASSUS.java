package net.pottercraft.ollivanders2.stationaryspell;

import java.util.Map;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

import net.pottercraft.ollivanders2.Ollivanders2;
import net.pottercraft.ollivanders2.Ollivanders2API;
import net.pottercraft.ollivanders2.Ollivanders2Common;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

/**
 * Checks for entities going into a vanishing cabinet
 *
 * @author lownes
 */
public class HARMONIA_NECTERE_PASSUS extends StationarySpellObj implements StationarySpell
{
   private Location twin;
   private Set<UUID> teleported = new HashSet<>();

   private final String twinLabel = "Twin";

   /**
    * Simple constructor used for deserializing saved stationary spells at server start. Do not use to cast spell.
    *
    * @param plugin a callback to the MC plugin
    */
   public HARMONIA_NECTERE_PASSUS (Ollivanders2 plugin)
   {
      super(plugin);

      spellType = O2StationarySpellType.HARMONIA_NECTERE_PASSUS;
   }

   /**
    * Constructor
    *
    * @param plugin a callback to the MC plugin
    * @param pid the player who cast the spell
    * @param location the center location of the spell
    * @param type the type of this spell
    * @param radius the radius for this spell
    * @param duration the duration of the spell
    * @param twin the location of this cabinet's twin
    */
   public HARMONIA_NECTERE_PASSUS (Ollivanders2 plugin, UUID pid, Location location, O2StationarySpellType type, Integer radius,
                                   Integer duration, Location twin)
   {

      super(plugin, pid, location, type, radius, duration);

      spellType = O2StationarySpellType.HARMONIA_NECTERE_PASSUS;
      this.twin = twin;
   }

   @Override
   public void checkEffect ()
   {
      HARMONIA_NECTERE_PASSUS twinHarm = null;
      for (StationarySpellObj stat : Ollivanders2API.getStationarySpells().getActiveStationarySpells())
      {
         if (stat instanceof HARMONIA_NECTERE_PASSUS
               && stat.location.getBlock().equals(twin.getBlock()))
         {
            twinHarm = (HARMONIA_NECTERE_PASSUS) stat;
         }
      }
      if (twinHarm == null || !cabinetCheck(location.getBlock()))
      {
         kill();
         return;
      }
      for (Entity entity : location.getWorld().getEntities())
      {
         if (teleported.contains(entity.getUniqueId()))
         {
            if (!entity.getLocation().getBlock().equals(location.getBlock()))
            {
               teleported.remove(entity.getUniqueId());
            }
         }
         else
         {
            if (entity.getLocation().getBlock().equals(location.getBlock()))
            {
               twinHarm.teleport(entity);
            }
         }
      }
   }

   /**
    * Checks the integrity of the cabinet
    *
    * @param feet - The block at the player's feet if the player is standing in the cabinet
    * @return - True if the cabinet is whole, false if not
    */
   private boolean cabinetCheck (Block feet)
   {
      if (feet.getType() != Material.AIR && feet.getType() != Material.WALL_SIGN)
      {
         return false;
      }

      if (feet.getRelative(1, 0, 0).getType() == Material.AIR ||
            feet.getRelative(-1, 0, 0).getType() == Material.AIR ||
            feet.getRelative(0, 0, 1).getType() == Material.AIR ||
            feet.getRelative(0, 0, -1).getType() == Material.AIR ||
            feet.getRelative(1, 1, 0).getType() == Material.AIR ||
            feet.getRelative(-1, 1, 0).getType() == Material.AIR ||
            feet.getRelative(0, 1, 1).getType() == Material.AIR ||
            feet.getRelative(0, 1, -1).getType() == Material.AIR ||
            feet.getRelative(0, 2, 0).getType() == Material.AIR)
      {
         return false;
      }
      else
      {
         return true;
      }
   }

   /**
    * Send the entity to the twin cabinet.
    *
    * @param entity the entity being transported
    */
   private void teleport (Entity entity)
   {
      location.setPitch(entity.getLocation().getPitch());
      location.setYaw(entity.getLocation().getYaw());
      entity.teleport(location);
      teleported.add(entity.getUniqueId());
   }

   /**
    * Serialize all data specific to this spell so it can be saved.
    *
    * @return a map of the serialized data
    */
   @Override
   public Map<String, String> serializeSpellData ()
   {
      Ollivanders2Common o2c = new Ollivanders2Common(p);

      return o2c.serializeLocation(location, twinLabel);
   }

   /**
    * Deserialize the data for this spell and load the data to this spell.
    *
    * @param spellData a map of the saved spell data
    */
   @Override
   public void deserializeSpellData (Map<String, String> spellData)
   {
      Ollivanders2Common o2c = new Ollivanders2Common(p);

      Location loc = o2c.deserializeLocation(spellData, twinLabel);

      if (loc != null)
         twin = loc;
   }
}