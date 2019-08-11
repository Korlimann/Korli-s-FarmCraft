package com.korlimann.korlisfarmcraft.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.korlimann.korlisfarmcraft.util.IHasModel;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;

public class BlockBaseFruitLeaves extends LeavesBlock implements IGrowable, IHasModel {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 2);
    public static AxisAlignedBB AABB;
    public Item fruit;
    public boolean canGrow;
    public boolean canUseBonemeal;
    private String type;
    //private boolean CreativeTab;


    public BlockBaseFruitLeaves(String name,String treeName, Material materialIn, double x1, double y1, double z1, double x2, double y2, double z2, Item fruit, boolean grow, boolean bonemeal, boolean CreativeTab) {

        this.fruit = fruit;
        this.canGrow = grow;
        this.canUseBonemeal = bonemeal;
        this.type=treeName;
        AABB = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)).with(LeavesBlock.CHECK_DECAY, Boolean.valueOf(true)).withProperty(LeavesBlock.DECAYABLE, Boolean.valueOf(true)));
        this.setTickRandomly(true);
        setUnlocalizedName(name);
        setRegistryName(name);
        //this.CreativeTab = CreativeTab;
    }

    public BlockBaseFruitLeaves(String name,String treeName, Material materialIn, Item fruit)
    {
        this(name,treeName,materialIn,0.25D, 0.25D, 0.25D, 0.75D, 1D, 0.75D, fruit, true, true,true);
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
        return Block.FULL_BLOCK_AABB;
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

            if (i < 2 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(5) == 0))
            {
                worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i + 1)), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, BlockState state)
    {
        return true;
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
        return this.getDefaultState().with(AGE, Integer.valueOf((meta & 3))).with(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(BlockState state)
    {
        int i = 0;
        i = i | ((Integer)state.get(AGE)).intValue();
        if (!((Boolean)state.get(DECAYABLE)).booleanValue())
        {
            i |= 4;
        }
        if (((Boolean)state.get(CHECK_DECAY)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new Property[] {AGE, LeavesBlock.CHECK_DECAY, LeavesBlock.DECAYABLE});
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
        return Block.FULL_BLOCK_AABB;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn,
                                    Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        if(((Integer)state.get(AGE)).intValue()==2) {
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

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        // TODO Auto-generated method stub
        List<ItemStack> l = new ArrayList<ItemStack> ();
        l.add(item);
        return l;
    }

    @Override
    public EnumType getWoodType(int meta) {
        // TODO Auto-generated method stub
        return EnumType.OAK;
    }


}
