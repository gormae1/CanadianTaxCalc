package canadiantaxcalculator;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

class Host {
    //TODO Abstract class
    private ContentHub contentHub = new ContentHub();
    private PanelChooser panelChooser = new PanelChooser();
    private JPanel sideButtonPanel = panelChooser.getSideButtonPanel();

    private final MouseAdapter panelSwitcher = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            String name = ((JPanel) e.getSource()).getName();
            panelChooser.setSelectedButton(name);
            CardLayout cardLayout = (CardLayout) contentHub.getMainContentPanel().getLayout();
            cardLayout.show(contentHub.getMainContentPanel(), name);
        }
    };

    private JPanel hostingPanel = new JPanel();
    private GroupLayout hostingPanelLayout = new GroupLayout(hostingPanel);

    private void initComponents() {
        panelChooser.setMouseListener(panelSwitcher);
        initLayout();
        hostingPanel.setLayout(hostingPanelLayout);
        hostingPanel.setBackground(Color.LIGHT_GRAY);
        hostingPanel.setBorder(null);
        hostingPanel.setSize(new Dimension(882, 724));
        hostingPanel.setMinimumSize(new Dimension(882, 724));
    }

    JPanel getHostingPanel() {
        return hostingPanel;
    }

    private void initLayout() {
        hostingPanelLayout.setHorizontalGroup(
                hostingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(hostingPanelLayout.createSequentialGroup()
                                .addComponent(sideButtonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(hostingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(contentHub.getMainContentPanel(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(hostingPanelLayout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                //.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))))
                                        ))));

        hostingPanelLayout.setVerticalGroup(
                hostingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, hostingPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(hostingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(sideButtonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(hostingPanelLayout.createSequentialGroup()
                                                //  .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(contentHub.getMainContentPanel(), GroupLayout.PREFERRED_SIZE, 686, GroupLayout.PREFERRED_SIZE))))
        );
    }

    Host() {
        initComponents();
    }

}
