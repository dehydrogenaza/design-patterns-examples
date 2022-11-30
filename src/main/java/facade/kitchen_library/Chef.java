package facade.kitchen_library;

import java.util.List;

//Element skomplikowanej "zewnętrznej biblioteki", zawierający detale, które mogłyby być potrzebne niektórym jej
// użytkownikom. Naszemu programowi na nich nie zależy — ukrywamy je za Fasadą.
public class Chef {
    private final String name;
    private final int experienceLevel;

    public Chef(String name, int experienceLevel) {
        this.name = name;
        this.experienceLevel = experienceLevel;
    }

    public void mixIngredients(List<Ingredient> ingredients, String stepName) {
        int timeRequired = Math.max(1, 2 * ingredients.size() - experienceLevel);

        StringBuilder mixed = new StringBuilder(getDisplayName());
        mixed.append("łączę ");
        for (int i = 0; i < ingredients.size(); i++) {
            mixed.append(ingredients.get(i));
            if (i < ingredients.size() - 1) {
                mixed.append(" + ");
            } else {
                mixed.append(". ");
            }
        }
        mixed.append("Zajmie to ")
             .append(timeRequired)
             .append(" min.");
        System.out.println(mixed);
        ingredients.clear();
        ingredients.add(new Ingredient(stepName, 1, MeasurementUnit.SZT));
    }

    public String wash(String item) {
        return "umyte " + item;
    }

    public String chop(String item) {
        return "pokrojone " + item;
    }

    public String getDisplayName() {
        return "[Kucharz " + name + "] ";
    }
}
