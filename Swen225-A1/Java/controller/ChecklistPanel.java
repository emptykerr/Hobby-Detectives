package controller;

import javax.swing.*;
import java.awt.*;

public class ChecklistPanel extends JPanel {
    protected JCheckBox lucillaCheckBox;
    protected JCheckBox bertCheckBox;
    protected JCheckBox percyCheckBox;
    protected JCheckBox malinaCheckBox;

    protected JCheckBox knifeCheckBox;
    protected JCheckBox shovelCheckBox;
    protected JCheckBox broomCheckBox;
    protected JCheckBox ipadCheckBox;
    protected JCheckBox scissorsCheckBox;

    protected JCheckBox calamityCastleCheckBox;
    protected JCheckBox hauntedHouseCheckBox;
    protected JCheckBox manicManorCheckBox;
    protected JCheckBox visitationVillaCheckBox;
    protected JCheckBox perilPalaceCheckBox;

    public ChecklistPanel() {
        setLayout(new GridLayout(0, 1));

        // Create checkboxes for players
        lucillaCheckBox = new JCheckBox("Lucilla");
        bertCheckBox = new JCheckBox("Bert");
        percyCheckBox = new JCheckBox("Percy");
        malinaCheckBox = new JCheckBox("Malina");

        // Create checkboxes for weapons
        knifeCheckBox = new JCheckBox("Knife");
        shovelCheckBox = new JCheckBox("Shovel");
        broomCheckBox = new JCheckBox("Broom");
        ipadCheckBox = new JCheckBox("iPad");
        scissorsCheckBox = new JCheckBox("Scissors");

        // Create checkboxes for estates
        calamityCastleCheckBox = new JCheckBox("Calamity Castle");
        hauntedHouseCheckBox = new JCheckBox("Haunted House");
        manicManorCheckBox = new JCheckBox("Manic Manor");
        visitationVillaCheckBox = new JCheckBox("Visitation Villa");
        perilPalaceCheckBox = new JCheckBox("Peril Palace");

        // Add checkboxes to the panel
        add(createSection("Players", lucillaCheckBox, bertCheckBox, percyCheckBox, malinaCheckBox));
        add(createSection("Weapons", knifeCheckBox, shovelCheckBox, broomCheckBox, ipadCheckBox, scissorsCheckBox));
        add(createSection("Estates", calamityCastleCheckBox, hauntedHouseCheckBox, manicManorCheckBox, visitationVillaCheckBox, perilPalaceCheckBox));

    }

    /**
     * Creates a new section of checkboxes for the checklist panel
     * @param title the name of the section
     * @param checkboxes the JCheckBox objects to add
     * @return
     */
    protected JPanel createSection(String title, JCheckBox... checkboxes) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBorder(BorderFactory.createTitledBorder(title));

        for (JCheckBox checkbox : checkboxes) {
            sectionPanel.add(checkbox);
        }

        return sectionPanel;
    }
}