package iterator;

import iterator.example.Bread;
import iterator.example.Shelf;

import java.time.LocalDate;

//Klasa do testów — nie jest częścią wzorca.
public class IteratorClient {

    public static void main(String[] args) {
        //"Shelf" jest zdefiniowaną przez nas, nową kolekcją.
        //Dla testów zapełniamy ją różnymi "chlebami".
        Shelf breadShelf = new Shelf();
        breadShelf.putBread(new Bread("wholewheat", LocalDate.now().plusDays(2)));
        breadShelf.putBread(new Bread("white", LocalDate.now().plusDays(1)));
        breadShelf.putBread(new Bread("rye", LocalDate.now().plusDays(5)));
        breadShelf.putBread(new Bread("wholewheat", LocalDate.now()));
        breadShelf.putBread(new Bread("spelt", LocalDate.now().plusDays(2)));
        breadShelf.putBread(new Bread("white", LocalDate.now().plusDays(2)));
        breadShelf.putBread(new Bread("white", LocalDate.now().plusDays(3)));
        breadShelf.putBread(new Bread("rye", LocalDate.now().plusDays(6)));
        breadShelf.putBread(new Bread("wholewheat", LocalDate.now().plusDays(3)));
        breadShelf.putBread(new Bread("white", LocalDate.now().plusDays(2)));

        //Dzięki implementacji ITERABLE w klasie Shelf możemy używać zwykłej pętli for-each.
        //Zaimplementowany przeze mnie Iterator zwraca wszystkie chlebki w kolejności dodania, ale możemy kombinować:
        // np. zwracać elementy w odwrotnej kolejności, określony typ elementu, albo np. co drugi element (jak w
        // kolejnym przykładzie).
        System.out.println("\nWSZYSTKIE CHLEBKI:");
        for (Bread bread : breadShelf) {
            System.out.println(bread);
        }

        //Ten iterator zwraca jedynie co drugi chleb.
        //Ze względu na sposób "zintegrowania" wzorca Iterator w samej Javie, samo "breadShelf" zawsze będzie miało
        // tylko jeden iterator, ale możemy sobie zdefiniować w Shelf metodę (u nas .iterateEveryOtherBread),
        // która zwróci inny wariant Iterable — moglibyśmy nawet podawać do niej parametr, np. określający
        // co ile iterator ma przeskakiwać.
        System.out.println("\nCO DRUGI CHLEBEK:");
        for (Bread bread : breadShelf.iterateEveryOtherBread()) {
            System.out.println(bread);
        }

        Shelf emptyShelf = new Shelf();
        System.out.println("\nPUSTA PÓŁKA:");
        for (Bread bread : emptyShelf) {
            System.out.println(bread);
        }
        for (Bread bread : emptyShelf.iterateEveryOtherBread()) {
            System.out.println(bread);
        }
    }
}
