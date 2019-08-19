package com.korlimann.korlisfarmcraft.items;

import com.korlimann.korlisfarmcraft.setup.ModSetup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Scythe extends HoeItem {

    private static final int minX = -1;
    private static final int maxX = 1;

    private static final int minZ = -1;
    private static final int maxZ = 1;

    public Scythe() {
        super(new IItemTier() {
            @Override
            public int getMaxUses() {
                return 512;
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
        setRegistryName("scythe");
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(state.getBlock() == Blocks.WHEAT)
        {
            if(((CropsBlock)Blocks.WHEAT).isMaxAge(state))
            {
                for(int i = minZ; i<=maxZ;i++) {
                    for(int j = minX;j<=maxX;j++) {
                        BlockPos pos1 = pos.add(j, 0, i);
                        BlockState state1 = worldIn.getBlockState(pos1);
                        if (state1.getBlock() == Blocks.WHEAT && (((CropsBlock) Blocks.WHEAT).isMaxAge(state1))) {
                            worldIn.destroyBlock(pos1,true);
                        }
                    }
                }
            }
        }
        return false;
    }
}