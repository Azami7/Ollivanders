package net.pottercraft.Ollivanders2.Spell;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.pottercraft.Ollivanders2.Ollivanders2;
import net.pottercraft.Ollivanders2.StationarySpell.StationarySpellObj;

/**
 * Lengthens the duration of shield spells.
 *
 * @version Ollivanders2
 * @author lownes
 * @author Azami7
 */
public final class FIANTO_DURI extends Charms
{
   public O2SpellType spellType = O2SpellType.FIANTO_DURI;

   protected ArrayList<String> flavorText = new ArrayList<String>() {{
      add("\"Protego Maxima. Fianto Duri. Repello Inimicum.\" - Filius Flitwick");
      add("");
   }};

   protected String text = "Fianto Duri lengthens the duration of a stationary spell.";

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public FIANTO_DURI () { }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public FIANTO_DURI (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);
   }

   @Override
   public void checkEffect ()
   {
      move();
      List<StationarySpellObj> inside = new ArrayList<>();
      for (StationarySpellObj spell : p.stationarySpells.getActiveStationarySpells())
      {
         if (spell.isInside(location))
         {
            inside.add(spell);
            kill();
         }
      }
      int addedAmount = (int) ((usesModifier * 1200) / inside.size());
      for (StationarySpellObj spell : inside)
      {
         spell.duration += addedAmount;
         spell.flair(10);
      }
   }
}