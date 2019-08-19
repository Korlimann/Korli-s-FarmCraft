package com.korlimann.korlisfarmcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;

import java.util.List;

public class SaltOre extends Block {

    public SaltOre() {
        super(Properties.create(Material.ROCK)
                    .sound(SoundType.STONE)
                    .hardnessAndResistance(1.5f)
                    .harvestLevel(1)
        );
        setRegistryName("salt_ore");
    }
}
