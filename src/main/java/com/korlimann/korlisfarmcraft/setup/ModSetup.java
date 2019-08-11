package com.korlimann.korlisfarmcraft.setup;

import com.korlimann.korlisfarmcraft.blocks.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModSetup {

    public static ItemGroup itemGroup = new ItemGroup("KFC") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.SALT_ORE);
        }
    };

    public static void init()
    {

    }

}
