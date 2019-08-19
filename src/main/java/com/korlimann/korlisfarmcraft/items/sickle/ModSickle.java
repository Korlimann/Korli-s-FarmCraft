package com.korlimann.korlisfarmcraft.items.sickle;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.korlimann.korlisfarmcraft.setup.ModSetup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModSickle extends TieredItem {
    private final float attackDamage;
    private final float attackSpeed;

    public ModSickle(int uses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Ingredient repairMaterial,float attackSpeed, String registryName) {
        super(new IItemTier() {
            @Override
            public int getMaxUses() {
                return uses;
            }

            @Override
            public float getEfficiency() {
                return efficiency;
            }

            @Override
            public float getAttackDamage() {
                return attackDamage;
            }

            @Override
            public int getHarvestLevel() {
                return harvestLevel;
            }

            @Override
            public int getEnchantability() {
                return enchantability;
            }

            @Override
            public Ingredient getRepairMaterial() {
                return repairMaterial;
            }
        }, new Properties().maxStackSize(1).group(ModSetup.itemGroup));
        setRegistryName(registryName);
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage-1f;
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
            if(random.nextInt(100)<59) {
                player.inventory.addItemStackToInventory(new ItemStack(Items.WHEAT,1));
            }
            context.getItem().damageItem(1, player, (p_220042_0_) -> {
                p_220042_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
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
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220042_0_) -> {
            p_220042_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }
}
