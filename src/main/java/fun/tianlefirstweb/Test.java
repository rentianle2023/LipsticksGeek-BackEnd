package fun.tianlefirstweb;

import java.util.HashMap;
import java.util.HashSet;

public class Test {

    HashMap<Integer,Integer> row = new HashMap<>();
    HashMap<Integer,Integer> col = new HashMap<>();
    HashMap<Integer,Integer> pos = new HashMap<>();
    HashMap<Integer,Integer> neg = new HashMap<>();
    HashSet<Long> lights = new HashSet<>();
    int n;

    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
        int m = queries.length;
        int[] res = new int[m];
        this.n = n;

        for(var lamp : lamps){
            int r = lamp[0], c = lamp[1];
            if(lights.contains(hash(r,c))) continue;

            row.put(r,row.getOrDefault(r,0) + 1);
            col.put(c,col.getOrDefault(c,0) + 1);
            pos.put(r + c,pos.getOrDefault(r + c,0) + 1);
            neg.put(r - c,neg.getOrDefault(r - c,0) + 1);
            lights.add(hash(r,c));
        }

        for(int i = 0; i < m; i++){
            var query = queries[i];
            int r = query[0], c = query[1];

            if(row.containsKey(r) || col.containsKey(c) || pos.containsKey(r + c) || neg.containsKey(r - c)){
                res[i] = 1;
            }

            for(int j = r - 1; j <= r + 1; j++){
                for(int k = c - 1; k <= c + 1; k++){
                    turnOff(j,k);
                }
            }
        }

        return res;
    }

    public void turnOff(int r, int c) {
        if(r < 0 || c < 0 || r >= n || c >= n) return;

        if(lights.contains(hash(r,c))){

            if(row.get(r) == 1) row.remove(r);
            else row.put(r,row.get(r) - 1);

            if(col.get(c) == 1) col.remove(c);
            else col.put(c,col.get(c) - 1);

            if(pos.get(r + c) == 1) pos.remove(r + c);
            else pos.put(r + c,pos.get(r + c) - 1);

            if(neg.get(r - c) == 1) neg.remove(r - c);
            else neg.put(r - c,neg.get(r - c) - 1);

            lights.remove(hash(r,c));
        }
    }

    public long hash(int r, int c) {
        return ((long)r << 32) + c;
    }
}
