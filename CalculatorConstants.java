package canadiantaxcalculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class CalculatorConstants {
    static final Color HEADER_COLOR = new Color(50, 56, 56);
    static final Font PANEL_TITLE_FONT = new Font("Dialog", Font.PLAIN, 18);
    static final Font ENTRY_FIELD_PROMPT = new Font("DejaVu Sans", Font.PLAIN, 14);
    static final Font LABEL_FONT = new Font("DejaVu Sans", Font.PLAIN, 14);
    static final Font BUTTON_FONT = new Font("Serif", Font.PLAIN, 15);
    static final Font PROMPT_FONT = new Font("Ubuntu", Font.PLAIN, 18);
    static final String ERROR_DIALOG_TITLE = "Tax Calculator Error";

    static final Font TABLE_FIRST_COL_FONT = new Font("DejaVu Sans", Font.BOLD, 16);
    static final Font TABLE_OTHER_FONT = new Font("DejaVu Sans", Font.PLAIN, 16);
    static final Color ALTERNATING_COLOR = new Color(230, 230, 230);
    static final DefaultTableCellRenderer CELL_RENDERER = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setBackground(isSelected ? Color.LIGHT_GRAY : ((row == 4) ? Color.LIGHT_GRAY : (((row % 2) == 0) ? ALTERNATING_COLOR : Color.WHITE)));
            c.setFont(column == 0 ? TABLE_FIRST_COL_FONT : TABLE_OTHER_FONT);
            return c;
        }
    };

    private CalculatorConstants() {
        throw new IllegalStateException();
    }
}
