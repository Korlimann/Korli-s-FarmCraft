package com.korlimann.korlisfarmcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RoofFlatLeft extends Block {

    public RoofFlatLeft() {
        super(Properties.create(Material.WOOD)
                .sound(SoundType.WOOD)
                .hardnessAndResistance(2.0f)
                .harvestLevel(1)
        );
        setRegistryName("roof_flat_left");
    }
}
