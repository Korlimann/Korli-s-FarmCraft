package com.korlimann.korlisfarmcraft.blocks;

import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockBaseFruit extends Block implements IGrowable {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public  AxisAlignedBB AABB;
    public Item fruit;
    public boolean canGrow;
    public boolean canUseBonemeal;
    private String type;
    //private boolean CreativeTab;


    public BlockBaseFruit(String name, double x1, double y1, double z1, double x2, double y2, double z2, Item fruit, boolean grow, boolean bonemeal) {
        super(Properties.create(Material.LEAVES)
                .sound(SoundType.PLANT)
                .hardnessAndResistance(1.0f)
                .harvestLevel(1)
        );
        this.fruit = fruit;
        this.canGrow = grow;
        this.canUseBonemeal = bonemeal;
        this.type=name;
        AABB = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
        //this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
        this.setDefaultState(this.stateContainer.getBaseState().with(this.getAgeProperty(), 0));
        setRegistryName(name);
        //this.CreativeTab = CreativeTab;
    }

    public BlockBaseFruit(String name, Material materialIn, Item fruit)
    {
        this(name,0.25D, 0.25D, 0.25D, 0.75D, 1D, 0.75D, fruit, true, true);
    }
    /*@Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }*/

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return canGrow;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return canUseBonemeal;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), 2);
    }

    /*public boolean isFullCube(BlockState state) {
        return false;
    }*/

    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockReader worldIn, BlockPos pos) {
        return getAABB();
    }

    public AxisAlignedBB getAABB() {
        return AABB;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            this.dropBlock(worldIn, pos, state);
        }
        else
        {
            int i = state.get(AGE);

            if (i < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(5) == 0))
            {
                worldIn.setBlockState(pos, state.with(AGE, i + 1), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    /*public void tick(World worldIn, BlockPos pos, BlockState state, Random rand)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            this.dropBlock(worldIn, pos, state);
        }
        else
        {
            int i = ((Integer)state.getValue(AGE)).intValue();

            if (i < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(5) == 0))
            {
                worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i + 1)), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }
    }*/

    public boolean canBlockStay(World worldIn, BlockPos pos, BlockState state)
    {
        BlockState state1 = worldIn.getBlockState(pos.up());
        return state1.getBlock() == Blocks.OAK_LEAVES;
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

    /*@Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, world, BlockPos pos, IBlockState state, int fortune)
    {
        if(((Integer)state.getValue(AGE)).intValue()==3)
        {
            drops.add(new ItemStack(fruit));
        }
    }*/

    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state)
    {
        return new ItemStack(fruit);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    /*public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf((meta & 15)));
    }*/

    /**
     * Convert the BlockState into the correct metadata value
     */
    /*public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((Integer)state.getValue(AGE)).intValue();
        return i;
    }*/

    /*protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }*/

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    /*public boolean isOpaqueCube(BlockState state)
    {
        return false;
    }*/

    /*@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }*/

    //@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn,
                                    Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        if(state.get(AGE)==3) {
            playerIn.addItemStackToInventory(new ItemStack(fruit));
            worldIn.setBlockState(pos, state.with(AGE, 0), 2);
            return true;
        }
        return false;

    }

    //Only Call When a Value for this already exists in BlockBaseFruit.EnumType
    //Automatically Creates Sapling that creates a FruitTree
    //Only Call once for each Fruit
    /*public BlockFruitSapling createFruitTreeAndSapling()
    {
        BlockFruitSapling ret = new BlockFruitSapling(this.getTypeName()+"_sapling",this,true);

        return ret;
    }*/

    /*public String getTypeName()
    {
        return type;
    }*/


}

