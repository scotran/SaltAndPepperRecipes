import javax.swing.*;
import java.awt.*;

public class HelpPanel extends JPanel {

    private JTextArea display;
    private final String displayStr =
            "Welcome to the 'Salt and Pepper' recipe program.\n"
            + "'Salt and Pepper' allows you to store and edit recipes.\n\n"
            + "There are 3 'panels' or 'pages' that you can navigate to.\n\n"
            + "View/Display Recipes\n"
            + "This page allows you to view the recipes that have been added to the 'Recipes' folder located "
            + "in the root program folder.\nClick the Left and Right buttons to scroll through recipes.\n\n"
            + "Add Recipes\n"
            + "This page allows you to add recipes to the 'Recipes' folder so the program will be able to "
            + "view the recipe.\nEach recipe is saved as a 'txt' file which can be viewed in the 'Recipes' folder.\n"
            + "Enter in parts of the recipe as dictated. Using '--' for the ingredients is important to be able to "
            + "search through recipes.\n\nSort/Search Recipes\n"
            + "This page allows you to sort/search the recipes by which recipes have a specific ingredient or "
            + "by looking for a recipe that has a specific name.\nNames are case-sensitive, and the display will "
            + "present a list of the recipes with the any recipes matching the name in beginning.\n\n"
            + "There are also several actions that can be done to recipes.\nThese actions can be done by going to "
            + "'View/Display Recipes' and clicking an action.\n\n"
            + "'Edit' allows you to edit the current recipe.\n"
            + "'Delete' deletes the current recipe.\n"
            + "'Scale' allows you to scale a recipe's ingredients. Be careful about what recipes you want to scale: "
            + "some recipes do not work after scaling linearly.";

    public HelpPanel() {
        setLayout(new BorderLayout());

        display = new JTextArea();

        add(new JScrollPane(display));

        display.setText(displayStr);

    }
}
