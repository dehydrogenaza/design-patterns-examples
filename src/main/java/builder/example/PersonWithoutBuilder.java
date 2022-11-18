package builder.example;

import java.time.LocalDate;

public class PersonWithoutBuilder extends PersonBase {

    //"Zwykła" implementacja, w konstruktorze przekazujemy wartości wszystkich pól klasy bazowej PersonBase.
    public PersonWithoutBuilder(int id, String description, LocalDate dateOfBirth, String[] firstNames,
                                String[] lastNames, String mothersMaidenName, String title, String nationality,
                                String[] citizenship, double heightInCMs, double weightInKGs) {
        this.id = id;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.firstNames = firstNames;
        this.lastNames = lastNames;
        this.mothersMaidenName = mothersMaidenName;
        this.title = title;
        this.nationality = nationality;
        this.citizenship = citizenship;
        this.heightInCMs = heightInCMs;
        this.weightInKGs = weightInKGs;

    }
}
