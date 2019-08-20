package com.korlimann.korlisfarmcraft.blocks;

import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockBaseFruit extends Block implements IGrowable {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    private static VoxelShape SHAPE = null;
    private Item fruit;
    private boolean canGrow;
    private boolean canUseBonemeal;
    private String type;

    public BlockBaseFruit(String name, double x1, double y1, double z1, double x2, double y2, double z2, Item fruit, boolean grow, boolean bonemeal) {
        super(Properties.create(Material.LEAVES)
                .sound(SoundType.PLANT)
                .hardnessAndResistance(1.0f)
                .harvestLevel(1)
                .tickRandomly()
        );
        this.fruit = fruit;
        this.canGrow = grow;
        this.canUseBonemeal = bonemeal;
        this.type=name;
        SHAPE = Block.makeCuboidShape(x1, y1, z1, x2, y2, z2);
        this.setDefaultState(this.stateContainer.getBaseState().with(this.getAgeProperty(), 0));
        setRegistryName(name);
    }

    public BlockBaseFruit(String name, Material materialIn, Item fruit)
    {
        this(name,0.25D, 0.25D, 0.25D, 0.75D, 1D, 0.75D, fruit, true, true);
    }

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

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (this.canBlockStay(worldIn, pos, state))
        {
            worldIn.destroyBlock(pos, false);
        }
        else
        {
            int i = state.get(AGE);
            if (i < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(5) == 0))
            {
                grow(worldIn, random, pos, state);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos, BlockState state)
    {
        BlockState state1 = worldIn.getBlockState(pos.up());
        return state1.getBlock() != Blocks.OAK_LEAVES;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (this.canBlockStay(worldIn, pos, state))
        {
            worldIn.destroyBlock(pos, false);
        }
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(state.get(AGE)==3) {
            player.addItemStackToInventory(new ItemStack(fruit));
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

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(AGE);
    }
}

