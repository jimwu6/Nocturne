import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.*;
import org.jfree.chart.ChartFactory.*;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Graph {

    public XYSeries series;
    public JFreeChart lineChart;
    public ChartPanel cp;
    public XYDataset ds;
    XYSeriesCollection col;
    String axis;
    public int n = 10;


    public Graph(String axis) {
        this.axis = axis;
        ds = createDataset();
        lineChart = ChartFactory.createXYLineChart(axis+"-Axis", "Number", "Degrees", createDataset());
        lineChart.removeLegend();
        lineChart.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        lineChart.getPlot().setBackgroundPaint(Color.white);
        cp = new ChartPanel(lineChart);
        cp.setPreferredSize(new Dimension(500,500));
    }

    private XYDataset createDataset() {
        col = new XYSeriesCollection();

        series = new XYSeries("deg");

        for (int i = 0; i < n; i++) {
            series.add(i, 0);
        }

        col.addSeries(series);

        return col;
    }

    public void addValue(double x) {
        for (int i = 0; i < n-1; i++) {
            series.updateByIndex(i, series.getY(i+1));
        }
        series.updateByIndex(n-1, x);
    }



}
