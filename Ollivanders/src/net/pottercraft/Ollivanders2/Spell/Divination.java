package net.pottercraft.Ollivanders2.Spell;

import net.pottercraft.Ollivanders2.Divination.O2Divination;
import net.pottercraft.Ollivanders2.Divination.O2DivinationType;
import net.pottercraft.Ollivanders2.Effect.O2EffectType;
import net.pottercraft.Ollivanders2.O2MagicBranch;
import net.pottercraft.Ollivanders2.Ollivanders2;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Super class for all divination spells.
 *
 * @author Azami7
 * @since 2.2.9
 */
public abstract class Divination extends O2Spell
{
   O2DivinationType divinationType = null;
   Player target = null;

   public static final ArrayList<O2SpellType> divinationSpells = new ArrayList<O2SpellType>()
   {{
      add(O2SpellType.ASTROLOGIA);
   }};

   /**
    * Default constructor for use in generating spell text.  Do not use to cast the spell.
    */
   public Divination ()
   {
      super();

      branch = O2MagicBranch.DIVINATION;
   }

   /**
    * Constructor.
    *
    * @param plugin    a callback to the MC plugin
    * @param player    the player who cast this spell
    * @param rightWand which wand the player was using
    */
   public Divination (Ollivanders2 plugin, Player player, Double rightWand)
   {
      super(plugin, player, rightWand);

      branch = O2MagicBranch.DIVINATION;
   }

   /**
    * Override setUsesModifier because this spell does not require holding a wand.
    */
   @Override
   protected void setUsesModifier ()
   {
      usesModifier = p.getSpellNum(player, spellType);

      if (p.players.playerEffects.hasEffect(player.getUniqueId(), O2EffectType.HIGHER_SKILL))
      {
         usesModifier *= 2;
      }
   }

   /**
    * Set the target for this divination. This must be done when the spell is created.
    *
    * @param t the target player
    */
   public void setTarget (Player t)
   {
      if (player != null)
      {
         target = t;
      }
   }

   @Override
   public void checkEffect ()
   {
      // target must be logged in to make prophecy about them
      if (target == null || !target.isOnline())
      {
         player.sendMessage(Ollivanders2.chatColor + "Unable to find that player online.");
         kill();
         return;
      }

      int experience = p.getO2Player(player).getSpellCount(spellType);

      // create a prophecy of the correct type
      O2Divination divination;
      Class<?> divinationClass = divinationType.getClassName();

      try
      {
         divination = (O2Divination) divinationClass.getConstructor(Ollivanders2.class, Player.class, Player.class, Integer.class).newInstance(p, player, target, experience);
      }
      catch (Exception e)
      {
         p.getLogger().warning("Exception creating divination");
         e.printStackTrace();
         kill();
         return;
      }

      divination.divine();

      kill();
   }
}