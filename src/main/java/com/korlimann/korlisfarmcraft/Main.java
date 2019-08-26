package com.korlimann.korlisfarmcraft;


import com.korlimann.korlisfarmcraft.blocks.BlockBaseFruit;
import com.korlimann.korlisfarmcraft.blocks.BlockBaseFruitSapling;
import com.korlimann.korlisfarmcraft.blocks.ModBlocks;

import com.korlimann.korlisfarmcraft.items.scythe.ModScythe;
import com.korlimann.korlisfarmcraft.items.sickle.ModSickle;
import com.korlimann.korlisfarmcraft.blocks.SaltOre;
import com.korlimann.korlisfarmcraft.config.Config;
import com.korlimann.korlisfarmcraft.config.OreGenConfig;
import com.korlimann.korlisfarmcraft.items.Salt;
import com.korlimann.korlisfarmcraft.setup.ClientProxy;
import com.korlimann.korlisfarmcraft.setup.IProxy;
import com.korlimann.korlisfarmcraft.setup.ModSetup;
import com.korlimann.korlisfarmcraft.setup.ServerProxy;
import com.korlimann.korlisfarmcraft.world.FruittreeGeneration;
import com.korlimann.korlisfarmcraft.world.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
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
   // public static final Feature<NoFeatureConfig> apple_tree;
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
            BlockBaseFruit apple = new BlockBaseFruit("apple_block", 6.25, 12, 6.25, 9.75, 16D, 9.75D, Items.APPLE, true, true);
            event.getRegistry().register(apple);
            event.getRegistry().register(new BlockBaseFruitSapling("apple_block_sapling",apple));
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties().group(setup.itemGroup);
          
            event.getRegistry().register(new ModSickle(128,1.0f,1.0f,4,0, null,-1.6f,"stone_sickle"));
            event.getRegistry().register(new ModScythe(128,1.0f,2.0f,4,0, null,-1.5f,"stone_scythe",3));
            event.getRegistry().register(new ModSickle(256,1.0f,2.0f,4,0, null,-2.4f,"iron_sickle"));
            event.getRegistry().register(new ModScythe(256,1.0f,4.0f,4,0, null,-2f,"iron_scythe",3));
            event.getRegistry().register(new ModSickle(1024,1.0f,3.0f,4,0, null,-2.4f,"diamond_sickle"));
            event.getRegistry().register(new ModScythe(1024,1.0f,5.0f,4,0, null,-2f,"diamond_scythe",3));
            event.getRegistry().register(new BlockItem(ModBlocks.SALT_ORE, properties).setRegistryName("salt_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.APPLE_BLOCK, properties).setRegistryName("apple_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.APPLE_BLOCK_SAPLING, properties).setRegistryName("apple_block_sapling"));
            event.getRegistry().register(new Salt());
        }
        @SubscribeEvent
        public static void onFeatureRegistry(final RegistryEvent.Register<Feature<?>> event)
        {
            event.getRegistry().register(new FruittreeGeneration("feature_fruit_tree_gen_apple",NoFeatureConfig::deserialize, false, 5, false, ModBlocks.APPLE_BLOCK));
        }

    }
}
