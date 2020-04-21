import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ViewPanel extends JPanel implements ActionListener {

    private JTextArea textArea;
    private JButton leftButton;
    private JButton rightButton;

    private ViewRecipeListener buttonListener;

    public ViewPanel() {
        textArea = new JTextArea();
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");

        leftButton.addActionListener(this);
        rightButton.addActionListener(this);

        textArea.setEditable(false);

        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        add(leftButton, BorderLayout.LINE_START);
        add(rightButton, BorderLayout.LINE_END);
    }

    public void updateViewPanel(Recipe r) {
        textArea.setText(null);

        if(r != null) {
            textArea.append("----------------\n");
            textArea.append("Title");
            textArea.append("\n----------------\n");
            textArea.append(r.getTitle());
            textArea.append("\n\n----------------\n");
            textArea.append("Introduction");
            textArea.append("\n----------------\n");
            textArea.append(r.getIntroduction());
            textArea.append("\n----------------\n");
            textArea.append("Ingredients");
            textArea.append("\n----------------\n");
            textArea.append(r.getIngredients());
            textArea.append("\n----------------\n");
            textArea.append("Directions");
            textArea.append("\n----------------\n");
            textArea.append(r.getDirections());
        }
        else {
            textArea.append("There are no recipes to display.");
        }

        textArea.setCaretPosition(0);
    }

    public void setButtonListener(ViewRecipeListener buttonListener) { this.buttonListener = buttonListener; };

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if(clicked == leftButton) {
            if(buttonListener != null) {
                buttonListener.switchRecipe("left");
            }
        }
        else if(clicked == rightButton) {
            if(buttonListener != null) {
                buttonListener.switchRecipe("right");
            }
        }
    }

}