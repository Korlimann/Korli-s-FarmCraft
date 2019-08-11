package com.korlimann.korlisfarmcraft.util;

import java.util.Random;

public class RngHelper {

    private RngHelper()
    {

    }

    //returns blocks per chunk for the given percentage and the Worldgen rng
    //anything above 100 has at least one block(above 200 has 2 Blocks etc..)
    public static int getPercentageRNG(Random rand, int percentage)
    {
        int tmp =0;
        while(percentage>0)
        {
            if(rand.nextInt(100)<percentage)
            {
                tmp++;
                percentage-=100;
            }
        }
        return tmp;
    }

    //runs the PercentageRNG the given number of times
    public static int getRepeatedPercentageRNG(Random rand, int percentage, int runs)
    {
        int tmp =0;
        for(int i=0;i<runs;i++)
        {
            tmp+= getPercentageRNG(rand, percentage);
        }
        return tmp;
    }
}
