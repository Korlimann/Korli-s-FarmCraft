package com.korlimann.korlisfarmcraft.blocks;

import net.minecraft.item.Items;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("korlisfarmcraft:salt_ore")
    public static SaltOre SALT_ORE;

    @ObjectHolder("korlisfarmcraft:apple_block")
    public static BlockBaseFruit APPLE_BLOCK = new BlockBaseFruit("apple_block", 6.25, 12, 6.25, 9.75, 16D, 9.75D, Items.APPLE, true, true);

    @ObjectHolder("korlisfarmcraft:apple_block_sapling")
    public static BlockBaseFruitSapling APPLE_BLOCK_SAPLING;
}
