package fun.tianlefirstweb;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public int largestVariance(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        int[] cnt = new int[26];
        for(var c : chars) cnt[c - 'a']++;
        int ans = 0;
        for(int i = 0; i < 26; i++){
            int[] num = Arrays.copyOf(cnt,26);
            int left = 0, right = n - 1;
            while(left < right){
                while(chars[left] != 'a' + i){
                    num[chars[left] - 'a']--;
                    left++;
                }
                while(chars[right] != 'a' + i){
                    num[chars[right] - 'a']--;
                    right--;
                }
                int first = -1;
                int second = -1;
                for(int j = 0; j < 26; j++){
                    if(first == -1 || num[first] < num[j]){
                        second = first;
                        first = j;
                    }else if(second == -1 || num[second] < num[j]){
                        second = j;
                    }
                    if(first == i && second != -1 && cnt[second] != 0){
                        ans = Math.max(ans,num[first] - num[second]);
                    }
                }
            }
        }
        return ans;
    }
}
