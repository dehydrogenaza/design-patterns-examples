package copy;

import copy.example.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Klasa do testów, nie jest częścią wzorca.

//W tym przykładzie tworzę "książkę", kopiuję ją na dwa sposoby, wprowadzam pewne modyfikacje i wyświetlam wyniki, by
// pokazać różnicę między Deep a Shallow copy.
public class CopyClient {

    public static void main(String[] args) {
        //Tworzymy kilka "stron" będących zawartością naszej książki.
        List<Page> pages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String pageContent = "To jest strona nr " + (i + 1);
            pages.add(new Page(i + 1, pageContent));
        }
        //Oryginał książki.
        Book book = new Book("Zostań programistą w 2 tygodnie xD", false, "IT", LocalDate.now(), pages);

        System.out.println("Oryginał: ");
        System.out.println(book); //Dzięki przesłonięciu metody .toString() mogę łatwo wyświetlać obiekty typu Book.

        Book sameBook = book;
        System.out.println("Przypisanie do nowej zmiennej to tylko \"zmiana etykietki\":");
        System.out.println(sameBook);
        System.out.println("Czy jest to ten sam obiekt? \n\t\t> "
                + (book == sameBook)); //TRUE
        System.out.println("Czy jego strony są tą samą listą? \n\t\t> "
                + (book.getPages() == sameBook.getPages())); //TRUE

        Photocopier xerox = new Photocopier(); //"Ksero" będzie tworzyło DEEP COPY książek.
        Book photocopiedBook = xerox.photocopy(book);
        System.out.println("\n--------------------------\nKsero (kopia głęboka): ");
        //Ten egzemplarz niczym nie różni się od oryginału, ponieważ nie wprowadziliśmy jeszcze żadnych modyfikacji,
        // ale jest to nowy, niezależny obiekt, którego wszystkie pola są również całkowicie niezależne od oryginału.
        System.out.println(photocopiedBook);
        System.out.println("Czy jest to ten sam obiekt? \n\t\t> "
                + (book == photocopiedBook)); //FALSE
        System.out.println("Czy jego strony są tą samą listą? \n\t\t> "
                + (book.getPages() == photocopiedBook.getPages())); //FALSE (!)

        //"Baza danych" będzie tworzyła SHALLOW COPY i przechowywała je w swoich wewnętrznych "katalogach". Każda
        // kopia jest co prawda nowym obiektem, ale nie jest całkowicie niezależna: jej pola złożone (choćby lista
        // stron) w rzeczywistości wskazują na dokładnie ten sam obiekt w pamięci.
        LibraryDatabase libraryDatabase = new LibraryDatabase();
        System.out.println("\n--------------------------\nZAWARTOŚĆ BAZY DANYCH");
        libraryDatabase.add(book, "thriller", "manual");
        libraryDatabase.add(photocopiedBook, "thriller", "manual");
        libraryDatabase.showManuals();
        libraryDatabase.showThrillers();
        System.out.println("\nKOPIA Z KATALOGU");
        System.out.println("Czy jest to ten sam obiekt? \n\t\t> "
                + (book == libraryDatabase.getBook(0, "thriller"))); //FALSE
        System.out.println("Czy jego strony są tą samą listą? \n\t\t> "
                + (book.getPages() == libraryDatabase.getBook(0, "thriller").getPages())); //TRUE (!)

        //Modyfikacja oryginału wpływa na płytkie kopie i odwrotnie.
        System.out.println("\n--------------------------\nUSUWAM LOSOWE 5 STRON ORYGINAŁU");
        book.censorRandom(5);
        System.out.println("Oryginał po cenzurze: ");
        System.out.println(book);

        System.out.println("Z katalogu (kopia płytka) po cenzurze: ");
        System.out.println(libraryDatabase.getBook(0, "thriller"));

        System.out.println("Ksero (kopia głęboka) po cenzurze: ");
        System.out.println(photocopiedBook);

        //Płytkie kopie stworzone w LibraryDatabase zostały zmienione. "Oryginał fotokopii" nie został zmieniony, ale
        // trzeba pamiętać, że w LibraryDatabase mamy teraz jego płytkie kopie ("płytkie kopie głębokiej kopii").
        // Innymi słowy, jeśli zmienimy oryginalną książkę 'book', nie wpłynie to na egzemplarze fotokopii dodane do
        // bazy danych...
        libraryDatabase.showManuals();
        libraryDatabase.showThrillers();

        //...jeśli jednak zmienimy fotokopię 'photocopiedBook', zmienią się także jej płytkie kopie w bazie danych
        // (ale nie kopie oryginalnej 'book'!).
        System.out.println("\n--------------------------\nUSUWAM LOSOWE 8 STRON FOTOKOPII");
        photocopiedBook.censorRandom(8);
        libraryDatabase.showManuals();
        libraryDatabase.showThrillers();
    }
}
