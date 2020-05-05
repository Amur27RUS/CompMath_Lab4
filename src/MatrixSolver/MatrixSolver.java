package MatrixSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatrixSolver {

    public double triangleDeterminantFinder(double[][] a, int count, int n){
        double determinant = 1;
        for(int i = 0; i<n; i++){
            determinant *= a[i][i];
        }

        if(!(count % 2 == 0)){
            determinant *= -1;
        }
        return determinant;
    }

    public double[] getResultsFromTriangleMatrix(double[] resultArray, double[][] a, double[] b, int n){

        for (int i = n - 1; i >= 0; i--) { //Проходим по элементам, начиная с последней строки и считаем решения
            double s = 0.0;

            for (int j = i + 1; j < n; j++) {
                s += a[i][j] * resultArray[j];
            }
            resultArray[i] = (b[i] - s) / a[i][i]; //Решения
        }
        return resultArray;
    }

    public double[][] rearrangeLines(double[][] a, double[] b, int i){

            double[] temp = a[i];
            a[i] = a[i+1];
            a[i+1] = temp;

            b[i] = temp[i];
            b[i] = b[i+1];
            b[i+1] = temp[i];

            return a;
    }

    public double[][] matrixToTriangle(double[][] a, double[] b, int i, int n){
        for(int k = i+1;k<n;k++){

            double c = a[k][i] / a[i][i];
            a[k][i] = 0;

            for(int j =i+1;j<n;j++){
                a[k][j] = a[k][j] - c * a[i][j];
            }

            b[k] =b[k] - c * b[i];

        }
        return a;
    }

    public Matrix readMatrixFromFile(Matrix matrix){
        System.out.println("Некоторые особенности чтения файлов:");
        System.out.println("Сначала внесите в файл 2 числа: кол-во ур-ий и кол-во переменных.");
        System.out.println("После чего внесите саму матрицу, включая коэффициенты справа");
        System.out.println(" ");
        System.out.println("Поместите файл в папку src и введите название файла:");

        Scanner scn = new Scanner(System.in);
        String path = "/Users/andreyklyuev/Desktop/ВУЗ/4 семестр/Лабы/Вычислительная математика/Lab1VM/src/" +scn.next();

        try {
            Scanner sc = new Scanner(new File(path));

            matrix.setN(sc.nextInt());
            matrix.setM(sc.nextInt());

            double[][] a = new double[100][100];
            double[] b = new double[100];

            for (int i = 0; i < matrix.getN(); i++) {

                for (int j = 0; j < matrix.getM(); j++) {
                    a[i][j] = sc.nextDouble();
                }
                b[i] = sc.nextDouble();
            }
            matrix.setA(a);
            matrix.setB(b);

            System.out.println("Файл прочитан!");

        }catch (FileNotFoundException e){
            System.out.println("Файл не найден!");
        }

        return matrix;
    }

    public Matrix readMatrix(Matrix matrix){

        double[][] a = new double[100][100];
        double[] b = new double[100];

        Scanner scn = new Scanner(System.in);

        System.out.println("Введите количество ур-ий:");
        matrix.setN(scn.nextInt());

        System.out.println("Введите кол-во неизвестных в матрице:");
        matrix.setM(scn.nextInt());


        System.out.println("Заполнение матрицы:");

        for (int i = 0; i < matrix.getN(); i++) {
            int p = i + 1;
            System.out.println("Введите " + (matrix.getM() + 1) + " коэффициента(ов) " + p + "-го уравнения: (Например: 1 2 3 4)");

            for (int j = 0; j < matrix.getM(); j++) {
                a[i][j] = scn.nextDouble();
            }
            b[i] = scn.nextDouble();
        }

        matrix.setA(a);
        matrix.setB(b);

        return matrix;
    }

}
