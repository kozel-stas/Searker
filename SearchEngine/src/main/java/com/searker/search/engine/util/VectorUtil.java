package com.searker.search.engine.util;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class VectorUtil {

    public static double scalarMultiplication(double[] a, double[] b, double defaultValue) {
        double[] m1, m2;
        if (a.length > b.length) {
            m1 = a;
            m2 = b;
        } else {
            m1 = b;
            m2 = a;
        }
        double result = 0;
        for (int i = 0; i < m1.length; i++) {
            if (m2.length > i) {
                result += m2[i] * m1[i];
            } else {
                result += defaultValue * m1[i];
            }
        }
        return result;
    }

    public static double euclideanNorm(double[] a) {
        double result = 0;
        int i =0;
        for (double v : a) {
            result += v * v;
            i++;
        }
        return Math.sqrt(result);
    }

    public static int[] sum(int[] a, int[] b, int num) {
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i];
            if (i < b.length && b[i] > 0) {
                res[i] += num;
            }
        }
        return res;
    }

}
