package com.korlimann.korlisfarmcraft.items.sickle;

import com.korlimann.korlisfarmcraft.setup.ModSetup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoneSickle extends HoeItem {

    public StoneSickle() {
        super(new IItemTier() {
            @Override
            public int getMaxUses() {
                return 128;
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
        }, 2.0f, new Properties().maxStackSize(1).group(ModSetup.itemGroup));
        setRegistryName("stone_sickle");
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos pos = context.getPos();
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(pos);
        if(blockState.getBlock() == Blocks.WHEAT && ((CropsBlock)Blocks.WHEAT).isMaxAge(blockState)) {
            player.inventory.addItemStackToInventory(new ItemStack(Items.WHEAT,1));
            world.setBlockState(pos,((CropsBlock)Blocks.WHEAT).withAge(0));
        }
        return ActionResultType.SUCCESS;
    }
}
