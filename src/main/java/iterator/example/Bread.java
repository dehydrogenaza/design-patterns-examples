package iterator.example;

import java.time.LocalDate;

//Przykładowa klasa, której obiekty będziemy przechowywać w naszej nowej "kolekcji". Nie ma tu nic związanego z samym
// wzorcem — to mógłby być dowolny obiekt.
public class Bread {
    private final String type;
    private final LocalDate expirationDate;

    public Bread(String type, LocalDate expirationDate) {
        this.type = type;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Type: " + type
                + " | Expires: " + expirationDate.toString();
    }
}
