package com.korlimann.korlisfarmcraft.world.gen;

import java.util.Random;

import com.korlimann.korlisfarmcraft.init.ModBlocks;
import com.korlimann.korlisfarmcraft.util.RngHelper;

import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.fml.common.IWorldGenerator;

public class KFCWorldGen implements IWorldGenerator
{
    /*
     * This class is used for implementing, for example, new ores into the world generation.
     * That means, that when you create a new world, salt ore will be spread all over the world.
     * */

    private Feature fruit_tree_avocado;

    public KFCWorldGen()
    {
        fruit_tree_avocado = new WorldGenFruitTree(true, 5, ModBlocks.AVOCADO_BLOCK);
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator chunkGenerator, AbstractChunkProvider chunkProvider)
    {
        switch(world.dimension.getDimension())
        {
            case 0:
                //chunkChance is the percentage how likely it is to spawn a patch in a Chunk
                //any ChunkChance higher then including 100 is always true
                //PatchPercentage is the percentage the generator gets for blocks in a Chunk
                //PatchRuns is the number of times the PercentageRNG runs to get the Blocks per Chunk


                //Min Height does not affect this Generator
                runGeneratorFruitTree(fruit_tree_avocado, world, random, chunkX, chunkZ, 54, 4,95,60, 256);

                break;

            case 1:

                break;

            case -1:

                break;
        }
    }

    private void runGeneratorOre(Feature gen, World world, Random rand, int chunkX, int chunkZ, int patchPercentage,int patchRuns,int chunkChance, int minHeight, int maxHeight)
    {
        if(RngHelper.getPercentageRNG(rand, chunkChance)>=1)
        {
            if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");
            int heightDiff = maxHeight - minHeight + 1;

            int bpc = RngHelper.getRepeatedPercentageRNG(rand, patchPercentage, patchRuns);
            for(int i = 0; i < bpc; i++)
            {
                int x = chunkX * 16 + rand.nextInt(16);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = chunkZ * 16 + rand.nextInt(16);

                gen.place(world, SMTH WEIRD GOES HERE, rand, new BlockPos(x, y, z));
            }
        }
    }

    private void runGeneratorSeaweed(Feature gen, World world, Random rand, int chunkX, int chunkZ, int patchPercentage,int patchRuns,int chunkChance, int minHeight, int maxHeight)
    {

        if(RngHelper.getPercentageRNG(rand, chunkChance)>=1)
        {
            if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");
            int heightDiff = maxHeight - minHeight + 1;


            int bpc = RngHelper.getRepeatedPercentageRNG(rand, patchPercentage, patchRuns);
            for(int i = 0; i < bpc; i++)
            {
                int x = 2+ chunkX * 16 + rand.nextInt(8);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = 2+ chunkZ * 16 + rand.nextInt(8);

                gen.place(world, SMTH WEIRD GOES HERE, rand, new BlockPos(x, y, z));

            }}
    }
    private void runGeneratorFruitTree(Feature gen, World world, Random rand, int chunkX, int chunkZ, int patchPercentage,int patchRuns,int chunkChance, int minHeight, int maxHeight)
    {

        if(RngHelper.getPercentageRNG(rand, chunkChance)>=1)
        {
            if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");
            int heightDiff = maxHeight - minHeight + 1;


            int bpc = RngHelper.getRepeatedPercentageRNG(rand, patchPercentage, patchRuns);
            for(int i = 0; i < bpc; i++)
            {
                int x = 2+ chunkX * 16 + rand.nextInt(8);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = 2+ chunkZ * 16 + rand.nextInt(8);

                gen.place(world, SMTH WEIRD GOES HERE, rand, new BlockPos(x, y, z));

            }}
    }
    private void runGeneratorHerbs(Feature gen, World world, Random rand, int chunkX, int chunkZ, int patchPercentage,int patchRuns,int chunkChance, int minHeight, int maxHeight)
    {
        if(RngHelper.getPercentageRNG(rand, chunkChance)>=1)
        {
            if(minHeight > maxHeight || minHeight < 60) throw new IllegalArgumentException("Herbs generated out of bounds");

            int bpc = RngHelper.getRepeatedPercentageRNG(rand, patchPercentage, patchRuns);

            for(int i = 0; i < bpc; i++)
            {
                int x = 2 + chunkX * 16 + rand.nextInt(8);
                int y = maxHeight;
                int z = 2 + chunkZ * 16 + rand.nextInt(8);

                gen.place(world, SMTH WEIRD GOES HERE, rand, new BlockPos(x, y, z));
            }
        }
    }
}
