package joshie.mariculture.helpers;

import java.util.Arrays;

public class GroupHelper {
    public static int getMostPopular(int[] array) {
        if (array == null || array.length == 0)
            return 0;
        Arrays.sort(array);
        int previous = array[0];
        int popular = array[0];
        int count = 1;
        int maxCount = 1;

        for (int i = 1; i < array.length; i++) {
            if (array[i] == previous)
                count++;
            else {
                if (count > maxCount) {
                    popular = array[i-1];
                    maxCount = count;
                }

                previous = array[i];
                count = 1;
            }
        }

        return count > maxCount ? array[array.length-1] : popular;
    }
}
