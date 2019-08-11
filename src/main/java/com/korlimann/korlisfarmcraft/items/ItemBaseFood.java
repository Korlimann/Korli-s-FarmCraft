package com.korlimann.korlisfarmcraft.items;

import com.korlimann.korlisfarmcraft.Main;
import com.korlimann.korlisfarmcraft.util.IHasModel;
import net.minecraft.item.Food;

public class ItemBaseFood extends Food implements IHasModel {

    /*
     * This class creates custom food.
     * */

    public ItemBaseFood(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        setUnlocalizedName(name);
        setRegistryName(name);
        //setCreativeTab(Main.korlissushicraft);

        //ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {

    }
}
