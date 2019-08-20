package com.korlimann.korlisfarmcraft.world;

import com.korlimann.korlisfarmcraft.blocks.BlockBaseFruit;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.block.material.Material;
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
        int i = rand.nextInt(2) + this.minTreeHeight;
        boolean flag = true;

        //DEBUG
        //int checkY =0;


        if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight())
        {
            for (int j = position.getY(); j <= position.getY() + 1 + i; ++j)
            {
                //DEBUG
                //checkY++;


                int k = 1;

                if (j == position.getY())
                {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2)
                {
                    k = 2;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
                {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < worldIn.getMaxHeight())
                        {
                            if (!this.isReplaceable(worldIn,blockpos$mutableblockpos.setPos(l, j, i1)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }
            //DEBUG
            //System.out.println("CheckY: " + checkY);
            if (!flag)
            {
                return false;
            }
            else
            {
                //DEBUG
                //System.out.println("Check x");


                //BlockState state = worldIn.getBlockState(position.down());
                if ()
                if (state.getBlock().canSustainPlant(state, worldIn, position.down(), net.minecraft.util.Direction.UP, (net.minecraft.block.SaplingBlock)Blocks.OAK_SAPLING) && position.getY() < worldIn.getMaxHeight() - i - 1)
                {
                    //state.getBlock().onPlantGrow(state, worldIn, position.down(), position);

                    for (int i3 = position.getY() - 3 + i; i3 <= position.getY() + i; ++i3)
                    {
                        int i4 = i3 - (position.getY() + i);
                        int j1 = 1 - i4 / 2;

                        for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; ++k1)
                        {
                            int l1 = k1 - position.getX();

                            for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; ++i2)
                            {
                                int j2 = i2 - position.getZ();

                                if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0)
                                {
                                    BlockPos blockpos = new BlockPos(k1, i3, i2);

                                    if (isAirOrLeaves(worldIn, blockpos))
                                    {
                                        this.setLogState(changedBlocks, worldIn, blockpos, this.leaf, p_208519_5_);
                                        if(isAir(worldIn, blockpos.down())) {
                                            fruitPlaces.add(blockpos.down());
                                        }


                                    }
                                }
                            }
                        }
                    }
                    for (int j3 = 0; j3 < i; ++j3)
                    {
                        BlockPos upN = position.up(j3);

                        if (isAirOrLeaves(worldIn, upN))
                        {
                            this.setLogState(changedBlocks, worldIn, upN, this.leaf, p_208519_5_);
                        }
                    }

                    //DEBUG
                    //System.out.println("pass NoFruit");

                    List<BlockPos> toRem = new ArrayList<BlockPos>();
                    for(BlockPos blockpos:fruitPlaces) {

                        if(!isAir(worldIn, blockpos.down())) {
                            fruitPlaces.add(blockpos.down());
                        }
                    }
                    fruitPlaces.removeAll(toRem);
                    toRem.clear();
                    for(int index = 0; index < 5; index++) {
                        if(!(fruitPlaces.size()<3))
                        {
                            int place = rand.nextInt(fruitPlaces.size());
                            placeFruit(worldIn, rand.nextInt(3),fruitPlaces.get(place), changedBlocks, p_208519_5_);
                            fruitPlaces.remove(fruitPlaces.get(place));
                        }
                    }
                }
            }
            fruitPlaces.clear();
            return true;
        }
        else
        {
            return false;
        }
    }

    public void placeFruit(IWorldGenerationReader worldIn, int p_181652_2_, BlockPos pos, Set<BlockPos> changedBlocks, MutableBoundingBox p_208519_5_) {
        this.setLogState(changedBlocks, worldIn, pos, fruit.getDefaultState().with(BlockBaseFruit.AGE, p_181652_2_), p_208519_5_);
    }
}
