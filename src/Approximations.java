import MatrixSolver.MatrixSolver;
import MatrixSolver.NewGauss;

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

    public static double rFinder(double[] arrX, double[] arrY){
        //Подсчёт коэффициента кореляции(r)
        double averX = 0;
        double averY = 0;
        double numerator = 0; //числитель
        double denominator = 0; //знаменатель
        double denX = 0;
        double denY = 0;

        for(int i = 0; i < arrX.length; i++){
            averX += arrX[i];
            averY += arrY[i];
        }
        averX = averX / arrX.length;
        averY = averY / arrY.length;

        for(int i = 0; i < arrX.length; i++){
            numerator += (arrX[i]-averX)*(arrY[i]-averY);
            denX += Math.pow((arrX[i]-averX), 2);
            denY += Math.pow((arrY[i]-averY), 2);
        }
        denominator = Math.sqrt(denX*denY);

        return numerator / denominator;
    }

    public static double[] linearApproximation(double[] arrX, double[] arrY){
        double SX = sumArr(x -> x, arrX);
        double SXX = sumArr(x -> Math.pow(x, 2), arrX);
        double SY = sumArr(y -> y, arrY);
        double SXY = sumArrXY((x,y) -> x*y, arrX, arrY);

        double n = arrX.length;

        double a = (SXY*n - SX*SY) / (SXX*n - SX*SX);
        double b = (SXX*SY - SX*SXY) / (SXX*n - SX*SX);

        double S = 0;
        double 𝜹 = 0;
        double[] funcValues = new double[arrX.length+1];
        System.out.printf("%s %12s %20s %6s %9s %n", "X", "Y", "ax + b", "e", "p");

        for(int i = 0; i < arrX.length; i++){
            funcValues[i] = (a * arrX[i] + b);
            double e = (a * arrX[i] + b) - arrY[i];
            System.out.printf("%f %12f %12f %12f %12f %n", arrX[i], arrY[i], (a * arrX[i] + b), e, e/arrY[i]);
            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("Мера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);
        funcValues[arrX.length] = 𝜹;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        double r = rFinder(arrX, arrY);
        System.out.println("Коэффициент корреляции r = "+ r);

        GraphController gc = new GraphController();
        gc.buildGraphForOneMethod(arrX, arrY, funcValues, "Линейная аппроксимация");

        return funcValues;
    }

    public static double[] polynominalApproximation(double[] arrX, double[] arrY){
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
        double[] funcValues = new double[arrX.length + 1];

        System.out.printf("%n %s %12s %20s %6s %9s %n", "X", "Y", "ax^2 + bx + c", "e", "p");
        for(int i = 0; i < arrX.length; i++){
            funcValues[i] = ((result[2] * arrX[i]* arrX[i]) + (result[1] * arrX[i]) + result[0]);
            double e = ((result[2] * arrX[i]* arrX[i]) + (result[1] * arrX[i]) + result[0]) - arrY[i];
            System.out.printf("%f %12f %12f %12f %12f %n", arrX[i], arrY[i], ((result[2] * arrX[i]* arrX[i]) + (result[1] * arrX[i]) + result[0]), e, e/arrY[i]);

            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("\n\nМера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);

        System.out.println("a = " + result[2]);
        System.out.println("b = " + result[1]);
        System.out.println("c = " + result[0]);

        funcValues[arrX.length] = 𝜹;

        double r = rFinder(arrX, arrY);
        System.out.println("Коэффициент корреляции r = "+ r);

        GraphController gc = new GraphController();
        gc.buildGraphForOneMethod(arrX, arrY, funcValues, "Полиноминальная аппроксимация");

        return funcValues;

    }

    public static double[] exponentialApproximation(double[] arrX, double[] arrY){
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
        double[] funcValues = new double[arrX.length+1];

        System.out.printf("%s %12s %20s %6s %9s %n", "X", "Y", "ae^bx", "e", "p");
        for(int i = 0; i < arrX.length; i++){
            funcValues[i] = (a * Math.pow(Math.E, b * arrX[i]));
            double e = (a * Math.pow(Math.E, b * arrX[i])) - arrY[i];
            System.out.printf("%f %12f %12f %12f %12f %n", arrX[i], arrY[i], (a * Math.pow(Math.E, b * arrX[i])), e, e/arrY[i]);
            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("Мера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        funcValues[arrX.length] = 𝜹;

        double r = rFinder(arrX, arrY);
        System.out.println("Коэффициент корреляции r = "+ r);

        GraphController gc = new GraphController();
        gc.buildGraphForOneMethod(arrX, arrY, funcValues, "Экспоненциальная аппроксимация");

        return funcValues;
    }

    public static double[] logarithmicApproximation(double[] arrX, double[] arrY){
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
        double[] funcValues = new double[arrX.length + 1];

        System.out.printf("%s %12s %20s %6s %9s %n", "X", "Y", "a*lnx + b", "e", "p");
        for(int i = 0; i < arrX.length; i++){
            funcValues[i] = (a * Math.log(arrX[i]) + b);
            double e = (a * Math.log(arrX[i]) + b) - arrY[i];
            System.out.printf("%f %12f %12f %12f %12f %n", arrX[i], arrY[i], (a * Math.log(arrX[i]) + b), e, e/arrY[i]);
            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("Мера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        funcValues[arrX.length] = 𝜹;

        double r = rFinder(arrX, arrY);
        System.out.println("Коэффициент корреляции r = "+ r);

        GraphController gc = new GraphController();
        gc.buildGraphForOneMethod(arrX, arrY, funcValues, "Логарифмическая аппроксимация");

        return funcValues;
    }

    public static double[] powerApproximation(double[] arrX, double[] arrY){
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
        double[] funcValues = new double[arrX.length+1];

        System.out.printf("%s %12s %20s %6s %9s %n", "X", "Y", "a*x^b", "e", "p");
        for(int i = 0; i < arrX.length; i++){
            funcValues[i] = (a * Math.pow(arrX[i], b));
            double e = (a * Math.pow(arrX[i], b)) - arrY[i];
            System.out.printf("%f %12f %12f %12f %12f %n", arrX[i], arrY[i], (a * Math.pow(arrX[i], b)), e, e/arrY[i]);

            S += e * e;
        }
        𝜹 = Math.sqrt(S / arrX.length);

        System.out.println("Мера отклонения S = " + S);
        System.out.println("Среднеквадратичное отклонение \uD835\uDF39 = " + 𝜹);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        funcValues[arrX.length] = 𝜹;

        double r = rFinder(arrX, arrY);
        System.out.println("Коэффициент корреляции r = "+ r);

        GraphController gc = new GraphController();
        gc.buildGraphForOneMethod(arrX, arrY, funcValues, "Степенная аппроксимация");

        return funcValues;
    }

    public static void allApproxFunctions(double[] arrX, double[] arrY){
        GraphController gc = new GraphController();
        double[] linValues = linearApproximation(arrX, arrY);
        double[] polValues = polynominalApproximation(arrX, arrY);
        double[] expoValues = exponentialApproximation(arrX, arrY);
        double[] logValues = logarithmicApproximation(arrX, arrY);
        double[] powValues = powerApproximation(arrX, arrY);

        System.out.println("\nСреднеквадратические отклонения функций:");
        System.out.println("Линейное \uD835\uDF39 = " + linValues[arrX.length]);
        System.out.println("Полиноминальное \uD835\uDF39 = " + polValues[arrX.length]);
        System.out.println("Экспоненциальное \uD835\uDF39 = " + expoValues[arrX.length]);
        System.out.println("Логарифмическое \uD835\uDF39 = " + logValues[arrX.length]);
        System.out.println("Степенное \uD835\uDF39 = " + powValues[arrX.length]);

        System.out.println("\nЛучшая аппроксимирующая функция:");
        double min = linValues[arrX.length];
        String title = "Линейная аппроксимация";
        if(polValues[arrX.length]< min){
            min = polValues[arrX.length];
            title = "Полиноминальная аппроксимация";
        }
        if(expoValues[arrX.length]< min){
            min = expoValues[arrX.length];
            title = "Экспоненциальная аппроксимация";
        }
        if(logValues[arrX.length]< min){
            min = logValues[arrX.length];
            title = "Логарифмическая аппроксимация";
        }
        if(powValues[arrX.length]< min){
            min = powValues[arrX.length];
            title = "Степенная аппроксимация";
        }
        System.out.println(title+" имеет наименьшее \uD835\uDF39. \uD835\uDF39 = "+min);

        gc.buildGraphForAllMethods(arrX, arrY, linValues, polValues, expoValues, logValues, powValues);
    }
}
