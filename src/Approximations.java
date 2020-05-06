import MatrixSolver.MatrixSolver;
import MatrixSolver.NewGauss;
import MatrixSolver.Matrix;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Approximations {

    public static double sumArr(Function<Double, Double> func, double[] arr){
        double result = 0;
        for(int i =0; i < arr.length; i++){
            result += func.apply(arr[i]);
        }

        return result;
    }

    public static double sumArrXY(BiFunction<Double, Double, Double> func, double[] arrX, double[] arrY){
        double result = 0;
        for(int i = 0; i < arrX.length; i++){
            result += func.apply(arrX[i], arrY[i]);
        }

        return result;
    }

    public static void linearApproximation(double[] arrX, double[] arrY){
        double SX = sumArr(x -> x, arrX);
        double SXX = sumArr(x -> Math.pow(x, 2), arrX);
        double SY = sumArr(y -> y, arrY);
        double SXY = sumArrXY((x,y) -> x*y, arrX, arrY);

        double n = arrX.length;

        double a = (SXY*n - SX*SY) / (SXX*n - SX*SX);
        double b = (SXX*SY - SX*SXY) / (SXX*n - SX*SX);

        double S = 0;
        double 𝜹 = 0;

        for(int i = 0; i < arrX.length; i++){
            System.out.println("X = "+ arrX[i] + " Y = "+ arrY[i]);
            System.out.println("ax + b = "+ (a * arrX[i] + b));
            double e = (a * arrX[i] + b) - arrY[i];
            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("Мера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);

        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    public static void polynominalApproximation(double[] arrX, double[] arrY){
        double SX    = sumArr(x -> x, arrX);
        double SXX   = sumArr(x -> Math.pow(x, 2), arrX);
        double SXXX  = sumArr(x -> Math.pow(x, 3), arrX);
        double SXXXX = sumArr(x -> Math.pow(x, 4), arrX);
        double SY    = sumArr(y -> y, arrY);
        double SXY   = sumArrXY((x,y) -> x*y, arrX, arrY);
        double SXXY  = sumArrXY((x, y) -> x*x*y, arrX, arrY);

        int m = 3, n = arrX.length;
        double[][] a = new double[3][3];
        a[0][0] = n;
        a[0][1] = SX;
        a[0][2] = SXX;
        a[1][0] = SX;
        a[1][1] = SXX;
        a[1][2] = SXXX;
        a[2][0] = SXX;
        a[2][1] = SXXX;
        a[2][2] = SXXXX;
        double[] b = new double[3];
        b[0] = SY;
        b[1] = SXY;
        b[2] = SXXY;
        n = 3;

        MatrixSolver ms = new MatrixSolver();

        double[] result = NewGauss.solveMatrix(ms, a, b, n, m);

        double S = 0;
        double 𝜹 = 0;

        for(int i = 0; i < arrX.length; i++){
            System.out.println("X = "+ arrX[i] + " Y = "+ arrY[i]);
            System.out.println("ax^2 + bx + c = "+ ((result[2] * arrX[i]* arrX[i]) + (result[1] * arrX[i]) + result[0]));
            double e = ((result[2] * arrX[i]* arrX[i]) + (result[1] * arrX[i]) + result[0]) - arrY[i];
            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("\n\nМера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);

        System.out.println("a = " + result[2]);
        System.out.println("b = " + result[1]);
        System.out.println("c = " + result[0]);


    }

    public static void exponentialApproximation(double[] arrX, double[] arrY){
        double [] ARR_Y = Arrays.stream(arrY).map(Math::log).toArray();
        double SX  = sumArr(x -> x, arrX);
        double SXX = sumArr(x -> x*x, arrX);
        double SY  = sumArr(y -> y, ARR_Y);
        double SXY = sumArrXY((x,y) -> x*y, arrX, ARR_Y);
        double n = arrX.length;

        double tempA = (SXY*n - SX*SY) / (SXX*n - SX*SX);
        double tempB = (SXX*SY - SX*SXY) / (SXX*n - SX*SX);
        double a = Math.exp(tempB);
        double b = tempA;

        double S = 0;
        double 𝜹 = 0;

        for(int i = 0; i < arrX.length; i++){
            System.out.println("X = "+ arrX[i] + " Y = "+ arrY[i]);
            System.out.println("ae^bx = "+ (a * Math.pow(Math.E, b * arrX[i])));
            double e = (a * Math.pow(Math.E, b * arrX[i])) - arrY[i];
            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("Мера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

    }

    public static void logarithmicApproximation(double[] arrX, double[] arrY){
        double [] ARR_X = Arrays.stream(arrX).map(Math::log).toArray();
        double SX  = sumArr(x -> x, ARR_X);
        double SXX = sumArr(x -> x*x, ARR_X);
        double SY  = sumArr(y -> y, arrY);
        double SXY = sumArrXY((x,y) -> x*y, ARR_X, arrY);
        double n = arrX.length;

        double a = (SXY*n - SX*SY) / (SXX*n - SX*SX);
        double b = (SXX*SY - SX*SXY) / (SXX*n - SX*SX);

        double S = 0;
        double 𝜹 = 0;

        for(int i = 0; i < arrX.length; i++){
            System.out.println("X = "+ arrX[i] + " Y = "+ arrY[i]);
            System.out.println("a*lnx + b = "+ (a * Math.log(arrX[i]) + b));
            double e = (a * Math.log(arrX[i]) + b) - arrY[i];
            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("Мера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

    }

    public static void powerApproximation(double[] arrX, double[] arrY){
        double [] logArrY = Arrays.stream(arrY).map(Math::log).toArray();
        double [] logArrX = Arrays.stream(arrX).map(Math::log).toArray();
        double SX  = sumArr(x -> x, logArrX);
        double SXX = sumArr(x -> x*x, logArrX);
        double SY  = sumArr(y -> y, logArrY);
        double SXY = sumArrXY((x,y) -> x*y, logArrX, logArrY);
        int n = arrX.length;

        double tempA = (SXY*n - SX*SY) / (SXX*n - SX*SX);
        double tempB = (SXX*SY - SX*SXY) / (SXX*n - SX*SX);

        double a = Math.exp(tempB);
        double b = tempA;

        double S = 0;
        double 𝜹 = 0;

        for(int i = 0; i < arrX.length; i++){
            System.out.println("X = "+ arrX[i] + " Y = "+ arrY[i]);
            System.out.println("a*x^b = "+ (a * Math.pow(arrX[i], b)));
            double e = (a * Math.pow(arrX[i], b)) - arrY[i];
            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("Мера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

    }
}
