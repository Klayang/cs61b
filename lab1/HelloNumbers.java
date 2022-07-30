public class HelloNumbers {
    public static void main(String[] args) {
        for (int i = 0; i < 10; ++i){
            int num = 0;
            for (int j = 0; j <= i; ++j)
                num += j;
            System.out.print(num);
            System.out.print(' ');
        }
        System.out.println();
    }
}