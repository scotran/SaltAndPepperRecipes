import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPanel extends JPanel implements ActionListener {
    private JLabel panelLabel, titleLabel, introductionLabel, ingredientsLabel, directionsLabel;
    private JTextField titleField;
    private JTextArea introductionArea, ingredientsArea, directionsArea;
    private JButton confirmButton, clearButton;

    private GridBagConstraints gc = new GridBagConstraints();

    private AddRecipeListener buttonListener;

    private final String TITLE_DEFAULT = "Enter the title/name of the recipe. This will be the name of the file. " +
            "Ex. Chocolate Chip Cookies";
    private final String INTRODUCTION_DEFAULT = "Enter a small introduction/description of the recipe.\n" +
            "Ex. A delightful recipe featuring delicious chocolate chip cookies.";
    private final String INGREDIENTS_DEFAULT = "Enter a list of ingredients used in the recipe-follow format. \n" +
            "Use a new line for each new ingredient and use a '--' to separate ingredient type and amounts.\n" +
            "Insert ingredient amount first followed by type. Ex.\n" +
            "3 Cups--Flour\n2--Eggs\n3 Cups--Sugar";
    private final String DIRECTIONS_DEFAULT = "Enter a list of directions for the recipe. Ex.\n" +
            "1. Mix together sugar, butter, and eggs until smooth\n2. Incorporate flour taking care not to over mix.";

    public AddPanel() {
        setLayout(new GridBagLayout());

        panelLabel = new JLabel("Add A Recipe");
        titleLabel = new JLabel("Title: ");
        introductionLabel = new JLabel("Introduction: ");
        ingredientsLabel = new JLabel("Ingredients: ");
        directionsLabel = new JLabel("Directions: ");

        titleField = new JTextField();

        introductionArea = new JTextArea();
        ingredientsArea = new JTextArea();
        directionsArea = new JTextArea();

        confirmButton = new JButton("Add Recipe");
        clearButton = new JButton("Clear");

        clearText();

        confirmButton.addActionListener(this);
        clearButton.addActionListener(this);

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
        add(clearButton, gc);

        gc.gridx = 1;
        gc.gridy = 5;
        add(confirmButton, gc);
    }

    public void setButtonListener(AddRecipeListener buttonListener) { this.buttonListener = buttonListener; }

    public void clearText() {
        titleField.setText(TITLE_DEFAULT);
        introductionArea.setText(INTRODUCTION_DEFAULT);
        ingredientsArea.setText(INGREDIENTS_DEFAULT);
        directionsArea.setText(DIRECTIONS_DEFAULT);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();
        if(clicked == confirmButton) {
            if(buttonListener != null) {
                String title = titleField.getText();
                String intro = introductionArea.getText();
                String ingredients = ingredientsArea.getText();
                String directions = directionsArea.getText();

                buttonListener.addRecipe(title, intro, ingredients, directions);

                clearText();
            }
        }
        else if(clicked == clearButton) {
            clearText();
        }
    }
}
