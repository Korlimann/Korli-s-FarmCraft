package com.korlimann.korlisfarmcraft.items;

import com.korlimann.korlisfarmcraft.items.scythe.DiamondScythe;
import com.korlimann.korlisfarmcraft.items.scythe.IronScythe;
import com.korlimann.korlisfarmcraft.items.scythe.StoneScythe;
import com.korlimann.korlisfarmcraft.items.sickle.StoneSickle;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder("korlisfarmcraft:stone_sickle")
    public static StoneSickle STONE_SICKLE;
    @ObjectHolder("korlisfarmcraft:iron_sickle")
    public static StoneSickle IRON_SICKLE;
    @ObjectHolder("korlisfarmcraft:diamond_sickle")
    public static StoneSickle DIAMOND_SICKLE;

    @ObjectHolder("korlisfarmcraft:stone_scythe")
    public static StoneScythe STONE_SCYTHE;
    @ObjectHolder("korlisfarmcraft:iron_scythe")
    public static IronScythe IRON_SCYTHE;
    @ObjectHolder("korlisfarmcraft:diamond_scythe")
    public static DiamondScythe DIAMOND_SCYTHE;

    @ObjectHolder("korlisfarmcraft:salt")
    public static Salt SALT;
}
