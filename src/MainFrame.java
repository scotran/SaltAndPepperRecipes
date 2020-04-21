import java.awt.BorderLayout;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;


public class MainFrame extends JFrame {
    private ViewPanel viewPanel;
    private AddPanel addPanel;
    private EditPanel editPanel;
    private ScalePanel scalePanel;
    private SortPanel sortPanel;
    private HelpPanel helpPanel;
    private Toolbar toolbar;
    private JPanel currentPanel;

    private ArrayList<Recipe> recipeArr = new ArrayList<Recipe>();
    private int recipePosition;

    private String recipesFilePath;

    public MainFrame() {
        super("Salt and Pepper Recipes");

        setLayout(new BorderLayout());

        //Instantiate panels
        toolbar = new Toolbar();
        viewPanel = new ViewPanel();
        addPanel = new AddPanel();
        editPanel = new EditPanel();
        scalePanel = new ScalePanel();
        sortPanel = new SortPanel();
        helpPanel = new HelpPanel();
        //Add panels to MainFrame
        add(viewPanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.PAGE_START);

        currentPanel = viewPanel; //Set the panel that is currently showing

        updateRecipeArr();

        recipePosition = 0;
        updateViewPanel();


        try {
            recipesFilePath = new File(App.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParentFile().getPath() + "/Recipes/";
            File dir = new File(recipesFilePath);
            dir.mkdir();

        } catch (Exception e) {
            System.out.println("Recipes not created");
        }
        toolbar.setButtonListener(new ToolbarListener() {
            public void changePanel(String panel) {
                JPanel changeToPanel = currentPanel;
                switch(panel) {
                    case "viewPanel":
                        updateViewPanel();
                        changeToPanel = viewPanel;
                        break;
                    case "addPanel":
                        changeToPanel = addPanel;
                        break;
                    case "editPanel":
                        if(currentPanel == viewPanel) {
                            if (recipeArr.size() != 0) {
                                changeToPanel = editPanel;
                                editPanel.editPanel(recipeArr.get(recipePosition));
                            } else {
                                errorMessage("There is no recipe to edit.");
                                return;
                            }
                        }
                        else
                            informationMessage("Move to \"View/Display Recipes\" Page to edit a recipe.");
                        break;
                    case "delete":
                        if(currentPanel == viewPanel) {
                            if(recipeArr.size() != 0) {
                                try {
                                    String[] options = {"Yes", "No"};
                                    int n = JOptionPane.showOptionDialog(null,
                                            "Are you sure you want to delete the recipe?",
                                            "delete",
                                            JOptionPane.YES_NO_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            options,
                                            options[0]);
                                    if (n == JOptionPane.YES_OPTION) {
                                        String recipePath = recipesFilePath + recipeArr.get(recipePosition).getTitle() + ".txt";
                                        File deleteFile = new File(recipePath);
                                        deleteFile.delete();
                                        recipeArr.remove(recipeArr.get(recipePosition));
                                        if (recipePosition != 0) recipePosition -= 1;
                                        informationMessage("Recipe has been deleted.");
                                        updateViewPanel();
                                    }
                                } catch (Exception e) {
                                    errorMessage("Error: There is something wrong with files; Recipe could not be deleted.");
                                }
                            }
                            else
                                errorMessage("Error: There is no recipe to delete.");
                        }
                        else
                            informationMessage("Move to \"View/Display Recipes\" Page to delete a recipe.");
                        break;
                    case "scalePanel":
                        if(currentPanel == viewPanel) {
                            if (recipeArr.size() != 0) {
                                changeToPanel = scalePanel;
                            } else {
                                errorMessage("There is no recipe to scale.");
                                return;
                            }
                        }
                        else
                            informationMessage("Move to \"View/Display Recipes\" Page to scale a recipe.");
                        break;
                    case "sortPanel":
                        changeToPanel = sortPanel;
                        break;
                    case "helpPanel":
                        changeToPanel = helpPanel;
                        break;
                }

                remove(currentPanel);
                add(changeToPanel, BorderLayout.CENTER);
                validate();
                repaint();
                currentPanel = changeToPanel;
            }
        });

        viewPanel.setButtonListener(new ViewRecipeListener() {
            public void switchRecipe(String switchDirection) {
                if(recipeArr.size() != 0) {
                    if (switchDirection == "left") {
                        recipePosition = ((recipePosition - 1) + recipeArr.size()) % recipeArr.size();
                        viewPanel.updateViewPanel(recipeArr.get(recipePosition));
                    } else if (switchDirection == "right") {
                        recipePosition = (recipePosition + 1) % recipeArr.size();
                        viewPanel.updateViewPanel(recipeArr.get(recipePosition));
                    }
                }
            }
        });

        addPanel.setButtonListener(new AddRecipeListener() {
            public void addRecipe(String title, String intro, String ingredients, String directions) {
                String recipePath = recipesFilePath + title + ".txt";
                File newRecipe = new File(recipePath);

                try {
                    boolean created = newRecipe.createNewFile();
                    if(created) {
                        writeToFile(newRecipe, title, intro, ingredients, directions);
                        addRecipeToArr(newRecipe, true);
                        informationMessage("Your recipe has been added.");
                    }
                    else {
                        errorMessage("Error: There is already a recipe with that name.");
                    }
                }
                catch (Exception e) {
                    errorMessage("Error: There is something wrong with files; Recipe could not be created.");
                }
            }
        });

        editPanel.setButtonListener(new EditRecipeListener() {
            public void editRecipe(boolean submit, String title, String intro, String ingredients, String directions) {
                if(submit) {
                    String recipePath = recipesFilePath + title + ".txt";
                    File newRecipe = new File(recipePath);

                    try {
                            writeToFile(newRecipe, title, intro, ingredients, directions);
                            addRecipeToArr(newRecipe, false);
                            informationMessage("Your recipe has been edited.");
                    }
                    catch (Exception e) {
                        errorMessage("Error: There is something wrong with files; Recipe could not be edited.");
                    }
                }
                remove(editPanel);
                updateViewPanel();
                add(viewPanel, BorderLayout.CENTER);
                validate();
                repaint();
                currentPanel = viewPanel;
            }
        });

        scalePanel.setButtonListener(new ScaleRecipeListener() {
            public void scaleRecipe(boolean submit, boolean scaleUp, int amount) {
                if(submit) {
                    Recipe currRecipe = recipeArr.get(recipePosition);
                    currRecipe.scale(scaleUp, amount);

                    String title = currRecipe.getTitle();
                    String intro = currRecipe.getIntroduction();
                    String ingredients = currRecipe.getIngredients();
                    String directions = currRecipe.getDirections();

                    String recipePath = recipesFilePath + title + ".txt";
                    File newRecipe = new File(recipePath);

                    try {
                        writeToFile(newRecipe, title, intro, ingredients, directions);
                        informationMessage("Your recipe has been scaled.");
                    }
                    catch (Exception e) {
                        errorMessage("Error: There is something wrong with files; Recipe could not be scaled.");
                    }
                }
                remove(scalePanel);
                updateViewPanel();
                add(viewPanel, BorderLayout.CENTER);
                validate();
                repaint();
                currentPanel = viewPanel;
            }
        });

        sortPanel.setButtonListener(new SortRecipeListener() {
            public void sortRecipe() {
                sortPanel.updateRecipeArr(recipeArr);
            }
        });

        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void updateRecipeArr() {
        try {
            File dir = new File(new File(App.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParentFile().getPath() + "/Recipes");

            File[] txtFiles = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".txt");
                }
            });

            if (txtFiles != null) {
                for (File f : txtFiles) {
                    addRecipeToArr(f, true);
                }
            }
        } catch(Exception e) {
            System.out.println(e +" in updateRecipeArr");
        }

    }

    private void updateViewPanel() {
        if(recipeArr.size() != 0)
            viewPanel.updateViewPanel(recipeArr.get(recipePosition));
        else
            viewPanel.updateViewPanel(null);
    }

    private void addRecipeToArr(File f, boolean add) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String readLine, title;
            String intro = "";
            String directions = "";

            reader.readLine(); //Title
            title = reader.readLine();

            Recipe newRecipe = new Recipe(title);

            reader.readLine(); //Introduction
            readLine = reader.readLine();
            while (!readLine.equals("Ingredients")) { //Reading introduction
                intro += readLine + "\n";
                readLine = reader.readLine();
            }

            newRecipe.addIntroduction(intro);

            readLine = reader.readLine();
            while (!readLine.equals("Directions")) { //Reading ingredients
                String[] splitStr = readLine.split("--", 2);
                if(splitStr.length == 2)
                    newRecipe.addIngredient(splitStr[0], splitStr[1]);
                readLine = reader.readLine();
            }

            readLine = reader.readLine();
            while (readLine != null) { //Reading directions
                directions += readLine + "\n";
                readLine = reader.readLine();
            }

            newRecipe.addDirections(directions);

            reader.close();

            if(add) recipeArr.add(newRecipe);
            else {
                recipeArr.set(recipePosition, newRecipe);
            }
        }
        catch(Exception e) {
            System.out.println(e + " in addRecipeToArr");
        }
    }

    private void writeToFile(File recipeFile, String title, String intro, String ingredients, String directions) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(recipeFile, false));

            bufferedWriter.write("Title");
            bufferedWriter.newLine();
            bufferedWriter.write(title);
            bufferedWriter.newLine();
            bufferedWriter.write("Introduction");
            bufferedWriter.newLine();
            bufferedWriter.write(intro);
            bufferedWriter.newLine();
            bufferedWriter.write("Ingredients");
            bufferedWriter.newLine();
            bufferedWriter.write(ingredients);
            bufferedWriter.newLine();
            bufferedWriter.write("Directions");
            bufferedWriter.newLine();
            bufferedWriter.write(directions);
            bufferedWriter.close();
        }
        catch (Exception e) {
            System.out.println(e + " in writeToFile");
        }
    }

    private void errorMessage(String text) {
        JOptionPane.showMessageDialog(null, text, text, JOptionPane.ERROR_MESSAGE);
    }

    private void informationMessage(String text) {
        JOptionPane.showMessageDialog(null, text, text, JOptionPane.INFORMATION_MESSAGE);
    }
}