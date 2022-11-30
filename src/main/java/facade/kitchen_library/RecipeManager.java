package facade.kitchen_library;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

//Główny element skomplikowanej "zewnętrznej biblioteki". Naszemu programowi zależy tylko na "robieniu pizzy", a cała
// pozostała funkcjonalność nie jest potrzebna — dlatego ukrywamy ją za Fasadą.
public class RecipeManager {

    private final List<Ingredient> supplies;
    private final List<String> recipe;
    private final Map<String, List<Ingredient>> dishes;
    private Chef chef;

    public RecipeManager(List<Ingredient> supplies, List<String> recipe) {
        this.supplies = supplies;
        this.recipe = recipe;
        this.dishes = new HashMap<>();
    }

    public void assignChef(int requiredExperience) {
        switch(requiredExperience) {
            case 0 -> this.chef = new Chef("Krzysztof", 0);
            case 1 -> this.chef = new Chef("Hania", 1);
            case 2 -> this.chef = new Chef("Ania", 2);
            case 3 -> this.chef = new Chef("Kuba", 3);
            case 4 -> this.chef = new Chef("Mariola", 4);
            default -> throw new IllegalArgumentException("Nie ma kucharza o takim levelu!");
        }
    }

    public void prepDish(String dishID) {
        dishes.put(dishID, new LinkedList<>());
    }

    public void addToDish(Ingredient ingredient, String dishID) {
        checkDishStatus(dishID);

        if (!supplies.contains(ingredient)) {
            throw new IllegalArgumentException("Nie ma '" + ingredient + "' w spiżarni ;(((");
        }
        dishes.get(dishID).add(ingredient);
        System.out.println(" - " + chef.getDisplayName() + "dodaję '" + ingredient.name() + "' do '" + dishID + "'.");
    }

    public void mix(String dishID, int recipeStep) {
        checkDishStatus(dishID);

        List<Ingredient> currentDish = dishes.get(dishID);
        String currentStep = recipe.get(recipeStep);
        System.out.println(currentStep.toUpperCase() + ":");

        chef.mixIngredients(currentDish, currentStep);
    }

    public Ingredient prepVegetable(Ingredient foodstuff) {
        if (!supplies.contains(foodstuff)) {
            throw new IllegalArgumentException("Nie ma '" + foodstuff + "' w spiżarni ;(((");
        }
        String washed = chef.wash(foodstuff.name());
        String chopped = chef.chop(washed);
        Ingredient preppedFoodstuff = new Ingredient(chopped, 1, MeasurementUnit.SZT);

        supplies.add(preppedFoodstuff);
        return preppedFoodstuff;
    }

    public List<Ingredient> combineDishes(String firstDishID, String secondDishID) {
        checkDishStatus(firstDishID);
        checkDishStatus(secondDishID);

        List<Ingredient> combined = new LinkedList<>(dishes.get(firstDishID));
        combined.addAll(dishes.get(secondDishID));

        return combined;
    }

    private void checkDishStatus(String dishID) {
        if (!dishes.containsKey(dishID))
            throw new IllegalArgumentException("'" + dishID + "': nie ma takiego naczynia :/");
    }
}
