import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SortPanel extends JPanel implements ActionListener {
    private JLabel panelLabel, typeLabel, amountLabel;
    private JComboBox<String> typeBox;
    private JTextField nameField;
    private JTextArea displayArea;
    private JButton clearButton, confirmButton;

    private GridBagConstraints gc = new GridBagConstraints();

    private SortRecipeListener buttonListener;

    private ArrayList<Recipe> recipeArr;

    public SortPanel() {
        setLayout(new GridBagLayout());

        panelLabel = new JLabel("Sort Recipes");
        typeLabel = new JLabel("Sort by ingredient or by name?");
        amountLabel = new JLabel("Enter name of ingredient or recipe (case sensitive): ");

        typeBox = new JComboBox<String>();

        typeBox.addItem("Ingredient");
        typeBox.addItem("Recipe");

        nameField = new JTextField(20);

        displayArea = new JTextArea();
        clearButton = new JButton("Clear");
        confirmButton = new JButton("Sort/Search");

        displayArea.setEditable(false);
        clearButton.addActionListener(this);
        confirmButton.addActionListener(this);
        ////////GUI

        gc.insets = new Insets(0, 20, 20, 40);

        //Panel Label
        gc.weightx = 2;
        gc.weighty = 1;
        gc.gridwidth = 5;
        gc.gridx = 0;
        gc.gridy = 0;

        add(panelLabel, gc);

        //Column 1
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;

        add(typeLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;

        add(amountLabel, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.fill = GridBagConstraints.BOTH;

        add(clearButton, gc);

        //Column 2
        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridwidth = 4;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;

        add(typeBox, gc);

        gc.gridx = 1;
        gc.gridy = 2;

        add(nameField, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.fill = GridBagConstraints.BOTH;

        add(confirmButton, gc);

        //Display
        gc.weightx = 2;
        gc.weighty = 5;
        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 5;
        gc.fill = GridBagConstraints.BOTH;

        add(new JScrollPane(displayArea), gc);
    }

    public void setButtonListener(SortRecipeListener buttonListener) { this.buttonListener = buttonListener; }

    public void updateRecipeArr(ArrayList<Recipe> recipeArr) { this.recipeArr = (ArrayList<Recipe>)recipeArr.clone(); }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if(clicked == confirmButton) {
            if(buttonListener != null) {
                buttonListener.sortRecipe();
                sortArray();
            }
        }

        if (clicked == clearButton) {
            if (buttonListener != null) {
                displayArea.setText(null);
            }
        }
    }

    public void sortArray() {
        String typeStr = typeBox.getSelectedItem().toString();
        String searchStr = nameField.getText();

        if(typeStr.equals("Ingredient")) {
            String noIngredient = "";

            displayArea.setText(null);
            for(Recipe r : recipeArr) {
                String amountStr = r.amountOfIngredient(searchStr);
                if(r.amountOfIngredient(searchStr) != null) {
                    displayArea.append("\"" + r.getTitle() + "\" requires " + amountStr + " " + searchStr + "\n");
                }
                else
                    noIngredient += "\"" + r.getTitle() + "\" does not require " + searchStr + "\n";
            }

            displayArea.append(noIngredient);


        } else if (typeStr.equals("Recipe")) {
            boolean swapped;
            int n = recipeArr.size();

            for(int i = 0; i<n-1; i++) {
                swapped = false;
                for(int j = 0; j<n-i-1; j++) {
                    String firstTitle = recipeArr.get(j).getTitle();
                    String secondTitle = recipeArr.get(j+1).getTitle();

                    if(Math.abs(searchStr.compareTo(firstTitle)) > Math.abs(searchStr.compareTo(secondTitle))) {
                        Recipe temp = recipeArr.get(j);
                        recipeArr.set(j, recipeArr.get(j+1));
                        recipeArr.set(j+1, temp);
                        swapped = true;
                    }
                }

                if(swapped == false) {
                    break;
                }
            }

            displayArea.setText(null);
            for(Recipe r : recipeArr) {
                displayArea.append(r.getTitle() + "\n");
            }

        }
    }
}
