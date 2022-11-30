package facade.kitchen_library;

import java.util.Objects;

//Reprezentuje typ + ilość składnika. Te detale mogą być przydatne dla innych użytkowników "zewnętrznej" biblioteki
// `kitchen_library`, dlatego zostały zaimplementowane w taki sposób, jednak dla naszego programu nie są konieczne.
// Między innymi dlatego warto skorzystać z Fasady, aby te detale ukryć.
public record Ingredient(String name, int amount, MeasurementUnit unit) {
    @Override
    public String toString() {
        return amount + " " + unit + " " + name;
    }

    //Dla uproszczenia sprawdzamy tylko, czy równe są pola 'name'.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
