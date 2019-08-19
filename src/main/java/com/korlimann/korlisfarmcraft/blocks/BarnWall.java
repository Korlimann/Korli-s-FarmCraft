package com.korlimann.korlisfarmcraft.blocks;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import java.util.Map;

public class BarnWall extends SixWayBlock {

    private static final Direction[] FACING_VALUES = Direction.values();
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (p_203421_0_) -> {
        p_203421_0_.put(Direction.NORTH, NORTH);
        p_203421_0_.put(Direction.EAST, EAST);
        p_203421_0_.put(Direction.SOUTH, SOUTH);
        p_203421_0_.put(Direction.WEST, WEST);
        p_203421_0_.put(Direction.UP, UP);
        p_203421_0_.put(Direction.DOWN, DOWN);
    });

    public BarnWall() {
        super(1.0f, Properties.create(Material.WOOD)
                .sound(SoundType.WOOD)
                .hardnessAndResistance(2.0f)
                .harvestLevel(1));
        setRegistryName("barn_wall");
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return super.propagatesSkylightDown(state, reader, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    protected int getShapeIndex(BlockState state) {
        return super.getShapeIndex(state);
    }

    /*public BarnWall() {
        super(Properties.create(Material.WOOD)
                .sound(SoundType.WOOD)
                .hardnessAndResistance(2.0f)
                .harvestLevel(1)
        );
        setRegistryName("barn_wall");
    }*/


}
