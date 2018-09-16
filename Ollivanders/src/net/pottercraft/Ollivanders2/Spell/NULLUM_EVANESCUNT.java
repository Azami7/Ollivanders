package net.pottercraft.Ollivanders2.Spell;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.pottercraft.Ollivanders2.Ollivanders2;
import net.pottercraft.Ollivanders2.StationarySpell.StationarySpells;

/**
 * Makes an anti-disapparition spell. Players can't apparate out of it.
 *
 * @author lownes
 */
public final class NULLUM_EVANESCUNT extends Charms
{
   public O2SpellType spellType = O2SpellType.NULLUM_EVANESCUNT;

   protected String text = "Nullum evanescunt creates a stationary spell which will not allow disapparition out of it.";

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public NULLUM_EVANESCUNT () { }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public NULLUM_EVANESCUNT (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);
   }

   public void checkEffect ()
   {
      move();
      if (getBlock().getType() != Material.AIR && getBlock().getType() != Material.FIRE && getBlock().getType() != Material.WATER && getBlock().getType() != Material.STATIONARY_WATER)
      {
         int duration = (int) (usesModifier * 1200);
         net.pottercraft.Ollivanders2.StationarySpell.NULLUM_EVANESCUNT nullum = new net.pottercraft.Ollivanders2.StationarySpell.NULLUM_EVANESCUNT(p, player, location, StationarySpells.NULLUM_EVANESCUNT, 5, duration);
         nullum.flair(10);
         p.stationarySpells.addStationarySpell(nullum);
         kill();
      }
   }
}