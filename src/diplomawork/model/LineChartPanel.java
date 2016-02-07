/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.timer.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Volodymyr
 */
public class LineChartPanel extends Thread {

    private TimeSeries timeSeries;
    String stock = "EURUSD=X";

    private String url;

    public TimeSeries getTimeSeries() {
        return timeSeries;
    }
    private ChartPanel chartPanel;

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public LineChartPanel(String name, String url) {
        this.url = url;
        this.stock = name;
        chartPanel = createChartPanel();
        this.start();
    }

    /**
     * Creates a chart.
     *
     * @param dataset a dataset.
     *
     * @return A chart.
     */
    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                stock, // title
                "Time", // x-axis label
                "Price", // y-axis label
                dataset, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.setOutlinePaint(null);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setSeriesPaint(0, Color.BLUE);
        }
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd HH:mm:ss"));
        return chart;
    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private TimeSeriesCollection createDataset() {
        timeSeries = new TimeSeries(stock);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(timeSeries);
        return dataset;
    }

    /**
     * Creates a panel.
     *
     * @return A panel.
     */
    private ChartPanel createChartPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);

        return panel;
    }

    /**
     * Periodly conecting for server and check a data if it was change than
     * update a chart view for panel
     */
    @Override
    public void run() {
        Double price = new Double(-1);
        while (this != null) {
//          Float price = (GetDataFormYahoo.getPriceValue(url) != null) ? GetDataFormYahoo.getPriceValue().floatValue() : 1;
            Quote  quote = GetDataFormYahoo.getQouteFromYahoo(url);
            Double tmpPrice = quote.getLast();
            if (!tmpPrice.equals(price)) {
                price = tmpPrice;
                
                timeSeries.add(new Second(quote.getDate()), price);
            }

            synchronized (this) {
                try {
                    this.wait(Timer.ONE_SECOND * 5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LineChartPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}