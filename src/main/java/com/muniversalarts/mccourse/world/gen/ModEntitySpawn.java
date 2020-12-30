package com.muniversalarts.mccourse.world.gen;

import com.muniversalarts.mccourse.MCCourseMod;
import com.muniversalarts.mccourse.block.RedwoodTree;
import com.muniversalarts.mccourse.entity.ModEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = MCCourseMod.MOD_ID)
public class ModEntitySpawn {
    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event){
        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        if (!types.contains(BiomeDictionary.Type.NETHER) && !types.contains(BiomeDictionary.Type.END) &&
                !types.contains(BiomeDictionary.Type.OCEAN)){
            List<MobSpawnInfo.Spawners> base =
                    event.getSpawns().getSpawner(EntityClassification.CREATURE);

            base.add(new MobSpawnInfo.Spawners(ModEntityTypes.BUFFALO.get(), 30, 2, 5));
        }
    }
}
