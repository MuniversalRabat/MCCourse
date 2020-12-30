package com.muniversalarts.mccourse.setup;

import com.muniversalarts.mccourse.MCCourseMod;
import com.muniversalarts.mccourse.block.ModBlocks;
import com.muniversalarts.mccourse.container.ModContainers;
import com.muniversalarts.mccourse.entity.BuffaloEntity;
import com.muniversalarts.mccourse.entity.ModEntityTypes;
import com.muniversalarts.mccourse.entity.render.BuffaloRenderer;
import com.muniversalarts.mccourse.item.ModSpawnEggItem;
import com.muniversalarts.mccourse.screens.ElectrifierScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MCCourseMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy implements IProxy{
    @Override
    public void init() {
        RenderTypeLookup.setRenderLayer(ModBlocks.ZUCCINI_CROP.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.REDWOOD_SAPLING.get(), RenderType.getCutout());

        ScreenManager.registerFactory(ModContainers.ELECTRIFIER_CONTAINER.get(), ElectrifierScreen::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BUFFALO.get(), BuffaloRenderer::new);

        ModSpawnEggItem.initSpawnEggs();
    }



    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }
}
