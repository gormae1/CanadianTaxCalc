package canadiantaxcalculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

class TaxCalculator {
    private JPanel mainPanel = new JPanel();
    private GroupLayout generalLayout = new GroupLayout(mainPanel);
    private JPanel inputHeaderPanel = new JPanel();
    private GroupLayout inputHeaderPanelLayout = new GroupLayout(inputHeaderPanel);
    private SaveSystem rateSystem;

    private JLabel operationPrompt = new JLabel("Operation:");
    private JLabel entryPrompt = new JLabel("Amount:   ");

    private JTable taxTable = new JTable();
    private JScrollPane taxTableScrollpane = new JScrollPane(taxTable);

    private JFormattedTextField amountEntry = new JFormattedTextField("");

    private JRadioButton addTaxButton = new JRadioButton("ADD Taxes");
    private JRadioButton removeTaxButton = new JRadioButton("REMOVE Taxes");
    private ButtonGroup addRemoveGroup = new ButtonGroup();

    private void initListeners() {
        amountEntry.addActionListener(actionEvent -> {
            try {
                BigDecimal enteredAmount = new BigDecimal(amountEntry.getText());
                BigDecimal result;
                boolean noRound = false;
                if (addRemoveGroup.getSelection().getActionCommand().equals("add")) {
                    for (int i = 1; i < taxTable.getColumnCount(); i++) {
                        for (int j = 1; j < taxTable.getRowCount() + 1; j++) {

                            switch (i) {
                                case 1:
                                    result = enteredAmount;
                                    break;
                                case 2:
                                    result = enteredAmount.multiply(rateSystem.getGST());
                                    break;
                                case 3:
                                    result = enteredAmount.multiply(rateSystem.getGST()).add(enteredAmount);
                                    break;
                                case 4:
                                    result = enteredAmount.multiply(rateSystem.getRate(j));
                                    break;
                                case 5:
                                    result = enteredAmount.multiply(rateSystem.getRate(j).add(rateSystem.getGST()));
                                    break;
                                case 6:
                                    noRound = (j - 1) == 5;
                                    result = rateSystem.getGST().add(rateSystem.getRate(j));
                                    break;
                                case 7:
                                    result = enteredAmount.multiply(rateSystem.getRate(j))
                                            .add(enteredAmount.multiply(rateSystem.getGST())
                                                    .add(enteredAmount));
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + i);
                            }
                            if (!noRound) {
                                taxTable.getModel().setValueAt(result.setScale(2, RoundingMode.HALF_UP), j - 1, i);
                            } else {
                                taxTable.getModel().setValueAt(result, j - 1, i);
                                noRound = false;
                            }
                        }
                    }
                } else {
                    for (int i = 1; i < taxTable.getColumnCount(); i++) {
                        for (int j = 1; j < taxTable.getRowCount() + 1; j++) {
                            switch (i) {
                                case 1:
                                    result = enteredAmount;
                                    break;
                                case 2:
                                    result = enteredAmount.divide((rateSystem.getGST()
                                            .add(rateSystem.getRate(j))).add(BigDecimal.ONE), 2, RoundingMode.HALF_UP)
                                            .multiply(rateSystem.getGST());
                                    break;
                                case 3:
                                    result = enteredAmount.divide((rateSystem.getGST()
                                            .add(rateSystem.getRate(j)))
                                            .add(BigDecimal.ONE), 2, RoundingMode.HALF_UP)
                                            .add(enteredAmount.divide((rateSystem.getGST()
                                                    .add(rateSystem.getRate(j)))
                                                    .add(BigDecimal.ONE), 2, RoundingMode.HALF_UP)
                                                    .multiply(rateSystem.getGST()));
                                    break;
                                case 4:
                                    result = enteredAmount.divide((rateSystem.getGST()
                                            .add(rateSystem.getRate(j))).add(BigDecimal.ONE), 2, RoundingMode.HALF_UP)
                                            .multiply(rateSystem.getRate(j));
                                    break;
                                case 5:
                                    result = enteredAmount.divide((rateSystem.getGST()
                                            .add(rateSystem.getRate(j)))
                                            .add(BigDecimal.ONE), 2, RoundingMode.HALF_UP)
                                            .multiply(rateSystem.getGST())
                                            .add(enteredAmount.divide((rateSystem.getGST()
                                                    .add(rateSystem.getRate(j)))
                                                    .add(BigDecimal.ONE), 2, RoundingMode.HALF_UP)
                                                    .multiply(rateSystem.getRate(j)));
                                    break;
                                case 6:
                                    noRound = (j - 1) == 5;
                                    result = rateSystem.getGST().add(rateSystem.getRate(j));
                                    break;
                                case 7:
                                    result = enteredAmount.divide((rateSystem.getGST().add(rateSystem.getRate(j))).add(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + i);
                            }
                            if (!noRound) {
                                taxTable.getModel().setValueAt(result.setScale(2, RoundingMode.HALF_UP), j - 1, i);
                            } else {
                                taxTable.getModel().setValueAt(result, j - 1, i);
                                noRound = false;
                            }
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(mainPanel, "Invalid Input", CalculatorConstants.ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalStateException ise) {
                JOptionPane.showMessageDialog(mainPanel, "Unexpected Internal Error", CalculatorConstants.ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initLayouts() {
        inputHeaderPanel.setLayout(inputHeaderPanelLayout);
        inputHeaderPanelLayout.setHorizontalGroup(
                inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                .addGap(28)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(operationPrompt)
                                        .addComponent(entryPrompt, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
                                .addGap(55)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                                .addComponent(addTaxButton)
                                                .addGap(18)
                                                .addComponent(removeTaxButton))
                                        .addComponent(amountEntry, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        inputHeaderPanelLayout.setVerticalGroup(
                inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, inputHeaderPanelLayout.createSequentialGroup()
                                .addGap(0, 19, Short.MAX_VALUE)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(addTaxButton)
                                                .addComponent(removeTaxButton))
                                        .addComponent(operationPrompt, GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(entryPrompt)
                                        .addComponent(amountEntry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        generalLayout.setHorizontalGroup(
                generalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(inputHeaderPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(taxTableScrollpane, GroupLayout.PREFERRED_SIZE, 705, GroupLayout.PREFERRED_SIZE)
        );
        generalLayout.setVerticalGroup(
                generalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, generalLayout.createSequentialGroup()
                                .addComponent(inputHeaderPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(taxTableScrollpane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(311, Short.MAX_VALUE))
        );
    }

    JPanel getMainPanel() {
        return mainPanel;
    }

    private void initComponents() {
        inputHeaderPanel.setBackground(CalculatorConstants.HEADER_COLOR);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(null);
        initLayouts();
        mainPanel.setLayout(generalLayout);

        initListeners();

        addTaxButton.setActionCommand("add");
        removeTaxButton.setActionCommand("remove");
        addTaxButton.setForeground(Color.WHITE);
        removeTaxButton.setForeground(Color.WHITE);

        addRemoveGroup.add(addTaxButton);
        addRemoveGroup.add(removeTaxButton);
        addRemoveGroup.setSelected(addTaxButton.getModel(), true);

        operationPrompt.setFont(new Font("Ubuntu", Font.PLAIN, 18));
        operationPrompt.setForeground(Color.WHITE);
        entryPrompt.setFont(new Font("Ubuntu", Font.PLAIN, 18));
        entryPrompt.setForeground(Color.WHITE);

        taxTableScrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        taxTableScrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        taxTableScrollpane.setViewportBorder(null);
        taxTableScrollpane.setPreferredSize(new Dimension(266, 266));
        taxTableScrollpane.setVerifyInputWhenFocusTarget(false);
        taxTableScrollpane.setViewportView(taxTable);

        taxTable.setDefaultRenderer(Object.class, CalculatorConstants.CELL_RENDERER);
        CalculatorConstants.CELL_RENDERER.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < taxTable.getColumnCount(); i++) {
            taxTable.getColumnModel().getColumn(i).setCellRenderer(CalculatorConstants.CELL_RENDERER);
        }

        taxTable.setBorder(null);
        taxTable.setModel(new DefaultTableModel(new Object[][]{
                {"BC", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"AB", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"SK", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"MB", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"ON", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"QC", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"NL", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"NB", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"NS", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"PE", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"YT", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"NT", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {"NU", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
        },
                new String[]{
                        "Province", "Amount", "GST", "GST+Amt", "PST", "GST + PST", "Rate", "Result"
                }));

        taxTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        taxTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taxTable.setShowGrid(true);
        taxTable.getTableHeader().setReorderingAllowed(false);
        taxTable.setPreferredScrollableViewportSize(taxTable.getPreferredSize());
        taxTableScrollpane.setViewportView(taxTable);

        if (taxTable.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i < taxTable.getColumnCount(); i++) {
                taxTable.getColumnModel().getColumn(i).setResizable(false);
            }
        }
    }

    TaxCalculator(final SaveSystem saveSystem) {
        rateSystem = saveSystem;
        initComponents();
    }
}
