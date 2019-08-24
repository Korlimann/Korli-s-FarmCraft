package com.korlimann.korlisfarmcraft.world;

import com.korlimann.korlisfarmcraft.blocks.BlockBaseFruit;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class FruittreeGeneration extends AbstractTreeFeature<NoFeatureConfig> {

    private static final BlockState DEFAULT_TRUNK = Blocks.OAK_LOG.getDefaultState();
    private static final BlockState DEFAULT_LEAF = Blocks.OAK_LEAVES.getDefaultState();
    protected final int minTreeHeight;
    private final boolean vinesGrow;
    private final BlockState trunk;
    private final BlockState leaf;
    private ArrayList<BlockPos> fruitPlaces = new ArrayList<BlockPos>();

    private final Block fruit;

    public FruittreeGeneration(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace, int minTreeHeightIn, boolean vinesGrowIn, Block fruit) {
        super(configFactoryIn, doBlockNotifyOnPlace);
        this.minTreeHeight = minTreeHeightIn;
        this.trunk = DEFAULT_TRUNK;
        this.leaf = DEFAULT_LEAF;
        this.vinesGrow = vinesGrowIn;
        this.fruit = fruit;
    }

    @Override
    protected boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_) {
        int i = this.getHeight(rand);
        boolean flag = true;
        if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight()) {
            for(int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
                int k = 1;
                if (j == position.getY()) {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2) {
                    k = 2;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for(int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
                    for(int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
                        if (j >= 0 && j < worldIn.getMaxHeight()) {
                            if (!func_214587_a(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else if (isSoil(worldIn, position.down(), getSapling()) && position.getY() < worldIn.getMaxHeight() - i - 1) {
                this.setDirtAt(worldIn, position.down(), position);
                int j2 = 3;
                int k2 = 0;

                for(int l2 = position.getY() - 3 + i; l2 <= position.getY() + i; ++l2) {
                    int l3 = l2 - (position.getY() + i);
                    int j4 = 1 - l3 / 2;

                    for(int j1 = position.getX() - j4; j1 <= position.getX() + j4; ++j1) {
                        int k1 = j1 - position.getX();

                        for(int l1 = position.getZ() - j4; l1 <= position.getZ() + j4; ++l1) {
                            int i2 = l1 - position.getZ();
                            if (Math.abs(k1) != j4 || Math.abs(i2) != j4 || rand.nextInt(2) != 0 && l3 != 0) {
                                BlockPos blockpos = new BlockPos(j1, l2, l1);
                                if (isAirOrLeaves(worldIn, blockpos) || func_214576_j(worldIn, blockpos)) {
                                    this.setLogState(changedBlocks, worldIn, blockpos, this.leaf, p_208519_5_);
                                }
                            }
                        }
                    }
                }
                for(int i3 = 0; i3 < i; ++i3) {
                    if (isAirOrLeaves(worldIn, position.up(i3)) || func_214576_j(worldIn, position.up(i3))) {
                        this.setLogState(changedBlocks, worldIn, position.up(i3), this.trunk, p_208519_5_);
                    }
                }




                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected int getHeight(Random random) {
        return this.minTreeHeight + random.nextInt(3);
    }

    public void placeFruit(IWorldGenerationReader worldIn, int p_181652_2_, BlockPos pos, Set<BlockPos> changedBlocks, MutableBoundingBox p_208519_5_) {
        this.setLogState(changedBlocks, worldIn, pos, fruit.getDefaultState().with(BlockBaseFruit.AGE, p_181652_2_), p_208519_5_);
    }
}
