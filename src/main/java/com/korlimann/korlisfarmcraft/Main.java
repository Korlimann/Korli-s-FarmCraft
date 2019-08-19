package com.korlimann.korlisfarmcraft;


import com.korlimann.korlisfarmcraft.blocks.*;

import com.korlimann.korlisfarmcraft.items.Sickle;
import com.korlimann.korlisfarmcraft.config.Config;
import com.korlimann.korlisfarmcraft.config.OreGenConfig;
import com.korlimann.korlisfarmcraft.items.Salt;
import com.korlimann.korlisfarmcraft.setup.ClientProxy;
import com.korlimann.korlisfarmcraft.setup.IProxy;
import com.korlimann.korlisfarmcraft.setup.ModSetup;
import com.korlimann.korlisfarmcraft.setup.ServerProxy;
import com.korlimann.korlisfarmcraft.world.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("korlisfarmcraft")
public class Main {

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static OreGeneration oreGeneration = new OreGeneration();
    public static OreGenConfig oreGenConfig = new OreGenConfig();
    public static ModSetup setup = new ModSetup();

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public Main() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        Config.loadConfig(Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve("tutorialmod-client.toml").toString());
        Config.loadConfig(Config.SERVER, FMLPaths.CONFIGDIR.get().resolve("tutorialmod-server.toml").toString());
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.init();
        proxy.getClientWorld();
        setup.init();
        oreGeneration.setupOreGeneration();
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new SaltOre());
            event.getRegistry().register(new BarnWall());
            event.getRegistry().register(new BarnCorner());
            event.getRegistry().register(new Roof90Left());
            event.getRegistry().register(new Roof90Middle());
            event.getRegistry().register(new Roof90Right());
            event.getRegistry().register(new RoofFlatLeft());
            event.getRegistry().register(new RoofFlatMiddle());
            event.getRegistry().register(new RoofFlatRight());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties().group(setup.itemGroup);
          
            event.getRegistry().register(new Sickle());
            event.getRegistry().register(new BlockItem(ModBlocks.SALT_ORE, properties).setRegistryName("salt_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.BARN_WALL, properties).setRegistryName("barn_wall"));
            event.getRegistry().register(new BlockItem(ModBlocks.BARN_CORNER, properties).setRegistryName("barn_corner"));
            event.getRegistry().register(new BlockItem(ModBlocks.ROOF_90_LEFT, properties).setRegistryName("roof_90_left"));
            event.getRegistry().register(new BlockItem(ModBlocks.ROOF_90_MIDDLE, properties).setRegistryName("roof_90_middle"));
            event.getRegistry().register(new BlockItem(ModBlocks.ROOF_90_RIGHT, properties).setRegistryName("roof_90_right"));
            event.getRegistry().register(new BlockItem(ModBlocks.ROOF_FLAT_LEFT, properties).setRegistryName("roof_flat_left"));
            event.getRegistry().register(new BlockItem(ModBlocks.ROOF_FLAT_MIDDLE, properties).setRegistryName("roof_flat_middle"));
            event.getRegistry().register(new BlockItem(ModBlocks.ROOF_FLAT_RIGHT, properties).setRegistryName("roof_flat_right"));
            event.getRegistry().register(new Salt());
        }
    }
}
