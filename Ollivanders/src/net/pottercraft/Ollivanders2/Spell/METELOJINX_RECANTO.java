package net.pottercraft.Ollivanders2.Spell;

import org.bukkit.entity.Player;

import net.pottercraft.Ollivanders2.Ollivanders2;

/**
 * Ends a storm for a variable duration
 *
 * @version Ollivanders2
 * @see MetelojinxSuper
 * @author lownes
 * @author Azami7
 */
public final class METELOJINX_RECANTO extends MetelojinxSuper
{
   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public METELOJINX_RECANTO ()
   {
      super();

      spellType = O2SpellType.METELOJINX_RECANTO;
      text = "Metelojinx Recanto will turn a storm into a sunny day.";
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public METELOJINX_RECANTO (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      spellType = O2SpellType.METELOJINX_RECANTO;
      setUsesModifier();
      storm = false;
   }
}