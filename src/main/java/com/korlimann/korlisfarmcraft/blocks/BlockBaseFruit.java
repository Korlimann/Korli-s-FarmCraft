package com.korlimann.korlisfarmcraft.blocks;

import java.util.Random;

import com.korlimann.korlisfarmcraft.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;

public class BlockBaseFruit extends Block implements IGrowable, IHasModel {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
    public  AxisAlignedBB AABB;
    public Item fruit;
    public boolean canGrow;
    public boolean canUseBonemeal;
    private String type;
    //private boolean CreativeTab;


    public BlockBaseFruit(String name, Material materialIn, double x1, double y1, double z1, double x2, double y2, double z2, Item fruit, boolean grow, boolean bonemeal, boolean CreativeTab) {
        super(materialIn);
        this.fruit = fruit;
        this.canGrow = grow;
        this.canUseBonemeal = bonemeal;
        this.type=name;
        AABB = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)));
        this.ticksRandomly(this.getDefaultState());
        //setUnlocalizedName(name);
        setRegistryName(name);
        //this.CreativeTab = CreativeTab;
    }

    public BlockBaseFruit(String name, Material materialIn, Item fruit)
    {
        this(name,materialIn,0.25D, 0.25D, 0.25D, 0.75D, 1D, 0.75D, fruit, true, true,true);
    }
    @Override
    public void registerModels() {

    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return canUseBonemeal;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(((Integer)state.get(AGE)).intValue() + 1)), 2);
    }

    public boolean isFullCube(BlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        // TODO Auto-generated method stub
        return NULL_AABB;
    }
    public void updateTick(World worldIn, BlockPos pos, BlockState state, Random rand)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            this.dropBlock(worldIn, pos, state);
        }
        else
        {
            int i = ((Integer)state.get(AGE)).intValue();

            if (i < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(5) == 0))
            {
                worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i + 1)), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, BlockState state)
    {
        BlockState iblockstate = worldIn.getBlockState(pos.up());
        return iblockstate.getBlock() == Blocks.OAK_LEAVES;
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            this.dropBlock(worldIn, pos, state);
        }
    }

    private void dropBlock(World worldIn, BlockPos pos, BlockState state)
    {
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        this.dropBlock(worldIn, pos, state);
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune)
    {
        if(((Integer)state.get(AGE)).intValue()==3)
        {
            drops.add(new ItemStack(fruit));
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state)
    {
        return new ItemStack(fruit);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public BlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().with(AGE, Integer.valueOf((meta & 15)));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(BlockState state)
    {
        int i = 0;
        i = i | ((Integer)state.get(AGE)).intValue();
        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(BlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn,
                                    Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        if(((Integer)state.get(AGE)).intValue()==3) {
            playerIn.addItemStackToInventory(new ItemStack(fruit));
            worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(0)), 2);
            return true;
        }
        return false;

    }

    //Only Call When a Value for this already exists in BlockBaseFruit.EnumType
    //Automatically Creates Sapling that creates a FruitTree
    //Only Call once for each Fruit
    public BlockFruitSapling createFruitTreeAndSapling()
    {
        BlockFruitSapling ret = new BlockFruitSapling(this.getTypeName()+"_sapling",this,true);

        return ret;
    }

    public String getTypeName()
    {
        return type;
    }


}
