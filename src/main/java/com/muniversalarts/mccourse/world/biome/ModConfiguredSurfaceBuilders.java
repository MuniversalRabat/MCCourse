package com.muniversalarts.mccourse.world.biome;

import com.muniversalarts.mccourse.MCCourseMod;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class ModConfiguredSurfaceBuilders {
    public static RegistryKey<ConfiguredSurfaceBuilder<?>> OIL_SURFACE
            = RegistryKey.getOrCreateKey(Registry.CONFIGURED_SURFACE_BUILDER_KEY,
            new ResourceLocation(MCCourseMod.MOD_ID, "oil_surface"));
}
