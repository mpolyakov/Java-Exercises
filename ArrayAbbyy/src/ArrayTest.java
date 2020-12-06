import java.util.Arrays;

public class ArrayTest {
    public static void main(String[] args) {


        int[] array = {6, 1,  4, 5, 0};
        Arrays.sort(array);
        int n = array.length + 1;

        findTwo(array, n);
    }

    private static void findTwo(int[] array, int n) {
        int x = -1;
        int y;
        int perfectSum = n * (n + 1) / 2;
        int currentPerfectSum;
        int sumArr = 0;

        for (int i = 0; i <= array.length - 1; i++) {
            currentPerfectSum = i * (i + 1) / 2;
            sumArr = sumArr + array[i];
            System.out.println("Шаг " + i + " sumArr: " + sumArr + ", currentPerfectSum:  " + currentPerfectSum);
            if ((currentPerfectSum != sumArr) && (x < 0)) {
                x = i;
            }
        }
        if (x < 0) x = array.length;
        System.out.println("Первое пропущенное число " + x);
        y = perfectSum - sumArr - x;
        System.out.println("Второе пропущенное число " + y);
    }

}
