package canadiantaxcalculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;

class PanelChooser {
    private final Color sidePanelColor = new Color(128, 128, 128);
    private final Color unselectedColor = new Color(100, 100, 100);

    private JPanel sideButtonPanel = new JPanel();
    private GroupLayout sidePanelLayout = new GroupLayout(sideButtonPanel);

    private JLabel frameName = new JLabel("Tax Calc and Tools");
    private JSeparator frameNameUnderline = new JSeparator();

    private JPanel[] buttonPanels = new JPanel[2];
    private JLabel[] buttonLabels = new JLabel[2];
    private GroupLayout[] buttonLayouts = new GroupLayout[2];


    private void initComponents() {
        for (int i = 0; i < buttonLabels.length; i++) {
            buttonPanels[i] = new JPanel();
            buttonPanels[i].setBackground(unselectedColor);
            buttonLayouts[i] = new GroupLayout(buttonPanels[i]);
            buttonLabels[i] = new JLabel();
            buttonLabels[i].setText(i == 0 ? "Calculate Taxes" : "Change Rates");
            buttonPanels[i].setName(Integer.toString(i + 1));
            buttonLabels[i].setFont(CalculatorConstants.BUTTON_FONT);
            buttonLabels[i].setForeground(Color.WHITE);
        }
        initLayouts();
        buttonPanels[0].setBackground(Color.WHITE);
        buttonLabels[0].setForeground(Color.BLACK);
        sideButtonPanel.setLayout(sidePanelLayout);
        sideButtonPanel.setBackground(sidePanelColor);

        frameName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        frameName.setForeground(Color.WHITE);


    }

    void setSelectedButton(String name) {
        int panelNumber = Integer.parseInt(name);
        for (int i = 0; i < buttonPanels.length; i++) {
            if (i + 1 != panelNumber) {
                buttonLabels[i].setForeground(Color.WHITE);
                buttonPanels[i].setBackground(unselectedColor);
            } else {
                buttonPanels[i].setBackground(Color.WHITE);
                buttonLabels[i].setForeground(Color.BLACK);
            }
        }
    }

    void setMouseListener(final MouseListener mouseListener) {
        for (JPanel buttonPanel : buttonPanels) {
            buttonPanel.addMouseListener(mouseListener);
        }
    }

    private void initLayouts() {
        buttonPanels[0].setLayout(buttonLayouts[0]);
        buttonLayouts[0].setHorizontalGroup(
                buttonLayouts[0].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(buttonLayouts[0].createSequentialGroup()
                                .addContainerGap()
                                .addComponent(buttonLabels[0])
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonLayouts[0].setVerticalGroup(
                buttonLayouts[0].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonLabels[0], GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        buttonPanels[1].setLayout(buttonLayouts[1]);
        buttonLayouts[1].setHorizontalGroup(
                buttonLayouts[1].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(buttonLayouts[1].createSequentialGroup()
                                .addContainerGap()
                                .addComponent(buttonLabels[1])
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonLayouts[1].setVerticalGroup(
                buttonLayouts[1].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonLabels[1], GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );
/*
        buttonPanels[2].setLayout(buttonLayouts[2]);
        buttonLayouts[2].setHorizontalGroup(
                buttonLayouts[2].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(buttonLayouts[2].createSequentialGroup()
                                .addContainerGap()
                                .addComponent(buttonLabels[2])
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonLayouts[2].setVerticalGroup(
                buttonLayouts[2].createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonLabels[2], GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

 */

        sidePanelLayout.setHorizontalGroup(
                sidePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonPanels[0], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonPanels[1], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        //.addComponent(buttonPanels[2], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(sidePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(sidePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(frameNameUnderline)
                                        .addGroup(sidePanelLayout.createSequentialGroup()
                                                .addComponent(frameName)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap()));

        sidePanelLayout.setVerticalGroup(
                sidePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(sidePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(frameName)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(frameNameUnderline, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(buttonPanels[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(buttonPanels[1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                              //  .addGap(0, 0, 0)
                              //  .addComponent(buttonPanels[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(Short.MAX_VALUE, Short.MAX_VALUE)
                        ));
    }

    JPanel getSideButtonPanel() {
        return sideButtonPanel;
    }

    PanelChooser() {
        initComponents();
    }
}
