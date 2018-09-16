package net.pottercraft.Ollivanders2.Spell;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

import net.pottercraft.Ollivanders2.Ollivanders2;

/**
 * Target sheep or colored block turns orange.
 *
 * @author Azami7
 */
public final class COLORO_AURANTIACO extends ColoroSuper
{
   public O2SpellType spellType = O2SpellType.COLORO_AURANTIACO;
   protected String text = "Turns target colorable entity or block orange.";

   DyeColor color = DyeColor.ORANGE;

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public COLORO_AURANTIACO () { }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public COLORO_AURANTIACO (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);
   }
}