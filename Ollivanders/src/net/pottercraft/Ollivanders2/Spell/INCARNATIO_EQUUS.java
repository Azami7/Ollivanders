package net.pottercraft.Ollivanders2.Spell;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.HorseWatcher;
import net.pottercraft.Ollivanders2.Ollivanders2;
import net.pottercraft.Ollivanders2.Ollivanders2Common;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * Created by Azami7 on 6/28/17.
 *
 * Turn target player in to a horse.
 *
 * @since 2.2.3
 * @author Azami7
 */
public final class INCARNATIO_EQUUS extends PlayerDisguiseSuper
{
   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public INCARNATIO_EQUUS (O2SpellType type)
   {
      super(type);

      text = "Turns target player in to a horse.";
   }

   /**
    * Constructor for casting the spell.
    *
    * @param plugin
    * @param player
    * @param type
    * @param rightWand
    */
   public INCARNATIO_EQUUS(Ollivanders2 plugin, Player player, O2SpellType type, Double rightWand)
   {
      super(plugin, player, type, rightWand);

      targetType = EntityType.HORSE;
      disguiseType = DisguiseType.getType(targetType);
      disguise = new MobDisguise(disguiseType);

      HorseWatcher watcher = (HorseWatcher)disguise.getWatcher();
      watcher.setAdult();

      // randomize appearance
      Ollivanders2Common common = new Ollivanders2Common(p);

      watcher.setStyle(common.randomHorseStyle());
      watcher.setColor(common.randomHorseColor());
   }
}
