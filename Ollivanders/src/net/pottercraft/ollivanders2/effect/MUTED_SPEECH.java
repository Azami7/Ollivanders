package net.pottercraft.ollivanders2.effect;

import java.util.UUID;

import net.pottercraft.ollivanders2.Ollivanders2;
import org.jetbrains.annotations.NotNull;

public class MUTED_SPEECH extends O2Effect
{
   /**
    * Constructor
    *
    * @param plugin   a callback to the MC plugin
    * @param duration the duration of the effect
    * @param pid      the ID of the player this effect acts on
    */
   public MUTED_SPEECH(@NotNull Ollivanders2 plugin, int duration, @NotNull UUID pid)
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