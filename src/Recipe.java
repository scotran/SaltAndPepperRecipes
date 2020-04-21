import java.util.*;

public class Recipe {
    private String title, introduction, directions;
    private Map<String, String> ingredients = new HashMap<String, String>();

    public Recipe(String title) {
        this.title = title;
    }

    public void addIntroduction(String introduction) { this.introduction = introduction; }

    public void addDirections(String directions) { this.directions = directions; }

    public void addIngredient(String amount, String ingredient) {
        ingredients.put(ingredient, amount);
    }

    public String getTitle() { return title; }

    public String getIntroduction() { return introduction; }

    public String getDirections() { return directions; }

    public String getIngredients() {
        String ingredientStr = "";
        for(Map.Entry<String, String> entry : ingredients.entrySet()) {
            ingredientStr += entry.getValue() + " " + entry.getKey() + "\n";
        }
        return ingredientStr;
    }

    public String getIngredientsForEdit() {
        String ingredientStr = "";
        for(Map.Entry<String, String> entry : ingredients.entrySet()) {
            ingredientStr += entry.getValue() + "--" + entry.getKey() + "\n";
        }
        return ingredientStr;
    }

    public void scale(boolean scaleUp, int amount) {
        for(Map.Entry<String, String> entry : ingredients.entrySet()) {
            String newIng, newAmount;
            int oldAmount;
            String[] splitIng = entry.getValue().split(" ", 2);

            if(splitIng.length == 2) {
                newIng = " " + splitIng[1];
                oldAmount = Integer.parseInt(splitIng[0]);
            }
            else {
                newIng = "";
                oldAmount = Integer.parseInt(entry.getValue());
            }

            if(scaleUp) {
                newAmount = String.valueOf(oldAmount*amount);
            }
            else {
                newAmount = String.valueOf(oldAmount/amount);
            }

            ingredients.replace(entry.getKey(), newAmount + newIng);
        }
    }

    public String amountOfIngredient(String ingredient) {
        if(ingredients.containsKey(ingredient)) {
            return ingredients.get(ingredient);
        }
        else
            return null;
    }
}