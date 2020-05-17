import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.function.Function;

public class GraphController {

    public void buildGraphForOneMethod(double[] arrX, double[] arrY, double[] funcValues, String title){

        /*------Построение графика-------*/
        XYSeries series = new XYSeries("Аппроксимирующая функция");
        XYSeries series1 = new XYSeries("Исходная функция");

        for(int i = 0; i < arrX.length; i++){
            series.add(arrX[i], funcValues[i]);
        }
        for(int i = 0; i < arrX.length; i++){
            series1.add(arrX[i], arrY[i]);
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(series);
        xyDataset.addSeries(series1);

        JFreeChart chart = ChartFactory
                .createXYLineChart(title, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("График функции");

        /*------Помещаем график на фрейм-------*/
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(400,300);
        frame.show();
    }

    public void buildGraphForAllMethods(double[] arrX, double[] arrY, double[] linFuncVal, double[] polFuncVal,
                                        double[] expoFuncVal, double[] logFuncVal, double[] powFuncVal){
        /*------Построение графика-------*/
        XYSeries linSeries = new XYSeries("Линейная функция");
        XYSeries polSeries = new XYSeries("Полиноминальная функция");
        XYSeries expoSeries = new XYSeries("Экспоненциальная функция");
        XYSeries logSeries = new XYSeries("Логарифмическая функция");
        XYSeries powSeries = new XYSeries("Степенная функция");
        XYSeries series = new XYSeries("Исходная функция");

        for(int i = 0; i < arrX.length; i++){
            series.add(arrX[i], arrY[i]);
        }
        for(int i = 0; i < arrX.length; i++){
            linSeries.add(arrX[i], linFuncVal[i]);
        }
        for(int i = 0; i < arrX.length; i++){
            polSeries.add(arrX[i], polFuncVal[i]);
        }
        for(int i = 0; i < arrX.length; i++){
            expoSeries.add(arrX[i], expoFuncVal[i]);
        }
        for(int i = 0; i < arrX.length; i++){
            logSeries.add(arrX[i], logFuncVal[i]);
        }
        for(int i = 0; i < arrX.length; i++){
            powSeries.add(arrX[i], powFuncVal[i]);
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();

        xyDataset.addSeries(series);
        xyDataset.addSeries(linSeries);
        xyDataset.addSeries(polSeries);
        xyDataset.addSeries(expoSeries);
        xyDataset.addSeries(logSeries);
        xyDataset.addSeries(powSeries);

        JFreeChart chart = ChartFactory
                .createXYLineChart("Аппроксимирующие функции", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("График функции");

        /*------Помещаем график на фрейм-------*/
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(400,300);
        frame.show();

    }
}
