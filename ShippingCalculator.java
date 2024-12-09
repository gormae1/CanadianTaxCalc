package canadiantaxcalculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import static javax.swing.GroupLayout.Alignment;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;

//94:50

class ShippingCalculator {
    private JPanel mainPanel = new JPanel();
    private GroupLayout generalLayout = new GroupLayout(mainPanel);

    private JPanel firstShippingResult = new JPanel();
    private GroupLayout firstShippingResultLayout = new GroupLayout(firstShippingResult);

    private JPanel secondShippingResult = new JPanel();
    private GroupLayout secondShippingResultLayout = new GroupLayout(secondShippingResult);

    private JTextField firstShippingEntry = new JTextField();
    private JTextField secondShippingEntry = new JTextField();
    private JLabel firstShippingPrompt = new JLabel("First Ship Price:");
    private JLabel secondShippingPrompt = new JLabel("Second Ship Price:");

    private JLabel firstShippingTotalPricePrompt = new JLabel("Total Price:");
    private JLabel firstShippingTotalPriceFormulaPrompt = new JLabel("Formula:");
    private JLabel firstShippingTotalShippingPrompt = new JLabel("Total Shipping:");
    private JLabel firstShippingTotalShippingFormulaPrompt = new JLabel("Formula:");

    private JTextField firstShippingTotalPriceOutput = new JTextField();
    private JTextField firstShippingTotalPriceFormulaOutput = new JTextField();
    private JTextField firstShippingTotalShippingOutput = new JTextField();
    private JTextField firstShippingTotalShippingFormulaOutput = new JTextField();

    private JTextField secondShippingTotalPriceOutput = new JTextField();
    private JTextField secondShippingTotalPriceFormulaOutput = new JTextField();
    private JTextField secondShippingTotalShippingOutput = new JTextField();
    private JTextField secondShippingTotalShippingFormulaOutput = new JTextField();

    private JLabel secondShippingTotalPricePrompt = new JLabel("Total Price:");
    private JLabel secondShippingTotalPriceFormulaPrompt = new JLabel("Formula:");
    private JLabel secondShippingTotalShippingFormulaPrompt = new JLabel("Formula:");
    private JLabel secondShippingTotalShippingPrompt = new JLabel("Total Shipping:");

    private JLabel bookPrompt = new JLabel("Book:");

    private ButtonGroup bookButtonGroup = new ButtonGroup();

    private ActionListener bookButtonListener = actionEvent -> {
        if (actionEvent.getSource() instanceof JTextField) {
            if (actionEvent.getSource() == firstShippingEntry) {
                if ((bookButtonGroup.getSelection() != null) && !secondShippingEntry.getText().isEmpty()) {
                    shippingCalculations();
                }
            } else if (bookButtonGroup.getSelection() != null && !firstShippingEntry.getText().isEmpty()) {
                shippingCalculations();
            }
        } else if (!firstShippingEntry.getText().isEmpty() && !secondShippingEntry.getText().isEmpty()) {
            shippingCalculations();
        }
    };

    private void shippingCalculations() {
        try {
            BigDecimal shippingCost = new BigDecimal(firstShippingEntry.getText());
            BigDecimal shippingCostN = new BigDecimal(secondShippingEntry.getText());
            System.out.println(bookButtonGroup.getSelection().getActionCommand());
            System.out.println(rateSystem.getRate(Integer.parseInt(bookButtonGroup.getSelection().getActionCommand())));
            BigDecimal selectedBook = rateSystem.getRate(Integer.parseInt(bookButtonGroup.getSelection().getActionCommand()));

            BigDecimal gstResult;
            BigDecimal finalResult;

            gstResult = selectedBook.add(shippingCost).multiply(rateSystem.getGST()).setScale(2, RoundingMode.HALF_UP);
            finalResult = gstResult.add(selectedBook.add(shippingCost));

            firstShippingTotalPriceOutput.setText(finalResult.toString());
            firstShippingTotalPriceFormulaOutput.setText(selectedBook + "(book) + " + shippingCost + "(shipping) + "
                    + gstResult + "(GST) = " + finalResult);

            finalResult = gstResult.add(shippingCost);

            firstShippingTotalShippingOutput.setText(finalResult.toString());
            firstShippingTotalShippingFormulaOutput.setText(gstResult + "(GST) + " + shippingCost + "(shipping) = " + finalResult);

            gstResult = selectedBook.add(shippingCostN).multiply(rateSystem.getGST()).setScale(2, RoundingMode.HALF_UP);
            finalResult = gstResult.add(selectedBook.add(shippingCostN));

            secondShippingTotalPriceOutput.setText(finalResult.toString());
            secondShippingTotalPriceFormulaOutput.setText(selectedBook + "(book) + " + shippingCostN + "(shipping) + "
                    + gstResult + "(GST) = " + finalResult);

            finalResult = gstResult.add(shippingCostN);
            secondShippingTotalShippingOutput.setText(finalResult.toString());
            secondShippingTotalShippingFormulaOutput.setText(gstResult + "(GST) + " + shippingCostN + "(shipping) = " + finalResult);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(mainPanel, "Invalid Input", CalculatorConstants.ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);

        }
    }

