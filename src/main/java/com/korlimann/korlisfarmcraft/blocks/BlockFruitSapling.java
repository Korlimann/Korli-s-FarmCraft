package com.korlimann.korlisfarmcraft.blocks;

import java.util.Random;

import com.korlimann.korlisfarmcraft.Main;
import com.korlimann.korlisfarmcraft.worldgen.WorldGenFruitTree;
import com.korlimann.korlisfarmcraft.util.IHasModel;

import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

public class BlockFruitSapling extends BushBlock implements IHasModel, IGrowable {


    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
    public BlockBaseFruit fruit;
    public BlockBaseFruitLeaves leaves;
    public BlockFruitSapling(String name, BlockBaseFruit fruit,boolean CreativeTab) {
        //setUnlocalizedName(name);
        setRegistryName(name);
        if(CreativeTab)
            //setCreativeTab(Main.korlissushicraft);
        this.fruit=fruit;
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, Integer.valueOf(0)));
    }
    public BlockFruitSapling(String name, BlockBaseFruitLeaves fruitLeaves,boolean CreativeTab) {
        super();
        //setUnlocalizedName(name);
        setRegistryName(name);
        if(CreativeTab)
            //setCreativeTab(Main.korlissushicraft);
        this.leaves = fruitLeaves;
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, Integer.valueOf(0)));
    }

    protected BlockFruitSapling(Properties properties) {
        super(properties);
    }

    @Override
    public void registerModels() {

    }

    public void generateTree(World worldIn, BlockPos pos, BlockState state, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;

        Feature worldgenerator;
        if(fruit==null)
        {
            worldgenerator = new WorldGenTrees(true, 5, Blocks.OAK_LOG.getDefaultState().with(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK), leaves.getDefaultState().with(BlockBaseFruitLeaves.AGE, rand.nextInt(1)), false);
        }else
            worldgenerator = new WorldGenFruitTree(true, 5, fruit);


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

        if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j)))
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
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        // TODO Auto-generated method stub
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        // TODO Auto-generated method stub
        if (((Integer)state.get(STAGE)).intValue() == 0)
        {
            worldIn.setBlockState(pos, state.cycle(STAGE), 4);
        }
        else
        {
            this.generateTree(worldIn, pos, state, rand);
        }
    }
}
