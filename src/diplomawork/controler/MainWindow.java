/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.controler;

import diplomawork.model.CandleStickChartPanel;
import diplomawork.model.GetDataFormYahoo;
import diplomawork.model.JPEGSaver;
import diplomawork.model.NNRecognizer;
import diplomawork.model.Quote;
import diplomawork.model.ViewForDiagram;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

/**
 *
 * @author Volodymyr
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    private final String stock = "EURUSD=X";
//    private final String stock2 = "BZK16.NYM";
    String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20csv%20where%20url%3D%27download.finance.yahoo.com%2Fd%2Fquotes.csv%3Fs%3D"+stock+"%26f%3Dnd1t1l1ohgc1p2wv%27&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    private final String name = GetDataFormYahoo.getQouteFromYahoo(url).getName();
    ViewForDiagram viewForDiagram = new ViewForDiagram(name, url);
    CandleStickChartPanel candleStickChartPanel = new CandleStickChartPanel(name, url);
    NNRecognizer nNRecognizer = new NNRecognizer();
    JPEGSaver jPEGSaver = new JPEGSaver(viewForDiagram.getTimeSeries(),nNRecognizer);

    public MainWindow() {
        initComponents();
        viewForDiagram.getTimeSeries().addChangeListener(jPEGSaver);
        JPanel jPanel = new JPanel(new GridLayout(2, 1));
       
        jPanel.add(viewForDiagram.getChartPanel());
        jPanel.add(candleStickChartPanel.getChartPanel()); 
        jInternalFrame1.setContentPane(jPanel);
//        jInternalFrame2.setContentPane(viewForDiagram2.getChartPanel());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jInternalFrame1.setMaximumSize(new java.awt.Dimension(1000, 800));
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(1000, 800));
        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 522, Short.MAX_VALUE))
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFreeChart chart = null;
        try {
            Plot plot =  (Plot) viewForDiagram.getChartPanel().getChart().getPlot().clone();
            chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }    
        jPEGSaver.saveChartToFile(chart, true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JInternalFrame jInternalFrame1;
    // End of variables declaration//GEN-END:variables
}
