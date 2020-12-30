package com.muniversalarts.mccourse.sound;

import com.muniversalarts.mccourse.MCCourseMod;
import com.muniversalarts.mccourse.util.Registration;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

public class ModSoundEvents {
    public static final RegistryObject<SoundEvent> SMALL_EXPLOSION =
            Registration.SOUND_EVENTS.register( "small_explosion",
                    () -> new SoundEvent(new ResourceLocation(MCCourseMod.MOD_ID, "small_explosion")));

    public static void register() { }
}
