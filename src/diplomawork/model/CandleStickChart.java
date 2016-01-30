/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

/**
 *
 * @author Volodymyr
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;

import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class CandleStickChart extends ApplicationFrame {

    public CandleStickChart(String titel) {
        super(titel);

        final DefaultHighLowDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd HH:mm"));
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 350));
        setContentPane(chartPanel);
    }

    private DefaultHighLowDataset createDataset() {

        int serice = 200;

        Date[] date = new Date[serice];
        double[] high = new double[serice];
        double[] low = new double[serice];
        double[] open = new double[serice];
        double[] close = new double[serice];
        double[] volume = new double[serice];

        Calendar calendar = Calendar.getInstance();
        calendar.set(2008, 5, 1);

        for (int i = 0; i < serice; i++) {
            date[i] = createData(2008, 8, 30,12, i + 1);
            high[i] = 30 + Math.round(10) + new Double(Math.random() * 20.0);
            low[i] = 30 + Math.round(10) + new Double(Math.random() * 20.0);
            open[i] = 10 + Math.round(10) + new Double(Math.random() * 20.0);
            close[i] = 10 + Math.round(10) + new Double(Math.random() * 20.0);
            volume[i] = 10.0 + new Double(Math.random() * 20.0);
        }

        DefaultHighLowDataset data = new DefaultHighLowDataset(
                "", date, high, low, open, close, volume);
        return data;
    }

    private Date createData(int year, int month, int date, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1,date,hour, min);
        return calendar.getTime();
    }

    private JFreeChart createChart(final DefaultHighLowDataset dataset) {
        final JFreeChart chart = ChartFactory.createCandlestickChart(
                "Candlestick Demo", "Time", "Price", dataset, false);
        return chart;
    }

    public static void main(String args[]) {
        CandleStickChart chart = new CandleStickChart("Candle Stick Chart");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
       
        chart.setVisible(true);
    }
}
