import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Toolbar extends JPanel implements ActionListener {
    private JButton viewButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton sortButton;
    private JButton scaleButton;
    private JButton helpButton;

    private JLabel panelLabel;
    private JLabel actionLabel;

    private ToolbarListener buttonListener;

    public Toolbar() {
        viewButton = new JButton("View/Display Recipes");
        addButton = new JButton("Add Recipes");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        sortButton = new JButton("Sort/Search Recipes");
        scaleButton = new JButton("Scale");
        helpButton = new JButton("Help");

        panelLabel = new JLabel("Move to: ");
        actionLabel = new JLabel("Recipe Actions: ");

        viewButton.addActionListener(this);
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        sortButton.addActionListener(this);
        scaleButton.addActionListener(this);
        helpButton.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(panelLabel);
        add(viewButton);
        add(addButton);
        add(sortButton);

        add(actionLabel);
        add(editButton);
        add(deleteButton);
        add(scaleButton);
        add(helpButton);
    }

    public void setButtonListener(ToolbarListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if(clicked == viewButton) {
            if(buttonListener != null) {
                buttonListener.changePanel("viewPanel");
            }
        } else if(clicked == addButton) {
            if(buttonListener != null) {
                buttonListener.changePanel("addPanel");
            }
        } else if(clicked == editButton) {
            if(buttonListener != null) {
                buttonListener.changePanel("editPanel");
            }
        } else if(clicked == deleteButton) {
            if(buttonListener != null) {
                buttonListener.changePanel("delete");
            }
        } else if(clicked == scaleButton) {
            if(buttonListener != null) {
                buttonListener.changePanel("scalePanel");
            }
        } else if (clicked == sortButton) {
            if(buttonListener != null) {
                buttonListener.changePanel("sortPanel");
            }
        } else if (clicked == helpButton) {
            if (buttonListener != null) {
                buttonListener.changePanel("helpPanel");
            }
        }
    }
}