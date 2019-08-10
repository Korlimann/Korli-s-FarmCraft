package com.korlimann.korlisfarmcraft.items;

import com.sun.scenario.effect.Crop;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.state.IProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlockState;

public class Sickle extends HoeItem {


    public Sickle() {
        super(new IItemTier() {
            @Override
            public int getMaxUses() {
                return 256;
            }

            @Override
            public float getEfficiency() {
                return 1.0f;
            }

            @Override
            public float getAttackDamage() {
                return 2.0f;
            }

            @Override
            public int getHarvestLevel() {
                return 4;
            }

            @Override
            public int getEnchantability() {
                return 0;
            }

            @Override
            public Ingredient getRepairMaterial() {
                return null;
            }
        }, 2.0f, new Properties().maxStackSize(1));
        setRegistryName("sickle");
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos pos = context.getPos();
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(pos);
        if(blockState.getBlock() == Blocks.WHEAT) {
            player.inventory.add(1, new ItemStack(Items.WHEAT));
        }
        return ActionResultType.SUCCESS;
    }
}
