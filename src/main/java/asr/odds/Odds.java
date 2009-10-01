package asr.odds;

import java.util.Stack;

public class Odds
{
    private Stack<Double> probabilities = new Stack<Double>();
    private double        rs            = 0;
    private double        qs            = 1.0;
    private int           s             = 1;

    public boolean record(double probability)
    {
        if (probability < 0 || probability > 1.0) {
            throw new IllegalArgumentException(String.format("Can't record probability %f", probability));
        }

        probabilities.push(probability);

        rs = 0;
        qs = 1.0;

        for (int i = probabilities.size() - 1; i >= 0; i--) {
            Double p = probabilities.get(i);
            double q = (1 - p);
            rs += p / q;
            qs *= q;

            // Precision loss.
            if (rs >= 0.99999 && !Double.isInfinite(rs)) {
                s = probabilities.size() - i;
                return true;
            }
        }

        return false;
    }

    public int getStoppingIndex()
    {
        return s;
    }

    public double getWinProbability()
    {
        return qs * rs;
    }

    public static void main(String args[])
    {
//        Odds dice = new Odds();
//        while (!dice.record((double) 1 / (double) 6)) {
//        }
//        System.out.println(dice.getStoppingIndex());
//        System.out.println(dice.getWinProbability());
        
        Odds cars = new Odds();
        double k = 1;
        while (!cars.record((double) 1 / k)) {
            k++;
        }
        System.out.println(cars.getStoppingIndex());
        System.out.println(cars.getWinProbability());
    }
}
