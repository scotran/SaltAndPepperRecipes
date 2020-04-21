import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPanel extends JPanel implements ActionListener {
    private JLabel panelLabel, titleLabel, introductionLabel, ingredientsLabel, directionsLabel;
    private JTextField titleField;
    private JTextArea introductionArea, ingredientsArea, directionsArea;
    private JButton confirmButton, cancelButton;

    private GridBagConstraints gc = new GridBagConstraints();

    private EditRecipeListener buttonListener;

    public EditPanel() {
        setLayout(new GridBagLayout());

        panelLabel = new JLabel("Edit Recipe");
        titleLabel = new JLabel("Title: ");
        introductionLabel = new JLabel("Introduction: ");
        ingredientsLabel = new JLabel("Ingredients: ");
        directionsLabel = new JLabel("Directions: ");

        titleField = new JTextField();

        introductionArea = new JTextArea();
        ingredientsArea = new JTextArea();
        directionsArea = new JTextArea();

        confirmButton = new JButton("Submit Changes");
        cancelButton = new JButton("Cancel");

        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);

        ////////////GUI

        gc.insets = new Insets(0, 40, 20, 40);

        //Add Panel Label
        gc.weightx = 2;
        gc.weighty = 2;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 5;

        add(panelLabel, gc);
        //Column 1
        gc.gridwidth = 1; //reset gridwidth to default

        gc.weightx = 1;
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.LINE_END;

        gc.gridx = 0;
        gc.gridy = 1;
        add(titleLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        add(introductionLabel, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        add(ingredientsLabel, gc);

        gc.gridx = 0;
        gc.gridy = 4;
        add(directionsLabel, gc);

        //Column 2
        gc.anchor = GridBagConstraints.LINE_START;
        gc.weightx = 20;
        gc.weighty = 2;
        gc.fill = GridBagConstraints.BOTH;
        //gc.gridwidth = 4;

        gc.gridx = 1;
        gc.gridy = 1;
        add(titleField, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(new JScrollPane(introductionArea), gc);

        gc.gridx = 1;
        gc.gridy = 3;
        add(new JScrollPane(ingredientsArea), gc);

        gc.gridx = 1;
        gc.gridy = 4;
        add(new JScrollPane(directionsArea), gc);

        //Buttons
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 5;
        gc.weighty = 5;
        gc.fill = GridBagConstraints.BOTH;

        gc.gridx = 0;
        gc.gridy = 5;
        add(cancelButton, gc);

        gc.gridx = 1;
        gc.gridy = 5;
        add(confirmButton, gc);
    }

    public void setButtonListener(EditRecipeListener buttonListener) { this.buttonListener = buttonListener; }

    public void editPanel(Recipe r) {
        titleField.setText(r.getTitle());
        introductionArea.setText(r.getIntroduction());
        ingredientsArea.setText(r.getIngredientsForEdit());
        directionsArea.setText(r.getDirections());
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();
        if(clicked == confirmButton) {
            if(buttonListener != null) {
                String title = titleField.getText();
                String intro = introductionArea.getText();
                String ingredients = ingredientsArea.getText();
                String directions = directionsArea.getText();

                buttonListener.editRecipe(true, title, intro, ingredients, directions);
            }
        }
        else if(clicked == cancelButton) {
            if(buttonListener != null) {
                buttonListener.editRecipe(false, null, null, null, null);
            }
        }
    }
}
