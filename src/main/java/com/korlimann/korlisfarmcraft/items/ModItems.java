package com.korlimann.korlisfarmcraft.items;

import com.korlimann.korlisfarmcraft.items.scythe.ModScythe;
import com.korlimann.korlisfarmcraft.items.sickle.ModSickle;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {

    @ObjectHolder("korlisfarmcraft:stone_sickle")
    public static ModSickle STONE_SICKLE;
    @ObjectHolder("korlisfarmcraft:iron_sickle")
    public static ModSickle IRON_SICKLE;
    @ObjectHolder("korlisfarmcraft:diamond_sickle")
    public static ModSickle DIAMOND_SICKLE;

    @ObjectHolder("korlisfarmcraft:stone_scythe")
    public static ModScythe STONE_SCYTHE;
    @ObjectHolder("korlisfarmcraft:iron_scythe")
    public static ModScythe IRON_SCYTHE;
    @ObjectHolder("korlisfarmcraft:diamond_scythe")
    public static ModScythe DIAMOND_SCYTHE;

    @ObjectHolder("korlisfarmcraft:salt")
    public static Salt SALT;
}
