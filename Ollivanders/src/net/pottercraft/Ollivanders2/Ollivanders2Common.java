package net.pottercraft.Ollivanders2;

import net.pottercraft.Ollivanders2.Spell.Spells;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Ocelot;
import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Common functions and data
 *
 * @since 2.2.6
 * @author Azami7
 */
public class Ollivanders2Common
{
   public static final ArrayList<EntityType> smallFriendlyAnimals = new ArrayList<EntityType>() {{
      add(EntityType.BAT);
      add(EntityType.CHICKEN);
      add(EntityType.RABBIT);
      add(EntityType.PARROT);
   }};

   public static final ArrayList<EntityType> mediumFriendlyAnimals = new ArrayList<EntityType>() {{
      add(EntityType.SHEEP);
      add(EntityType.PIG);
      add(EntityType.OCELOT);
      add(EntityType.WOLF);
   }};

   public static final ArrayList<EntityType> largeFriendlyAnimals = new ArrayList<EntityType>() {{
      add(EntityType.COW);
      add(EntityType.DONKEY);
      add(EntityType.HORSE);
      add(EntityType.MUSHROOM_COW);
      add(EntityType.IRON_GOLEM);
      add(EntityType.SNOWMAN);
      add(EntityType.MULE);
      add(EntityType.SQUID);
      add(EntityType.POLAR_BEAR);
      add(EntityType.LLAMA);
      add(EntityType.SHULKER); // not large in size but complex
   }};

   public static final ArrayList<Spells> libsDisguisesSpells = new ArrayList<Spells>() {{
      add(Spells.AMATO_ANIMO_ANIMATO_ANIMAGUS);
      add(Spells.AVIFORS);
      add(Spells.DRACONIFORS);
      add(Spells.ENTOMORPHIS);
      add(Spells.EQUUSIFORS);
      add(Spells.INCARNATIO_DEVITO);
      add(Spells.INCARNATIO_EQUUS);
      add(Spells.INCARNATIO_LAMA);
      add(Spells.INCARNATIO_LUPI);
      add(Spells.INCARNATIO_PORCILLI);
      add(Spells.INCARNATIO_URSUS);
      add(Spells.INCARNATIO_VACCULA);
      add(Spells.LAPIFORS);
      add(Spells.SNUFFLIFORS);
      add(Spells.VERA_VERTO);
   }};

   Ollivanders2 p;

   public Ollivanders2Common (Ollivanders2 plugin)
   {
      p = plugin;
   }

   /**
    * Create a UUID from a string.
    *
    * @param uuid
    * @return the UUID or null if an exception occurred.
    */
   public UUID uuidFromString (String uuid)
   {
      UUID pid = null;

      try
      {
         pid = UUID.fromString(uuid);
      }
      catch (Exception e)
      {
         p.getLogger().warning("Failed to parse uuid " + uuid);
         if (p.debug)
            e.printStackTrace();
      }

      return pid;
   }

   /**
    * Get an integer from a string.
    *
    * @param intString
    * @return
    */
   public Integer integerFromString (String intString)
   {
      Integer i = null;

      try
      {
         int p = Integer.parseInt(intString);
         i = new Integer(p);
      }
      catch (Exception e)
      {
         p.getLogger().warning("Failed to parse integer " + intString);
         if (p.debug)
            e.printStackTrace();
      }

      return i;
   }

   /**
    * Get a boolean from a string.
    *
    * @param boolString
    * @return
    */
   public Boolean booleanFromString (String boolString)
   {
      Boolean b = null;

      try
      {
         b = Boolean.valueOf(boolString);
      }
      catch (Exception e)
      {
         p.getLogger().warning("Failed to parse boolean " + boolString);
         if (p.debug)
            e.printStackTrace();
      }

      return b;
   }

   /**
    * Get a Spells enum from a string.
    *
    * @param spellString
    * @return
    */
   public Spells spellsFromString (String spellString)
   {
      Spells spell = null;

      try
      {
         spell = Spells.valueOf(spellString);
      }
      catch (Exception e)
      {
         p.getLogger().warning("Failed to parse spell " + spellString);
         if (p.debug)
            e.printStackTrace();
      }

      return spell;
   }

