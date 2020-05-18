import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TerminalHandler {

    private double[] arrX;
    private double[] arrY;


    public void start() throws InterruptedException {
        while (true) {
            Scanner scn = new Scanner(System.in);

            System.out.println("Выберите аппроксимирующую функцию:");
            Thread.sleep(1000);
            System.out.println("1. Линейная аппроксимация");
            System.out.println("2. Квадратичеая аппроксимация");
            System.out.println("3. Экспоненциальная аппроксимация");
            System.out.println("4. Логарифмическая аппроксимация");
            System.out.println("5. Степенная аппроксимация");
            System.out.println("6. Сравнить все функции");

            //Выбор метода аппроксимации
            switch (scn.nextInt()) {
                case 1:
                    inputMethod();
                    Approximations.linearApproximation(this.arrX, this.arrY);
                    System.out.println("\nХотите продолжить? Д/Н");
                    if(scn.next().equals("Д")){
                        continue;
                    }else{
                        System.exit(0);
                    }
                    break;

                case 2:
                    inputMethod();
                    Approximations.polynominalApproximation(this.arrX, this.arrY);
                    System.out.println("\nХотите продолжить? Д/Н");
                    if(scn.next().equals("Д")){
                        continue;
                    }else{
                        System.exit(0);
                    }
                    break;

                case 3:
                    inputMethod();
                    Approximations.exponentialApproximation(this.arrX, this.arrY);
                    System.out.println("\nХотите продолжить? Д/Н");
                    if(scn.next().equals("Д")){
                        continue;
                    }else{
                        System.exit(0);
                    }
                    break;

                case 4:
                    inputMethod();
                    Approximations.logarithmicApproximation(this.arrX, this.arrY);
                    System.out.println("\nХотите продолжить? Д/Н");
                    if(scn.next().equals("Д")){
                        continue;
                    }else{
                        System.exit(0);
                    }
                    break;

                case 5:
                    inputMethod();
                    Approximations.powerApproximation(this.arrX, this.arrY);
                    System.out.println("\nХотите продолжить? Д/Н");
                    if(scn.next().equals("Д")){
                        continue;
                    }else{
                        System.exit(0);
                    }
                    break;

                case 6:
                    inputMethod();
                    Approximations.allApproxFunctions(this.arrX, this.arrY);
                    System.out.println("\nХотите продолжить? Д/Н");
                    if(scn.next().equals("Д")){
                        continue;
                    }else{
                        System.exit(0);
                    }
                    break;

                default:
                    System.err.println("Пожалуйста, выберите правильный номер метода");
            }
        }
    }

        private void inputMethod() throws InterruptedException {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Как вы хотите вводить данные?");
            Thread.sleep(1000);
            System.out.println("1. Вручную");
            System.out.println("2. Из файла");

            //Выбор ввода(из файла или вручную)
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("Введите кол-во значений:");
                    int lengthOfArr = scanner.nextInt();
                    this.arrX = new double[lengthOfArr];
                    this.arrY = new double[lengthOfArr];
                    System.out.println("Вводите X:");
                    for (int i = 0; i < lengthOfArr; i++) {
                        this.arrX[i] = scanner.nextDouble();
                    }
                    System.out.println("Вводите Y:");
                    for (int i = 0; i < lengthOfArr; i++) {
                        this.arrY[i] = scanner.nextDouble();
                    }
                    break;

                case 2:
                    System.out.println("Некоторые особенности чтения файлов:");
                    System.out.println("Файл должен содержать кол-во значений, все X, введённые через пробел и все Y, введённые через пробел.");
                    System.out.println("Все числа должны быть записаны через пробел.\n");
                    System.out.println("Поместите файл в папку src и введите название файла:");
                    String path = "/Users/andreyklyuev/Desktop/ВУЗ/4 семестр/Лабы/Вычислительная математика/Lab4VM/src/" + scanner.next();
                    try {
                        Scanner sc = new Scanner(new File(path));
                        int lengthOfARR = sc.nextInt();
                        this.arrY = new double[lengthOfARR];
                        this.arrX = new double[lengthOfARR];

                        for (int i = 0; i < lengthOfARR; i++) {
                            this.arrX[i] = sc.nextDouble();
                        }
                        for (int i = 0; i < lengthOfARR; i++) {
                            this.arrY[i] = sc.nextDouble();
                        }

                    } catch (FileNotFoundException e) {
                        System.err.println("Файл не найден!");
                    }
                    break;

                default:
                    System.err.println("Введите либо 1, либо 2 :)");
            }

        }
}

