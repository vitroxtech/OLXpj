package squaring.vitrox.olxpj.Helper;

import java.util.Random;

/**
 * Created by miguelgomez on 10/27/16.
 */

public class RandomHelper {

    Random mRandom = new Random();

    /* Random generator in range*/
    public int getRandomIntInRange(int min, int max) {
        mRandom = new Random();
        int solve = mRandom.nextInt((max - min) + min) + min;
        System.out.println("random: " + solve);
        return solve;
    }
}
