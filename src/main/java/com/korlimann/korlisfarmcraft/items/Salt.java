package com.korlimann.korlisfarmcraft.items;

import com.korlimann.korlisfarmcraft.Main;
import net.minecraft.item.Item;

public class Salt extends Item{

    public Salt() {
        super(new Item.Properties()
                .maxStackSize(64)
                .group(Main.setup.itemGroup));
        setRegistryName("salt");
    }
}
