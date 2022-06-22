package fun.tianlefirstweb.www;

import fun.tianlefirstweb.www.search.lipstickColor.ColorUtil;

import java.awt.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        String[] colors = new String[]{"#c21e7c","#ab6241","#A84539","#c76864","#A7170b","#721e4b","#b48081","#690d0e","#b42225","#9e2625","#d39387","#950f1a","#88423c","#d41c5a","#a32525","#ec827a","#B71C1C","#D32F2F","#F44336","#E57373","#E65100","#C2185B"};
        Arrays.sort(colors);

        TreeMap<float[],String> hsb = new TreeMap<>((a,b) -> {
            if(a[0] < b[0]) return 1;
            else return -1;
        });
        for(var color : colors) {
            float[] floats = ColorUtil.HEXtoHSB(color);
            hsb.put(floats,color);
            for(var f : floats) System.out.println(f);
            System.out.println("---");
        }

        StringBuilder sb = new StringBuilder();
        hsb.forEach((key,value) -> {
            sb.append("\"");
            sb.append(value);
            sb.append("\",");
        });
        System.out.println(sb.toString());
    }
}
