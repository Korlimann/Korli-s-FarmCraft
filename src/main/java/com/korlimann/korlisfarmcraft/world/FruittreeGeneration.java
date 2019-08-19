package com.korlimann.korlisfarmcraft.world;

import com.korlimann.korlisfarmcraft.blocks.ModBlocks;
import com.korlimann.korlisfarmcraft.config.OreGenConfig;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class FruittreeGeneration extends AbstractTreeFeature<NoFeatureConfig> {

    public FruittreeGeneration(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49920_1_, boolean doBlockNofityOnPlace) {
        super(p_i49920_1_, doBlockNofityOnPlace);
    }

    public static void setupOreGeneration() {
        if(OreGenConfig.generate_overworld.get()) {
            for (Biome biome : ForgeRegistries.BIOMES) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.SALT_ORE.getDefaultState(), OreGenConfig.chance.get()), Placement.COUNT_RANGE, new CountRangeConfig(10, 20, 0, 100)));
            }
        }
    }

    @Override
    protected boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_) {
        return false;
    }
}
