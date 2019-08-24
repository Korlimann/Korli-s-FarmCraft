package com.korlimann.korlisfarmcraft.blocks;

import java.util.Random;

import com.korlimann.korlisfarmcraft.world.FruittreeGeneration;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BlockBaseFruitSapling extends BushBlock implements IGrowable {

    private static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    private BlockBaseFruit fruit;

    public BlockBaseFruitSapling(String name, BlockBaseFruit fruit) {
        super(Properties.create(Material.PLANTS)
                    .hardnessAndResistance(0f)
                    .sound(SoundType.PLANT)
                    .tickRandomly()
                    );

        setRegistryName(name);
        this.fruit=fruit;
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, 0));
    }

    /*
    public BlockFruitSapling(String name, BlockBaseFruitLeaves fruitLeaves,boolean CreativeTab) {
        setUnlocalizedName(name);
        setRegistryName(name);
        if(CreativeTab)
            setCreativeTab(Main.korlissushicraft);
        this.leaves = fruitLeaves;
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
    }*/

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(STAGE);
    }

    private void generateTree(IWorld worldIn, BlockPos pos, BlockState state, Random rand)
    {
        //if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;

        Feature worldGen = new FruittreeGeneration(NoFeatureConfig::deserialize, false, 5, false, fruit);
        if(fruit==null)
        {
            //worldgenerator = new WorldGenTrees(true, 5, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK), leaves.getDefaultState().withProperty(BlockBaseFruitLeaves.AGE, rand.nextInt(1)), false);
        }else
            worldGen = new FruittreeGeneration(NoFeatureConfig::deserialize, false, 5, false, fruit);

        int i = 0;
        int j = 0;
        boolean flag = false;

        BlockState iblockstate2 = Blocks.AIR.getDefaultState();

        if (flag)
        {
            worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
            worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
        }
        else
        {
            worldIn.setBlockState(pos, iblockstate2, 4);
        }

        if (!worldGen.place(worldIn, worldIn.getChunkProvider().getChunkGenerator(), rand, pos, IFeatureConfig.NO_FEATURE_CONFIG))
        {
            if (flag)
            {
                worldIn.setBlockState(pos.add(i, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
                worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
                worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
            }
            else
            {
                worldIn.setBlockState(pos, state, 4);
            }
        }
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    private void grow(IWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.get(STAGE) == 0)
        {
            worldIn.setBlockState(pos, state.cycle(STAGE), 4);
        }
        else
        {
            this.generateTree(worldIn, pos, state, rand);
        }
    }

    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        this.grow((IWorld)worldIn, (Random)rand, (BlockPos)pos, (BlockState)state);
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if (worldIn.isAreaLoaded(pos, 1)) {
            if (worldIn.getLight(pos.up()) >= 9 && random.nextInt(1) == 0) {
                this.grow((IWorld)worldIn, (Random)random, (BlockPos)pos, (BlockState)state);
            }

        }
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}

