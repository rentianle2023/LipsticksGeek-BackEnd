package fun.tianlefirstweb.www.search.lipstickColor;

import java.awt.*;

public class ColorUtil {

    public static float[] HEXtoHSB(String hexColor){
        if(hexColor.startsWith("#")) hexColor = hexColor.substring(1);
        int r = Hex2Integer(hexColor.substring(0,2));
        int g = Hex2Integer(hexColor.substring(2,4));
        int b = Hex2Integer(hexColor.substring(4,6));

        float[] hsb = new float[3];
        Color.RGBtoHSB(r,g,b,hsb);
        return hsb;
    }

    private static Integer Hex2Integer(String hex){
        int tens = 0, ones = 0;
        if(hex.charAt(1) > '9') ones = hex.charAt(1) - 'a' + 10;
        else ones = hex.charAt(1) - '0';
        if(hex.charAt(0) > '9') tens = hex.charAt(0) - 'a' + 10;
        else tens = hex.charAt(0) - '0';
        return tens * 16 + ones;
    }
}
