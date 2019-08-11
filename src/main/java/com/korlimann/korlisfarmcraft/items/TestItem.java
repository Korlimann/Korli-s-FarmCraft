package com.korlimann.korlisfarmcraft.items;

import com.korlimann.korlisfarmcraft.Main;
import net.minecraft.item.Item;

public class TestItem extends Item {

    public TestItem() {
        super(new Item.Properties()
                .maxStackSize(1)
                .group(Main.setup.itemGroup));
        setRegistryName("testitem");
    }
}
