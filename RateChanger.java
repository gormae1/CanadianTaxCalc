package canadiantaxcalculator;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.math.BigDecimal;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class RateChanger {
    private JPanel mainPanel = new JPanel();
    private GroupLayout generalLayout = new GroupLayout(mainPanel);

    private JPanel inputHeaderPanel = new JPanel();
    private GroupLayout inputHeaderPanelLayout = new GroupLayout(inputHeaderPanel);

    private SaveSystem rateSystem;

    private final DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setBackground(isSelected ? Color.LIGHT_GRAY : (((row % 2) == 0) ? CalculatorConstants.ALTERNATING_COLOR : Color.WHITE));
            c.setFont(column == 0 ? CalculatorConstants.TABLE_FIRST_COL_FONT : CalculatorConstants.TABLE_OTHER_FONT);
            return c;
        }
    };

    private JButton resetRates = new JButton("Reset");

    private JLabel resetRatesPrompt = new JLabel("Reset Rates:");
    private JLabel newRatePrompt = new JLabel("Value:");
    private JLabel ratePrompt = new JLabel("Item:");

    private JFormattedTextField newRateField = new JFormattedTextField();

    private JTable rateTable = new JTable();
    private JScrollPane rateTableScrollPane = new JScrollPane(rateTable);

    private JComboBox<String> ratesComboBox = new JComboBox<>();

    private void initLayouts() {
        inputHeaderPanel.setLayout(inputHeaderPanelLayout);
        inputHeaderPanelLayout.setHorizontalGroup(
                inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                .addGap(28)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(newRatePrompt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ratePrompt, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                                .addGap(55)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ratesComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(newRateField, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(resetRatesPrompt)
                                .addGap(18, 18, 18)
                                .addComponent(resetRates, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        inputHeaderPanelLayout.setVerticalGroup(
                inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, inputHeaderPanelLayout.createSequentialGroup()
                                .addGap(0, 19, Short.MAX_VALUE)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(ratePrompt)
                                                .addComponent(ratesComboBox)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
// .addGap(55)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(newRatePrompt)
                                        .addComponent(newRateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        //.addGap(50))
                        .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                .addGap(25)// resetbutton
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(resetRates, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(resetRatesPrompt))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.setLayout(generalLayout);
        generalLayout.setHorizontalGroup(
                generalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(inputHeaderPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(generalLayout.createSequentialGroup()
                                //.addGap(41, 41, 41)
                                .addComponent(rateTableScrollPane, GroupLayout.PREFERRED_SIZE, 705, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(46, Short.MAX_VALUE))
        );
        generalLayout.setVerticalGroup(
                generalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, generalLayout.createSequentialGroup()
                                .addComponent(inputHeaderPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED) //.addGap(18, 18, 18)
                                .addComponent(rateTableScrollPane, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(311, Short.MAX_VALUE))
        );
    }

    private void initListeners() {
        newRateField.addActionListener(actionEvent -> {
            try {
                BigDecimal newValue = new BigDecimal(newRateField.getText());
                rateSystem.setRate(ratesComboBox.getSelectedIndex(), newValue);
                rateTable.setValueAt(newValue, ratesComboBox.getSelectedIndex(), 1);
                rateSystem.saveRates();

                newRateField.setText(rateSystem.getRate(ratesComboBox.getSelectedIndex()).toString());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(mainPanel, "Invalid Input", CalculatorConstants.ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(mainPanel, "Unable to Save to File... Keeping Defaults", CalculatorConstants.ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
            }
        });

        resetRates.addActionListener(actionEvent -> {
            try {
                rateSystem.resetRates();

                for (int i = 0; i < rateTable.getRowCount(); i++) {
                    rateTable.setValueAt(rateSystem.getRate(i), i, 1);
                }
                newRateField.setText(rateSystem.getRate(ratesComboBox.getSelectedIndex()).toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(mainPanel, "Unable to Reset Rates... Keeping current values", CalculatorConstants.ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
            }
        });

        ratesComboBox.addActionListener(actionEvent -> newRateField.setText(rateSystem.getRate(ratesComboBox.getSelectedIndex()).toString()));
    }

    private void initComponents() {
        initLayouts();
        inputHeaderPanel.setBackground(CalculatorConstants.HEADER_COLOR);
        mainPanel.setBackground(Color.WHITE);

        ratePrompt.setFont(CalculatorConstants.PROMPT_FONT);
        ratePrompt.setForeground(Color.WHITE);

        newRatePrompt.setFont(CalculatorConstants.PROMPT_FONT);
        newRatePrompt.setForeground(Color.WHITE);

        resetRatesPrompt.setFont(CalculatorConstants.PROMPT_FONT);
        resetRatesPrompt.setForeground(Color.WHITE);

        newRateField.setColumns(10);

        ratesComboBox.setFont(CalculatorConstants.PROMPT_FONT.deriveFont(16f));
        ratesComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"GST", "British Columbia PST",
                "Alberta PST", "Saskatchewan PST", "Manitoba PST", "Ontario PST",
                "Quebec PST", "New Brunswick PST", "Nova Scotia PST", "P.E.I PST",
                "Newfoundland PST", "Yukon PST", "Northwest PST", "Nunavut PST",
                }));

        newRateField.setText(rateSystem.getGST().toString());

        rateTable.setDefaultRenderer(Object.class, cellRenderer);
        cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int x = 0; x < rateTable.getColumnCount(); x++) {
            rateTable.getColumnModel().getColumn(x).setCellRenderer(cellRenderer);
        }
        rateTable.setFont(CalculatorConstants.PROMPT_FONT.deriveFont(15f));
        rateTable.setModel(new DefaultTableModel(
                new Object[][]{
                        {"GST", null},
                        {"British Columbia PST", null},
                        {"Alberta PST", null},
                        {"Saskatchewan PST", null},
                        {"Manitoba PST", null},
                        {"Ontario PST", null},
                        {"Quebec PST", null},
                        {"NewBrunswick PST", null},
                        {"Nova Scotia PST", null},
                        {"Prince Edward PST", null},
                        {"Newfoundland PST", null},
                        {"Yukon PST", null},
                        {"Northwest PST", null},
                        {"Nunavut PST", null},
                }, new String[]{
                "Item", "Rate"
        }));

        rateTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //TODO disable editing (NOT NECESSARY)
        rateTable.getTableHeader().setReorderingAllowed(false);

        rateTableScrollPane.setViewportView(rateTable);
        rateTableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rateTableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        if (rateTable.getColumnModel().getColumnCount() > 0) {
            rateTable.getColumnModel().getColumn(0).setResizable(false);
            rateTable.getColumnModel().getColumn(1).setResizable(false);
        }

        for (int i = 0; i < rateTable.getRowCount(); i++) {
            rateTable.setValueAt(rateSystem.getRate(i), i, 1);
        }
        initListeners();
    }

    JPanel getMainPanel() {
        return mainPanel;
    }

    RateChanger(SaveSystem saveSystem) {
        rateSystem = saveSystem;
        initComponents();
    }
}