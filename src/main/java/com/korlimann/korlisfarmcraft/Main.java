package com.korlimann.korlisfarmcraft;


import com.korlimann.korlisfarmcraft.blocks.GamingChair;
import com.korlimann.korlisfarmcraft.blocks.ModBlocks;

import com.korlimann.korlisfarmcraft.items.Sickle;
import com.korlimann.korlisfarmcraft.blocks.SaltOre;
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
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main {

    public static final String MODID = "korlisfarmcraft";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static OreGeneration oreGeneration = new OreGeneration();
    public static OreGenConfig oreGenConfig = new OreGenConfig();
    public static ModSetup setup = new ModSetup();

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public Main() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        Config.loadConfig(Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve("tutorialmod-client.toml").toString());
        Config.loadConfig(Config.SERVER, FMLPaths.CONFIGDIR.get().resolve("tutorialmod-server.toml").toString());
    }

    private void setup(final FMLCommonSetupEvent event) {

        proxy.init();
        proxy.getClientWorld();
        setup.init();
        oreGeneration.setupOreGeneration();
    }

    private void setupClient(final FMLClientSetupEvent event)
    {
        OBJLoader.INSTANCE.addDomain(MODID);
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new SaltOre());
            event.getRegistry().register(new GamingChair());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties().group(setup.itemGroup);
            event.getRegistry().register(new BlockItem(ModBlocks.GAMING_CHAIR, properties).setRegistryName("gaming_chair"));
            event.getRegistry().register(new Sickle());
            event.getRegistry().register(new BlockItem(ModBlocks.SALT_ORE, properties).setRegistryName("salt_ore"));
            event.getRegistry().register(new Salt());
        }

        @SubscribeEvent
        public static void onModelRegistry(ModelRegistryEvent event)
        {
            OBJLoader.INSTANCE.addDomain(MODID);

        }
    }
}
