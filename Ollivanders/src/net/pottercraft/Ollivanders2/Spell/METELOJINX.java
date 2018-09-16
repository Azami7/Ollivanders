package net.pottercraft.Ollivanders2.Spell;

import org.bukkit.entity.Player;

import net.pottercraft.Ollivanders2.Ollivanders2;

/**
 * Creates a storm of a variable duration.
 *
 * @version Ollivanders2
 * @see MetelojinxSuper
 * @author lownes
 * @author Azami7
 */
public final class METELOJINX extends MetelojinxSuper
{
   public O2SpellType spellType = O2SpellType.METELOJINX;

   protected String text = "Metelojinx will turn a sunny day into a storm.";

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public METELOJINX () { }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public METELOJINX (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);
      storm = true;
   }
}