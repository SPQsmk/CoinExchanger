import java.util.ArrayList;
import java.util.Collections;

public class Exchanger {
    public static void main(String[] args) {
        ArrayList<Integer> coins = new ArrayList<>();

        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        int sum = parseInt(args[0]);

        if (sum <= 0) {
            throw new IllegalArgumentException("Sum less than or equal to zero");
        }

        for (int i = 1; i < args.length; i++) {
            coins.add(parseInt(args[i]));

            if (coins.get(i - 1) <= 0) {
                throw new IllegalArgumentException("Number less than or equal to zero");
            }
        }

        coins.sort(Collections.reverseOrder());

        System.out.println(sum);
        System.out.println(coins);
        System.out.println();

        ArrayList<Integer> result = exchange(sum, coins, new ArrayList<>());

        if (result.isEmpty()) {
            System.out.println("Impossible exchange");
        } else {
            System.out.printf("%d -> ", sum);
            for (int i = 0; i < result.size() - 1; i++) {
                if (result.get(i) != 0)
                    System.out.printf("(%d * %d) + ", coins.get(i), result.get(i));
            }
            System.out.printf("(%d * %d)", coins.get(result.size() - 1), result.get(result.size() - 1));
        }
    }

    private static int parseInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static ArrayList<Integer> exchange(Integer sum, ArrayList<Integer> coins, ArrayList<Integer> coefficients) {
        int n, rem;

        n = sum / coins.get(0);
        rem = sum % coins.get(0);

        if (rem == 0) {
            coefficients.add(n);
            return coefficients;
        }

        if (coins.size() == 1) {
            return new ArrayList<>();
        }

        ArrayList<Integer> subCoins = new ArrayList<>(coins.subList(1, coins.size()));
        ArrayList<Integer> res = new ArrayList<>();

        for (int i = n; i >= 0; i--) {
            ArrayList<Integer> newCoefficients = new ArrayList<>(coefficients);
            newCoefficients.add(i);
            res = exchange(sum - i * coins.get(0), subCoins, newCoefficients);

            if (!res.isEmpty())
                break;
        }

        return res;
    }
}