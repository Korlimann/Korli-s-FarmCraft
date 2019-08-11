package com.korlimann.korlisfarmcraft.world;

import com.korlimann.korlisfarmcraft.blocks.ModBlocks;
import com.korlimann.korlisfarmcraft.config.OreGenConfig;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {

    public static void setupOreGeneration() {
        if(OreGenConfig.generate_overworld.get()) {
            for (Biome biome : ForgeRegistries.BIOMES) {
                biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.SALT_ORE.getDefaultState(), OreGenConfig.chance.get()), Placement.COUNT_RANGE, new CountRangeConfig(10, 20, 0, 100)));
            }
        }
    }
}
