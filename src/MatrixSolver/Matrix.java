package MatrixSolver;

public class Matrix {

    private double[] b;
    private double[][] a;
    private int n;
    private int m;

    public Matrix(double[][] a, double[] b, int n, int m){
        this.a = a;
        this.b = b;
        this.n = n;
        this.m = m;
    }

    public double[] getB() {
        return b;
    }

    public void setB(double[] b) {
        this.b = b;
    }

    public double[][] getA() {
        return a;
    }

    public void setA(double[][] a) {
        this.a = a;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }


}