   /**
    * Get an EntityType enum from a string.
    *
    * @param entityTypeString
    * @return
    */
   public EntityType entityTypeFromString (String entityTypeString)
   {
      EntityType entityType = null;

      try
      {
         entityType = EntityType.valueOf(entityTypeString);
      }
      catch (Exception e)
      {
         p.getLogger().warning("Failed to parse EntityType " + entityTypeString);
         if (p.debug)
            e.printStackTrace();
      }
      return entityType;
   }

   /**
    * Determine if a location is within a radius of another location
    * @param loc1 the source location
    * @param loc2 the location to check
    * @param radius the radius from the source location
    * @return
    */
   public static boolean isInside (OLocation loc1, Location loc2, int radius)
   {
      double distance;
      try
      {
         distance = loc2.distance(loc1.toLocation());
      } catch (IllegalArgumentException e)
      {
         return false;
      }
      if (distance < radius)
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   /**
    * Generate a random Ocelot type.
    *
    * @return
    */
   public Ocelot.Type randomOcelotType ()
   {
      int rand = Math.abs(Ollivanders2.random.nextInt() % 4);

      Ocelot.Type type;

      switch (rand)
      {
         case 1:
           type = Ocelot.Type.BLACK_CAT;
           break;
         case 2:
            type = Ocelot.Type.RED_CAT;
            break;
         case 3:
            type = Ocelot.Type.SIAMESE_CAT;
            break;
         default:
            type = Ocelot.Type.WILD_OCELOT;
            break;
      }

      return type;
   }

   /**
    * Generate a random primary or secondary color - red, orange, yellow, green, blue, purple
    * @return
    */
   public DyeColor randomSecondaryDyeColor ()
   {
      DyeColor color;

      int rand = Math.abs(Ollivanders2.random.nextInt() % 4);

      switch (rand)
      {
         case 1:
            color = DyeColor.RED;
            break;
         case 2:
            color = DyeColor.ORANGE;
            break;
         case 3:
            color = DyeColor.YELLOW;
            break;
         case 4:
            color = DyeColor.GREEN;
            break;
         case 5:
            color = DyeColor.BLUE;
            break;
         default:
            color = DyeColor.PURPLE;
            break;
      }

      return color;
   }

   /**
    * Generate a random horse style.
    *
    * @return
    */
   public Horse.Style randomHorseStyle ()
   {
      Horse.Style style;

      int rand = Math.abs(Ollivanders2.random.nextInt() % 20);

      switch (rand)
      {
         case 1:
            style = Horse.Style.WHITE;
            break;
         case 2:
            style = Horse.Style.WHITE_DOTS;
            break;
         case 3:
            style = Horse.Style.WHITEFIELD;
            break;
         case 4:
            style = Horse.Style.BLACK_DOTS;
            break;
         default:
            style = Horse.Style.NONE;
            break;
      }

      return style;
   }

   /**
    * Generate a random horse color.
    *
    * @return
    */
   public Horse.Color randomHorseColor ()
   {
      Horse.Color color;

      int rand = Math.abs(Ollivanders2.random.nextInt() % 7);

      switch (rand)
      {
         case 1:
            color = Horse.Color.BLACK;
            break;
         case 2:
            color = Horse.Color.BROWN;
            break;
         case 3:
            color = Horse.Color.CHESTNUT;
            break;
         case 4:
            color = Horse.Color.CREAMY;
            break;
         case 5:
            color = Horse.Color.DARK_BROWN;
            break;
         case 6:
            color = Horse.Color.GRAY;
            break;
         default:
            color = Horse.Color.WHITE;
            break;
      }

      return color;
   }

   /**
    * Generate random llama color.
    *
    * @return
    */
   public Llama.Color randomLlamaColor ()
   {
      Llama.Color color;

      int rand = Math.abs(Ollivanders2.random.nextInt() % 4);

      switch (rand)
      {
         case 1:
            color = Llama.Color.BROWN;
            break;
         case 2:
            color = Llama.Color.CREAMY;
            break;
         case 3:
            color = Llama.Color.GRAY;
            break;
         default:
            color = Llama.Color.WHITE;
            break;
      }

      return color;
   }
}