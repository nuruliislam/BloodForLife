package capsulestudio.demobloodmanagement.utility;

import android.content.Context;
import android.support.v4.app.ActivityCompat;

import java.util.Random;

import capsulestudio.demobloodmanagement.R;

public class RandomColor {


    private static int getRandomNumber(int min,int mx){
        Random rand = new Random();
        return min + rand.nextInt((mx - min) + 1);
    }

    public static int getColor(Context context){
        int randNumber = getRandomNumber(0,9);
        switch (randNumber){
            case 0:
                return getColor(context, R.color.color1);
            case 1:
                return getColor(context, R.color.color_2);
            case 2:
                return getColor(context, R.color.color_3);
            case 3:
                return getColor(context, R.color.color_4);
            case 4:
                return getColor(context, R.color.color_5);
            case 5:
                return getColor(context, R.color.color_6);
            case 6:
                return getColor(context, R.color.color_7);
            case 7:
                return getColor(context, R.color.color_8);
            case 8:
                return getColor(context, R.color.color_9);
            default:
                return getColor(context, R.color.default_color);
        }
    }

    private static int getColor(Context context, int resourceId)
    {
        return ActivityCompat.getColor(context,resourceId);
    }

}