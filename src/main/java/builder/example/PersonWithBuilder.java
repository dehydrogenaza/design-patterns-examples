package builder.example;

import java.time.LocalDate;

public class PersonWithBuilder extends PersonBase {

    //Do konstruktora przekazujemy tylko Builder, z którego następnie wyciągamy potrzebne wartości. "Męczymy się"
    // tylko raz, robiąc tę klasę - późniejsza praca z nią jest już wygodniejsza i bardziej elastyczna.
    //
    //Konstruktor jest PRYWATNY, co oznacza, że nie można go wywołać spoza tej klasy. W ten sposób upewniamy się, że
    // użytkownicy będą konstruować obiekty poprzez Builder, a nie bezpośrednio, co może być niepożądane jeśli np.
    // nasz proces tworzenia wiąże się z weryfikacją jakichś danych.
    //
    //Jeżeli naprawdę chcemy zabezpieczyć klasę przed wywołaniem konstruktora, powinniśmy ją uczynić FINAL -> będzie
    // bardziej "zamknięta na modyfikacje", ale też zamknie się na "rozszerzenie", więc jest to taki tradeoff. ;)
    private PersonWithBuilder(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.dateOfBirth = builder.dateOfBirth;
        this.firstNames = builder.firstNames;
        this.lastNames = builder.lastNames;
        this.mothersMaidenName = builder.mothersMaidenName;
        this.title = builder.title;
        this.nationality = builder.nationality;
        this.citizenship = builder.citizenship;
        this.heightInCMs = builder.heightInCMs;
        this.weightInKGs = builder.weightInKGs;
    }

    //Builder jest statyczny, dzięki czemu możemy się do niego łatwo dostać notacją PersonWithBuilder.Builder()
    public static class Builder {
        private static final String DEFAULT_DESCRIPTION = "Domyślna wartość opisu";

        //W Builderze mamy te same pola, co w budowanej klasie (u nas pola są w PersonBase), co łamie zasadę DRY -
        // jest to częsta krytyka tego Patterna.
        //
        // Nowa Java (chyba 14+) pozwala na lepsze rozwiązanie tego problemu z wykorzystaniem RECORDs jako klasy
        // bazowej (wtedy nie musimy duplikować w niej pól, bo są generowane automatycznie).
        //
        //Tutaj postanowiłem przedstawić "tradycyjny" Pattern.
        private int id;

        //Builder pozwala łatwo stworzyć wartość domyślne dla pól. Tutaj postanowiłem, że jeśli user nie poda żadnego
        // 'description', to zostanie użyta wartość zapisana w stałej DEFAULT_DESCRIPTION. Gdybyśmy chcieli to robić
        // w konstruktorze, to musielibyśmy albo stworzyć oddzielny konstruktor, który nie przyjmuje 'description',
        // albo przekazywać 'null' jako parametr, co jest Złą Praktyką™.
        private String description = DEFAULT_DESCRIPTION;
        private LocalDate dateOfBirth;
        private String[] firstNames;
        private String[] lastNames;
        private String mothersMaidenName;
        private String title;
        private String nationality;
        private String[] citizenship;
        private double heightInCMs;
        private double weightInKGs;

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withFirstNames(String... firstNames) {
            this.firstNames = firstNames;
            return this;
        }

        public Builder withLastNames(String... lastNames) {
            this.lastNames = lastNames;
            return this;
        }

        public Builder withMothersMaidenName(String mothersMaidenName) {
            this.mothersMaidenName = mothersMaidenName;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withNationalIdentity(String nationality, String... citizenship) {
            this.nationality = nationality;
            this.citizenship = citizenship;
            return this;
        }

        //Builder Pattern często jest też wykorzystywany do weryfikacji pól - chodzi o to, by upewnić się, że KAŻDY
        // obiekt klasy PersonWithBuilder zawsze będzie "prawidłowy" tzn. zgodny z naszymi założeniami. Ułatwi to w
        // przyszłości debugowanie programu, bo zawsze wiemy, że problem nie wynika z wewnętrznego stanu obiektu, a z
        // czegoś, co z nim próbujemy zrobić.
        //
        //W tym przypadku, jeśli user poda złe wartości, są one po prostu ignorowane (z wyświetleniem ostrzeżenia), ale
        // oczywiście to jak problem zostanie rozwiązany już zależy od implementacji. Inny przykład walidacji
        // przedstawiłem w metodzie .create.
        public Builder withMeasurements(double heightInCMs, double weightInKGs) {
            if (heightInCMs <= 0.0 || weightInKGs <= 0.0) {
                System.out.println(">> Ostrzeżenie: zignorowano nieprawidłowe wymiary. <<");
            } else {
                this.heightInCMs = heightInCMs;
                this.weightInKGs = weightInKGs;
            }
            return this;
        }

        //Uznałem, że w moim designie każdy obiekt PersonWithBuilder MUSI mieć 'id' oraz 'dateOfBirth'. Dlatego,
        // zamiast tworzyć dla tych pól pseudo-settery .with, podaję je jako obowiązkowy parametr do samej funkcji
        // .create.
        // Ma to sens ponieważ tych pól "obowiązkowych" jest mało - gdyby było ich dużo, to wrócilibyśmy do
        // punktu wyjścia. Zamiast tego robi się wówczas .create() bez parametrów i dodaje walidację (np. jakieś
        // null checki).
        //
        //Ponadto, w metodzie upewniam się, że podana data jest właściwa (zgodna z moimi intencjami jako twórcy klasy
        // PersonWithBuilder). Jeśli nie, to tutaj po prostu rzucam wyjątek - jest typu unchecked, więc nie muszę go
        // łapać.
        public PersonWithBuilder create(int id, LocalDate dateOfBirth) {
            this.id = id;

            if (dateOfBirth.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Data urodzenia nie może wypadać w przyszłości");
            }
            this.dateOfBirth = dateOfBirth;

            return new PersonWithBuilder(this);
        }
    }
}
