package com.korlimann.korlisfood.setup;

import com.korlimann.korlisfood.blocks.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModSetup {

    public static ItemGroup itemGroup = new ItemGroup("KFC") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.TESTBLOCK);
        }
    };

    public static void init()
    {

    }

}
