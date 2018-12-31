package net.pottercraft.Ollivanders2.Effect;

import java.util.UUID;
import net.pottercraft.Ollivanders2.Ollivanders2;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MUTED_SPEECH extends O2Effect
{
   /**
    * Constructor
    *
    * @param plugin a callback to the MC plugin
    * @param duration the duration of the effect
    * @param pid the ID of the player this effect acts on
    */
   public MUTED_SPEECH (Ollivanders2 plugin, Integer duration, UUID pid)
   {
      super(plugin, duration, pid);

      effectType = O2EffectType.MUTED_SPEECH;
      informousText = legilimensText = "is unable to speak";

      divinationText.add("will be struck mute");
      divinationText.add("shall lose their mind to insanity");
      divinationText.add("shall be afflicted in the mind");
      divinationText.add("will fall silent");
   }

   /**
    * Age the effect by 1 every game tick.
    */
   public void checkEffect ()
   {
      age(1);
   }
}