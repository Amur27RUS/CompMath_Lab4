package MatrixSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class NewGauss {



    public static void gaussMethod(){
        DecimalFormat df = new DecimalFormat("###.###");

        /*------ВВОД ДАННЫХ:-------*/
        int n = 0; //кол-во уравнений в матрице
        int m = 0; //кол-во неизвестных в матрице
        int pereCount = 0; //Счётчик перестановок. Нужен для получения определителя
        double determinant;
        int howToEnter;
        double[][] a = new double[100][100]; //Основная матрица
        double[] b = new double[100]; //Значения справа
        MatrixSolver ms = new MatrixSolver();
        Matrix matrix = new Matrix(a, b, n, m);



        System.out.println("Приветствуем! Как вы хотите ввести данные?");
        System.out.println("С файла- 1, вводить самому - 0");

        Scanner scn = new Scanner(System.in);

        howToEnter = scn.nextInt();

        if(howToEnter == 1){
            System.out.println("Некоторые особенности чтения файлов:");
            System.out.println("Сначала внесите в файл 2 числа: кол-во ур-ий и кол-во переменных.");
            System.out.println("После чего внесите саму матрицу, включая коэффициенты справа");
            System.out.println(" ");
            System.out.println("Поместите файл в папку src и введите название файла:");

            String path = "/Users/andreyklyuev/Desktop/ВУЗ/4 семестр/Лабы/Вычислительная математика/Lab1VM/src/" +scn.next();

            try {
                Scanner sc = new Scanner(new File(path));

                n= sc.nextInt();
                m= sc.nextInt();

                matrix.setN(n);
                matrix.setM(m);

                for (int i = 0; i < n; i++) {

                    for (int j = 0; j < m; j++) {
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

        }else if(howToEnter == 0) {


            System.out.println("Введите количество ур-ий:");
            n = scn.nextInt();

            System.out.println("Введите кол-во неизвестных в матрице:");
            m = scn.nextInt();


            System.out.println("Заполнение матрицы:");

            for (int i = 0; i < n; i++) {
                int p = i + 1;
                System.out.println("Введите " + (m + 1) + " коэффициента(ов) " + p + "-го уравнения: (Например: 1 2 3 4)");

                for (int j = 0; j < m; j++) {
                    a[i][j] = scn.nextDouble();
                }
                b[i] = scn.nextDouble();
            }
            matrix.setA(a);
            matrix.setB(b);

        }else{
            System.out.println("Такое не прокатит...");
            return;
        }


        /*------МЕТОД ГАУССА--------*/
        /*------Прямой ход:---------*/

        for(int i = 0;i<n-1;i++){

            if(a[i][i] == 0){ //Если элемент диагонали равен нулю, переставляем строки
                a = ms.rearrangeLines(a, b, i);
                pereCount++;
            }

            if(a[i][i] == 0){
                System.out.println("Определитель равен нулю! Видимо, у матрицы нет или бесконечное множество решений...");
                return;
            }

            for(int k = i+1; k<n; k++){

                double c = a[k][i] / a[i][i];
                a[k][i] = 0;

                for(int j =i+1;j<n;j++){
                    a[k][j] = a[k][j] - c * a[i][j];
                }

                b[k] =b[k] - c * b[i];

            }

        }
            determinant = ms.triangleDeterminantFinder(a, pereCount, n); // Нахождение определителя

        if(determinant == 0){
            System.out.println("Определитель равен нулю! Видимо, у матрицы нет или бесконечное множество решений...");
            return;
        }

            System.out.println("Ваша матрица в треугольном виде:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.printf("%15s", df.format(a[i][j]));
                }
                System.out.printf("%15s"," | ");
                System.out.printf("%2s %n",df.format(b[i]));
            }



            /*------Обратный ход:---------*/

            double[] x = new double[n];
            x = ms.getResultsFromTriangleMatrix(x, a, b, n);


            System.out.println("Определитель вашей матрицы:");
            System.out.printf("%7s %n", df.format(determinant));


            System.out.println("Программа получила решения:");
            int z = 0;
            for (int i = 0; i < n; i++) {
                z++;
                System.out.printf("%7s", "X" + z + " = " + x[i] + " ");

            }

            System.out.println(" ");
            System.out.println("Невязки R:");

            z=0;
            for(int i=0;i<n;i++){ //Подсчёт невязок: r = a*x -b
                z++;
                double nev = 0;
                for(int j=0;j<m;j++){
                    nev += a[i][j] * x[j];
                }
                nev -= b[i];
                System.out.print("R"+z+" = "+ nev + " ");
            }


    }

    public static double[] solveMatrix(MatrixSolver ms, double[][] a, double[] b, int n, int m){

        /*------МЕТОД ГАУССА--------*/
        /*------Прямой ход:---------*/
        int pereCount = 0;
        double determinant;
        DecimalFormat df = new DecimalFormat("###.###");

        for(int i = 0;i<n-1;i++){

            if(a[i][i] == 0){ //Если элемент диагонали равен нулю, переставляем строки
                a = ms.rearrangeLines(a, b, i);
                pereCount++;
            }

            if(a[i][i] == 0){
                System.out.println("Определитель равен нулю! Видимо, у матрицы нет или бесконечное множество решений...");
                return b;
            }

            for(int k = i+1; k<n; k++){

                double c = a[k][i] / a[i][i];
                a[k][i] = 0;

                for(int j =i+1;j<n;j++){
                    a[k][j] = a[k][j] - c * a[i][j];
                }

                b[k] =b[k] - c * b[i];

            }

        }
        determinant = ms.triangleDeterminantFinder(a, pereCount, n); // Нахождение определителя

        if(determinant == 0){
            System.out.println("Определитель равен нулю! Видимо, у матрицы нет или бесконечное множество решений...");
            return b;
        }

        System.out.println("Ваша матрица в треугольном виде:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%15s", df.format(a[i][j]));
            }
            System.out.printf("%15s"," | ");
            System.out.printf("%2s %n",df.format(b[i]));
        }

        /*------Обратный ход:---------*/

        double[] x = new double[n];
        x = ms.getResultsFromTriangleMatrix(x, a, b, n);


        System.out.println("Определитель вашей матрицы:");
        System.out.printf("%7s %n", df.format(determinant));


        System.out.println("Программа получила решения:");
        int z = 0;
        for (int i = 0; i < n; i++) {
            z++;
            System.out.printf("%7s", "X" + z + " = " + x[i] + " ");

        }

        return x;
    }
}
