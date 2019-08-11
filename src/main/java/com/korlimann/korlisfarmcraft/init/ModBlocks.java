package com.korlimann.korlisfarmcraft.init;

import com.korlimann.korlisfarmcraft.blocks.BlockBaseFruit;
import com.korlimann.korlisfarmcraft.blocks.TestBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("korlisfarmcraft:testblock")
    public static TestBlock TESTBLOCK;

    @ObjectHolder("korlisfarmcraft:avocado_block")
    public static BlockBaseFruit AVOCADO_BLOCK = new BlockBaseFruit("avocado_block", Material.PLANTS, ModItems.AVOCADO);
}
