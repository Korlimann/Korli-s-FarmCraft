package com.korlimann.korlisfarmcraft.init;

import com.korlimann.korlisfarmcraft.items.ItemBaseFood;
import com.korlimann.korlisfarmcraft.items.TestItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder("korlisfarmcraft:testitem")
    public static TestItem TESTITEM;

    @ObjectHolder("korlisfarmcraft:avocado")
    public static ItemBaseFood AVOCADO = new ItemBaseFood("avocado", 2, 0.3f, false);
}
