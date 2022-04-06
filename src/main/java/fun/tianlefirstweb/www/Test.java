package fun.tianlefirstweb.www;

public class Test {

    public static void main(String[] args) {
        System.out.println(-2 >> 3);
    }

    public double myPow(double x, int n) {
        double ans = 0;
        while(x != 0){
            if((n & 1) == 1){
                ans += x;
            }
            x *= 2;
            n >>= 1;
        }
        return ans;
    }
}
