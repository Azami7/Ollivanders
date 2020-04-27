package net.pottercraft.ollivanders2.book;

import net.pottercraft.ollivanders2.O2MagicBranch;
import net.pottercraft.ollivanders2.spell.O2SpellType;
import net.pottercraft.ollivanders2.Ollivanders2;

/**
 * Basic Hexes for the Busy and Vexed
 * http://harrypotter.wikia.com/wiki/Basic_Hexes_for_the_Busy_and_Vexed
 *
 * @since 2.2.4
 * @author Azami7
 */
public class BASIC_HEXES extends O2Book
{
   public BASIC_HEXES (Ollivanders2 plugin)
   {
      super(plugin);

      shortTitle = "Basic Hexes";
      title = "Basic Hexes for the Busy and Vexed";
      author = "Unknown";
      branch = O2MagicBranch.DARK_ARTS;

      spells.add(O2SpellType.MUCUS_AD_NAUSEAM);
      spells.add(O2SpellType.IMPEDIMENTA);
      spells.add(O2SpellType.IMMOBULUS);
      spells.add(O2SpellType.OBSCURO);
      spells.add(O2SpellType.LOQUELA_INEPTIAS);
      // pepper breath
   }
}