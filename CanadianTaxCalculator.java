package canadiantaxcalculator;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

class CanadianTaxCalculator {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
           //  UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CanadianTaxCalculator.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Host mainHost = new Host();
        JFrame mainFrame = new JFrame("Tax Calculator & Tools");

        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainFrame.getContentPane());
        mainFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainHost.getHostingPanel(), javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainHost.getHostingPanel(), javax.swing.GroupLayout.PREFERRED_SIZE, 350, Short.MAX_VALUE)
        );
        mainFrame.pack();


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            mainFrame.setVisible(true);
        });
    }
}
