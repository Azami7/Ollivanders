package net.pottercraft.Ollivanders2.Spell;

import java.util.ArrayList;
import java.util.List;

import net.pottercraft.Ollivanders2.Ollivanders2;

import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Slows down any item or living entity according to your level in the spell.
 *
 * @version Ollivanders2
 * @author lownes
 * @author Azami7
 */
public final class ARRESTO_MOMENTUM extends Charms
{
   public O2SpellType spellType = O2SpellType.ARRESTO_MOMENTUM;

   protected ArrayList<String> flavorText = new ArrayList<String>() {{
      add("An incantation for slowing velocity.");
      add("\"Dumbledore ...ran onto the field as you fell, waved his wand, and you sort of slowed down before you hit the ground.\" - Hermione Granger");
      add("The witch Daisy Pennifold had the idea of bewitching the Quaffle so that if dropped, it would fall slowly earthwards as though sinking through water, meaning that Chasers could grab it in mid-air.");
   }};

   protected String text = "Arresto Momentum will immediately slow down any entity or item.";

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public ARRESTO_MOMENTUM () { }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public ARRESTO_MOMENTUM (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);
   }

   public void checkEffect ()
   {
      move();
      double modifier = usesModifier;
      List<LivingEntity> entities = getLivingEntities(2);
      for (LivingEntity entity : entities)
      {
         double speed = entity.getVelocity().length() / (Math.pow(modifier, 2));
         entity.setVelocity(entity.getVelocity().normalize().multiply(speed));
         entity.setFallDistance((float) (entity.getFallDistance() / modifier));
         kill = true;
         return;
      }
      List<Item> items = getItems(1);
      for (Item item : items)
      {
         double speed = item.getVelocity().length() / (Math.pow(modifier, 2));
         item.setVelocity(item.getVelocity().normalize().multiply(speed));
         kill = true;
         return;
      }
   }
}