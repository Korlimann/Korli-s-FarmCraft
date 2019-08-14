package com.korlimann.korlisfarmcraft.items.sickle;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.korlimann.korlisfarmcraft.setup.ModSetup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IronSickle extends HoeItem {

    private final float attackDamage;
    private final float attackSpeed;

    public IronSickle() {
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
        }, 1.6f, new Properties().maxStackSize(1).group(ModSetup.itemGroup));
        setRegistryName("iron_sickle");
        this.attackSpeed = -2.4f;
        this.attackDamage = 1.0f;
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
            if(random.nextInt(100)<39) {
                player.inventory.addItemStackToInventory(new ItemStack(Items.WHEAT,1));
            }
        }
        return ActionResultType.SUCCESS;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }
}