    private Dimension buttonDimension = new Dimension(40, 43);
    private JToggleButton[] books = new JToggleButton[4];
    private JPanel inputHeaderPanel = new JPanel();
    private GroupLayout inputHeaderPanelLayout = new GroupLayout(inputHeaderPanel);
    private SaveSystem rateSystem;

    private void initComponents() {
        inputHeaderPanel.setBackground(CalculatorConstants.HEADER_COLOR);
        mainPanel.setBackground(Color.WHITE);

        firstShippingTotalPricePrompt.setFont(CalculatorConstants.LABEL_FONT);
        firstShippingTotalPriceFormulaPrompt.setFont(CalculatorConstants.LABEL_FONT);
        firstShippingTotalShippingPrompt.setFont(CalculatorConstants.LABEL_FONT);
        firstShippingTotalShippingFormulaPrompt.setFont(CalculatorConstants.LABEL_FONT);

        secondShippingTotalPricePrompt.setFont(CalculatorConstants.LABEL_FONT);
        secondShippingTotalPriceFormulaPrompt.setFont(CalculatorConstants.LABEL_FONT);
        secondShippingTotalShippingPrompt.setFont(CalculatorConstants.LABEL_FONT);
        secondShippingTotalShippingFormulaPrompt.setFont(CalculatorConstants.LABEL_FONT);


        firstShippingTotalPriceOutput.setFont(CalculatorConstants.ENTRY_FIELD_PROMPT);
        firstShippingTotalShippingFormulaOutput.setFont(CalculatorConstants.ENTRY_FIELD_PROMPT);
        firstShippingTotalShippingOutput.setFont(CalculatorConstants.ENTRY_FIELD_PROMPT);
        firstShippingTotalPriceFormulaOutput.setFont(CalculatorConstants.ENTRY_FIELD_PROMPT);

        secondShippingTotalPriceOutput.setFont(CalculatorConstants.ENTRY_FIELD_PROMPT);
        secondShippingTotalShippingFormulaOutput.setFont(CalculatorConstants.ENTRY_FIELD_PROMPT);
        secondShippingTotalShippingOutput.setFont(CalculatorConstants.ENTRY_FIELD_PROMPT);
        secondShippingTotalPriceFormulaOutput.setFont(CalculatorConstants.ENTRY_FIELD_PROMPT);

        firstShippingResult.setBackground(Color.WHITE);
        firstShippingResult.setFont(CalculatorConstants.PANEL_TITLE_FONT);
        firstShippingResult.setBorder(new TitledBorder(null, "First Shipping", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, CalculatorConstants.PANEL_TITLE_FONT));

        firstShippingEntry.addActionListener(bookButtonListener);
        secondShippingEntry.addActionListener(bookButtonListener);

        secondShippingResult.setBackground(Color.WHITE);
        secondShippingResult.setFont(CalculatorConstants.PANEL_TITLE_FONT);
        secondShippingResult.setBorder(new TitledBorder(null, "Second Shipping", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, CalculatorConstants.PANEL_TITLE_FONT));

        bookPrompt.setFont(CalculatorConstants.PROMPT_FONT);
        bookPrompt.setForeground(Color.WHITE);

        secondShippingPrompt.setFont(CalculatorConstants.PROMPT_FONT);
        secondShippingPrompt.setForeground(Color.WHITE);

        firstShippingPrompt.setFont(CalculatorConstants.PROMPT_FONT);
        firstShippingPrompt.setForeground(Color.WHITE);

        for (int i = 0; i < books.length; i++) {
            books[i] = new JToggleButton();
            books[i].getModel().setActionCommand(Integer.toString(i + 14));
            books[i].setSize(buttonDimension);
            bookButtonGroup.add(books[i]);
            books[i].addActionListener(bookButtonListener);
        }

        try {
            books[0].setIcon(new ImageIcon(this.getClass().getResource("body-moveable-1vol-bw.png")));
            books[1].setIcon(new ImageIcon(this.getClass().getResource("body-moveable-1vol-colour.png")));
            books[2].setIcon(new ImageIcon(this.getClass().getResource("body-moveable-3vol-bw.png")));
            books[3].setIcon(new ImageIcon(this.getClass().getResource("looking-at-ourselves-colour.jpg")));
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(mainPanel, "Unable to Load Images...", CalculatorConstants.ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
        }

        initLayouts();
    }

    private void initLayouts() {
        inputHeaderPanel.setLayout(inputHeaderPanelLayout);
        inputHeaderPanelLayout.setHorizontalGroup(
                inputHeaderPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                .addGap(28)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(Alignment.LEADING, false)
                                        .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                                .addComponent(firstShippingPrompt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(firstShippingEntry, PREFERRED_SIZE, 136, PREFERRED_SIZE))
                                        .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                                .addComponent(secondShippingPrompt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(secondShippingEntry, PREFERRED_SIZE, 136, PREFERRED_SIZE)))
                                .addGap(18)
                                .addComponent(bookPrompt)
                                .addGap(18, 18, 18)
                                .addComponent(books[0], PREFERRED_SIZE, 55, PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(books[1], PREFERRED_SIZE, 56, PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(books[2], PREFERRED_SIZE, 56, PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(books[3], PREFERRED_SIZE, 56, PREFERRED_SIZE)
                                .addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inputHeaderPanelLayout.setVerticalGroup(
                inputHeaderPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, inputHeaderPanelLayout.createSequentialGroup()
                                .addGap(0, 7, Short.MAX_VALUE)
                                .addGroup(inputHeaderPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(books[0], PREFERRED_SIZE, 66, PREFERRED_SIZE)
                                        .addComponent(books[1], PREFERRED_SIZE, 66, PREFERRED_SIZE)
                                        .addGroup(inputHeaderPanelLayout.createParallelGroup(Alignment.LEADING)
                                                .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                                        .addComponent(books[2], PREFERRED_SIZE, 66, PREFERRED_SIZE)
                                                        .addContainerGap())
                                                .addGroup(Alignment.TRAILING, inputHeaderPanelLayout.createSequentialGroup()
                                                        .addGap(11, 11, 11)
                                                        .addGroup(inputHeaderPanelLayout.createParallelGroup(Alignment.TRAILING)
                                                                .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                                                        .addComponent(bookPrompt)
                                                                        .addGap(21, 21, 21))
                                                                .addGroup(inputHeaderPanelLayout.createSequentialGroup()
                                                                        .addGroup(inputHeaderPanelLayout.createParallelGroup(Alignment.BASELINE)
                                                                                .addComponent(firstShippingPrompt)
                                                                                .addComponent(firstShippingEntry, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(inputHeaderPanelLayout.createParallelGroup(Alignment.BASELINE)
                                                                                .addComponent(secondShippingPrompt)
                                                                                .addComponent(secondShippingEntry, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))))
                                                        .addGap(7)))
                                        .addComponent(books[3], PREFERRED_SIZE, 66, PREFERRED_SIZE)))
        );


        firstShippingResult.setLayout(firstShippingResultLayout);
        firstShippingResultLayout.setHorizontalGroup(
                firstShippingResultLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(firstShippingResultLayout.createSequentialGroup()
                                .addGap(2)
                                .addComponent(firstShippingTotalPricePrompt)
                                .addGap(37)
                                .addComponent(firstShippingTotalPriceOutput, PREFERRED_SIZE, 86, PREFERRED_SIZE)
                                .addGap(10)
                                .addComponent(firstShippingTotalPriceFormulaPrompt)
                                .addGap(10)
                                .addComponent(firstShippingTotalPriceFormulaOutput, PREFERRED_SIZE, 385, PREFERRED_SIZE))
                        .addGroup(firstShippingResultLayout.createSequentialGroup()
                                .addGap(2)
                                .addComponent(firstShippingTotalShippingPrompt)
                                .addGap(12)
                                .addComponent(firstShippingTotalShippingOutput, 20, 84, 84)
                                .addGap(10)
                                .addComponent(firstShippingTotalShippingFormulaPrompt)
                                .addGap(10)
                                .addComponent(firstShippingTotalShippingFormulaOutput, PREFERRED_SIZE, 385, PREFERRED_SIZE))
        );
        firstShippingResultLayout.setVerticalGroup(
                firstShippingResultLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(firstShippingResultLayout.createSequentialGroup()
                                .addGroup(firstShippingResultLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(firstShippingResultLayout.createSequentialGroup()
                                                .addGap(0)
                                                .addComponent(firstShippingTotalPricePrompt))
                                        .addGroup(firstShippingResultLayout.createSequentialGroup()
                                                .addGap(0)
                                                .addComponent(firstShippingTotalPriceOutput, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                        .addGap(0)
                                        .addComponent(firstShippingTotalPriceFormulaPrompt)
                                        .addGroup(firstShippingResultLayout.createSequentialGroup()
                                                .addGap(0)
                                                .addComponent(firstShippingTotalPriceFormulaOutput, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)))
                                .addGap(20)
                                .addGroup(firstShippingResultLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(firstShippingTotalShippingPrompt)
                                        .addComponent(firstShippingTotalShippingOutput, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(firstShippingResultLayout.createSequentialGroup()
                                                .addGroup(firstShippingResultLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(firstShippingTotalShippingFormulaPrompt, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(firstShippingTotalShippingFormulaOutput, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)))))
        );

        secondShippingResult.setLayout(secondShippingResultLayout);
        secondShippingResultLayout.setHorizontalGroup(
                secondShippingResultLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(secondShippingResultLayout.createSequentialGroup()
                                .addGap(2)
                                .addComponent(secondShippingTotalPricePrompt)
                                .addGap(37)
                                .addComponent(secondShippingTotalPriceOutput, PREFERRED_SIZE, 86, PREFERRED_SIZE)
                                .addGap(10)
                                .addComponent(secondShippingTotalPriceFormulaPrompt)
                                .addGap(10)
                                .addComponent(secondShippingTotalPriceFormulaOutput, PREFERRED_SIZE, 385, PREFERRED_SIZE))
                        .addGroup(secondShippingResultLayout.createSequentialGroup()
                                .addGap(2)
                                .addComponent(secondShippingTotalShippingPrompt)
                                .addGap(12)
                                .addComponent(secondShippingTotalShippingOutput, 20, 84, 84)
                                .addGap(10)
                                .addComponent(secondShippingTotalShippingFormulaPrompt)
                                .addGap(10)
                                .addComponent(secondShippingTotalShippingFormulaOutput, PREFERRED_SIZE, 385, PREFERRED_SIZE))
        );
        secondShippingResultLayout.setVerticalGroup(
                secondShippingResultLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(secondShippingResultLayout.createSequentialGroup()
                                .addGroup(secondShippingResultLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(secondShippingResultLayout.createSequentialGroup()
                                                .addGap(0)
                                                .addComponent(secondShippingTotalPricePrompt))
                                        .addGroup(secondShippingResultLayout.createSequentialGroup()
                                                .addGap(0)
                                                .addComponent(secondShippingTotalPriceOutput, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                        .addComponent(secondShippingTotalPriceFormulaPrompt, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(secondShippingResultLayout.createSequentialGroup()
                                                .addGap(0)
                                                .addComponent(secondShippingTotalPriceFormulaOutput, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)))
                                .addGap(20)
                                .addGroup(secondShippingResultLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(secondShippingTotalShippingPrompt, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(secondShippingTotalShippingOutput, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(secondShippingResultLayout.createSequentialGroup()
                                                .addGap(0)
                                                .addGroup(secondShippingResultLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(secondShippingTotalShippingFormulaPrompt, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(secondShippingTotalShippingFormulaOutput, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)))))
        );
        mainPanel.setLayout(generalLayout);
        generalLayout.setHorizontalGroup(
                generalLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(inputHeaderPanel, DEFAULT_SIZE, 679, Short.MAX_VALUE)
                        .addGroup(generalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(generalLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(firstShippingResult, 200, 700, 700)
                                        .addComponent(secondShippingResult, 200, 700, 700))
                                .addContainerGap())
        );
        generalLayout.setVerticalGroup(
                generalLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, generalLayout.createSequentialGroup()
                                .addComponent(inputHeaderPanel, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(firstShippingResult, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(secondShippingResult, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(30, 30, 30))
        );
    }

    JPanel getMainPanel() {
        return mainPanel;
    }

    ShippingCalculator(SaveSystem rateSystem) {
        this.rateSystem = rateSystem;
        initComponents();
    }

}
