package net.pottercraft.ollivanders2.spell;

import net.pottercraft.ollivanders2.O2MagicBranch;
import net.pottercraft.ollivanders2.Ollivanders2;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Fanciest version of PORFYRO_ASTERI.
 *
 * @author Azami7
 * @see Pyrotechnia
 * @see PORFYRO_ASTERI
 */
public final class PORFYRO_ASTERI_TRIA extends Pyrotechnia
{
   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public PORFYRO_ASTERI_TRIA()
   {
      super();

      spellType = O2SpellType.PORFYRO_ASTERI_TRIA;
      branch = O2MagicBranch.CHARMS;

      text = "Conjures purple star fireworks with trails and that fades to white.";
   }

   /**
    * Constructor.
    *
    * @param plugin a callback to the MC plugin
    * @param player the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public PORFYRO_ASTERI_TRIA (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      spellType = O2SpellType.PORFYRO_ASTERI_TRIA;
      branch = O2MagicBranch.CHARMS;
      initSpell();

      fireworkColors = new ArrayList<>();
      fireworkColors.add(Color.PURPLE);
      fireworkColors.add(Color.FUCHSIA);

      hasTrails = true;

      hasFade = true;
      fadeColors = new ArrayList<>();
      fadeColors.add(Color.WHITE);

      fireworkType = FireworkEffect.Type.STAR;
      shuffleTypes = true;

      setMaxFireworks(15);
   }
}