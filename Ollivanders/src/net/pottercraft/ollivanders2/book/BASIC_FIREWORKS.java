package net.pottercraft.ollivanders2.book;

import net.pottercraft.ollivanders2.O2MagicBranch;
import net.pottercraft.ollivanders2.spell.O2SpellType;
import net.pottercraft.ollivanders2.Ollivanders2;

/**
 * Non-cannon book written by George Weasley on firework making.
 *
 * @since 2.2.4
 * @author Azami7
 */
public final class BASIC_FIREWORKS extends O2Book
{
   public BASIC_FIREWORKS (Ollivanders2 plugin)
   {
      super(plugin);

      title = shortTitle = "Basic Fireworks";
      author = "George Weasley";
      branch = O2MagicBranch.CHARMS;

      spells.add(O2SpellType.BOTHYNUS);
      spells.add(O2SpellType.COMETES);
      spells.add(O2SpellType.PERICULUM);
      spells.add(O2SpellType.PORFYRO_ASTERI);
      spells.add(O2SpellType.VERDIMILLIOUS);
   }
}