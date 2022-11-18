package builder;

import builder.example.PersonBase;
import builder.example.PersonWithBuilder;
import builder.example.PersonWithoutBuilder;

import java.time.LocalDate;
import java.time.Month;

//poletko do testowania, samo w sobie nie ma nic wspólnego z patternem Buildera
public class Client {

    public static void main(String[] args) {
        //0: bez buildera:
        PersonBase person0 = new PersonWithoutBuilder(0, "Zwykły konstruktor, bez Buildera", LocalDate.of(1960,
                Month.DECEMBER, 24), new String[]{"Anna"}, new String[]{"Nowak", "Kowalska"}, "Brzęczyszczykiewicz",
                "Pani", "Polish", new String[]{"Polish", "German"}, 165.5, 62.3);
        System.out.println(person0);

        //1: z builderem, podane pełne dane
        PersonBase person1 = new PersonWithBuilder.Builder()
                .withDescription("Użycie buildera z podaniem wszystkich danych")
                .withFirstNames("John", "Bob")
                .withLastNames("Smith")
                .withMothersMaidenName("Brown")
                .withTitle("Dr")
                .withNationalIdentity("Scottish", "British", "Ugandan")
                .withMeasurements(201, 95.5)
                .create(1, LocalDate.of(1990, Month.APRIL, 1));
        System.out.println(person1);

        //2: z builderem, domyślne 'description'
        PersonBase person2 = new PersonWithBuilder.Builder()
                .withFirstNames("John", "Bob")
                .withLastNames("Smith")
                .withMothersMaidenName("Brown")
                .withTitle("Dr")
                .withNationalIdentity("Scottish", "British", "Ugandan")
                .withMeasurements(201, 95.5)
                .create(2, LocalDate.of(1990, Month.APRIL, 1));
        System.out.println(person2);

        //3: z builderem, tylko obowiązkowe pola + opis
        PersonBase person3 = new PersonWithBuilder.Builder()
                .withDescription("Użycie buildera tylko z obowiązkowymi polami")
                .create(3, LocalDate.of(2021, Month.SEPTEMBER, 10));
        System.out.println(person3);

        //4: z builderem, podanie nieprawidłowego opcjonalnego parametru
        PersonBase person4 = new PersonWithBuilder.Builder()
                .withDescription("Użycie buildera z podaniem nieprawidłowej (ujemnej) wagi")
                .withFirstNames("Maria")
                .withMeasurements(150.5, -6)
                .create(4, LocalDate.of(1983, Month.NOVEMBER, 30));
        System.out.println(person4);

        //5 i 6: builder wielokrotnego użytku
        PersonWithBuilder.Builder reusableBuilder = new PersonWithBuilder.Builder()
                .withDescription("Wielokrotne użycie tego samego buildera")
                .withLastNames("Nowakowski")
                .withNationalIdentity("Polish", "Polish");

        PersonBase person5 = reusableBuilder
                .withFirstNames("Maciej")
                .create(5, LocalDate.of(2020, Month.AUGUST, 3));
        PersonBase person6 = reusableBuilder
                .withFirstNames("Zbigniew")
                .create(6, LocalDate.of(2022, Month.MARCH, 11));
        System.out.println(person5);
        System.out.println(person6);

        //7: builder, podanie nieprawidłowego koniecznego parametru (rzuci wyjątek)
//        PersonBase person7 = new PersonWithBuilder.Builder()
//                .withDescription("Użycie buildera z podaniem nieprawidłowej daty")
//                .withFirstNames("Podróżnik w Czasie")
//                .withMeasurements(150.5, 65)
//                .create(7, LocalDate.of(2375, Month.NOVEMBER, 30));
//        System.out.println(person7);
    }
}
