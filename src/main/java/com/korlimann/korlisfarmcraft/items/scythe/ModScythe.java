package com.korlimann.korlisfarmcraft.items.scythe;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ModScythe extends HoeItem {
    private final float attackDamage;
    private final float attackSpeed;

    private final int minX;
    private final int maxX;

    private final int minZ;
    private final int maxZ;

    private static final Random rnd = new Random();

    public ModScythe(int uses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Ingredient repairMaterial,float attackSpeed, String registryName,int range) {
        super(new IItemTier() {
            @Override
            public int getMaxUses() {
                return uses;
            }

            @Override
            public float getEfficiency() { return efficiency; }

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
        }, attackSpeed*-1, new Item.Properties().maxStackSize(1).group(ModSetup.itemGroup));
        setRegistryName(registryName);
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage-1f;
        this.minX = range/2*-1;
        this.maxX = range/2;
        this.minZ = range/2*-1;
        this.maxZ = range/2;
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
                            if(random.nextInt(100)<29) {
                                PlayerEntity playerEntity = (PlayerEntity)entityLiving.getEntity();
                                playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.WHEAT,1));
                            }
                        }
                    }
                }
            }
        }
        return false;
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
