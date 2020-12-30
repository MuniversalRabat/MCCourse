package com.muniversalarts.mccourse;

import com.muniversalarts.mccourse.block.ModBlocks;
import com.muniversalarts.mccourse.block.ModFluids;
import com.muniversalarts.mccourse.container.ModContainers;
import com.muniversalarts.mccourse.enchantment.ModEnchantments;
import com.muniversalarts.mccourse.entity.BuffaloEntity;
import com.muniversalarts.mccourse.entity.ModEntityTypes;
import com.muniversalarts.mccourse.events.ModEvents;
import com.muniversalarts.mccourse.item.ModItems;
import com.muniversalarts.mccourse.setup.ClientProxy;
import com.muniversalarts.mccourse.setup.IProxy;
import com.muniversalarts.mccourse.setup.ServerProxy;
import com.muniversalarts.mccourse.sound.ModSoundEvents;
import com.muniversalarts.mccourse.tileentity.ModTileEntities;
import com.muniversalarts.mccourse.util.Config;
import com.muniversalarts.mccourse.util.Registration;
import com.muniversalarts.mccourse.world.biome.ModBiomes;
import com.muniversalarts.mccourse.world.biome.ModSurfaceBuilders;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MCCourseMod.MOD_ID)
public class MCCourseMod
{
    public static final String MOD_ID = "mccourse";

    public static final ItemGroup COURSE_TAB = new ItemGroup("courseTab"){
        @Override
        public ItemStack createIcon(){
            return new ItemStack(ModItems.COPPER_WIRE.get());
        }
    };

    public static IProxy proxy;

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public MCCourseMod() {
        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        registerModAdditions();

        /* For possible use later -
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        */

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        registerConfigs();

        proxy.init();

        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(ModEntityTypes.BUFFALO.get(), BuffaloEntity.setCustomAttributes().create());
        });

        loadConfigs();

    }

    private void registerConfigs(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
    }

    private void registerModAdditions(){
        // Initializes the registration of the Mod's Additions.
        Registration.init();

        // Registers items, blocks, and more added by this mod.
        ModItems.register();
        ModFluids.register();
        ModBlocks.register();

        ModBiomes.register();
        ModSurfaceBuilders.register();

        ModTileEntities.register();
        ModContainers.register();
        ModEntityTypes.register();
        ModSoundEvents.register();
        ModEnchantments.register();

        // Registers mod events.
        MinecraftForge.EVENT_BUS.register(new ModEvents());
    }

    private void loadConfigs(){
        Config.loadConfigFile(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mccourse-client.toml").toString());
        Config.loadConfigFile(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mccourse-server.toml").toString());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
