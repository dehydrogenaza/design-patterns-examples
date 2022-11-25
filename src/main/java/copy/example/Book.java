package copy.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Obiekty, które będziemy kopiować.
public class Book {
    private final String title; //Specjalna kategoria typu prostego.
    private final boolean hardcover; //Typ prosty.
    private String genre;
    private final LocalDate publicationDate; //Obiekt, czyli typ złożony.
    private final List<Page> pages; //Typ złożony (będący kolekcją innych typów złożonych).


    //Klasyczny konstruktor, którym możemy stworzyć zupełnie nowy obiekt.
    public Book(String title, boolean hardcover, String genre, LocalDate publicationDate, List<Page> pages) {
        this.title = title;
        this.hardcover = hardcover;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.pages = pages;
    }

    //KONSTRUKTOR KOPIUJĄCY — tutaj zastosowany do tworzenia KOPII GŁĘBOKIEJ (ale możemy też zdefiniować konstruktor
    // kopiujący płytko, jeśli to na tym nam zależy).
    //Konstruktor kopiujący przyjmuje obiekt tej samej klasy jako parametr i tworzy nowy obiekt (kopię) na jego
    // podstawie.
    public Book(Book original) {
        //W przypadku pól prostych, do stworzenia kopii głębokiej wystarczy przypisanie wartości z oryginału.
        this.title = original.title;
        this.hardcover = original.hardcover;
        this.genre = original.genre;

        //W przypadku pól złożonych, tworząc głęboką kopię, musimy stworzyć NOWE obiekty.
        //Tutaj używamy statycznej fabryki dostępnej w LocalDate przez metodę .from.
        this.publicationDate = LocalDate.from(original.publicationDate);
        //Lista również jest typem złożonym, więc tworzymy nową.
        this.pages = new ArrayList<>();
        for (Page originalPage : original.pages) {
            //To od nas zależy, jak "głęboko" skopiujemy obiekt. Moglibyśmy poprzestać na stworzeniu nowej listy i
            // zapełnieniu jej referencjami do istniejących już Page.
            //Aby jednak stworzyć *prawdziwą* kopię głęboką, musimy skopiować wszystkie "zagnieżdżone" obiekty. Tutaj
            // tworzymy indywidualną kopię każdej Page na podstawie oryginału i do listy dodajemy kopię.
            Page pageCopy = new Page(originalPage.getPageNumber(), originalPage.getText());
            this.pages.add(pageCopy);
        }
    }

    //Do kopiowania nie musimy koniecznie używać konstruktora — może to być metoda albo np. fabryka statyczna.
    //Tutaj implementujemy KOPIĘ PŁYTKĄ.
    public Book shallowCopy() {
        //Gdybyśmy zwrócili 'this', nie stworzylibyśmy kopii, a jedynie przekazali referencję do obecnego obiektu.
        //Dlatego zwracamy 'new Book'. W tym przypadku, pola 'title' i 'hardcover' (proste) zostają skopiowane, ale
        // do konstruktora nowego obiektu trafia jedynie referencja do TYCH SAMYCH PÓL, co w obiekcie oryginalnym.
        return new Book(title, hardcover, genre, publicationDate, pages);
    }

    //Pomocnicza metoda do testów, która "ocenzuruje" tj. usunie z książki kilka losowych stron.
    //Nie ma nic wspólnego ze wzorcem.
    public void censorRandom(int amount) {
        Random rng = new Random();
        for (int i = 0; i < amount; i++) {
            pages.remove(rng.nextInt(pages.size()));
        }
    }

    public String getDescription() {
        return "\""
                + title.toUpperCase()
                + "\" ("
                + publicationDate.getYear()
                + "), "
                + genre
                + ", "
                + (hardcover ? "twarda oprawa" : "miękka oprawa");
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder(getDescription());
        txt.append("\n");
        for (Page page : pages) {
            txt.append(" str. ")
                    .append(page.getPageNumber())
                    .append(": ")
                    .append(page.getText())
                    .append("\n");
        }

        return txt.toString();
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Page> getPages() {
        return pages;
    }
}
