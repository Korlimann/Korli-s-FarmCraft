package com.korlimann.korlisfarmcraft.setup;

import net.minecraft.world.World;

public interface IProxy {

    void init();

    World getClientWorld();
}
