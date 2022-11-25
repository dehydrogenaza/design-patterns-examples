package copy.example;

import java.util.ArrayList;
import java.util.List;

public class LibraryDatabase {
    private final List<Book> manualCatalog = new ArrayList<>();
    private final List<Book> thrillerCatalog = new ArrayList<>();

    //Ta metoda tworzy KOPIE PŁYTKIE podanej książki i przypisuje im kategorię (po 1 kopii na kategorię).
    //Każda PŁYTKA KOPIA ma własne wartości pól prostych (prymitywnych i Stringów), ale WSPÓŁDZIELI pola złożone: w
    // naszym przypadku datę wydania oraz listę stron. Oznacza to, że jeśli zmienimy jakoś np. strony oryginału (albo
    // jednej z kopii), wpłynie to na WSZYSTKIE PŁYTKIE KOPIE ORAZ ORYGINAŁ.
    public void add(Book book, String... categories) {
        for (String category : categories) {
            if (category.equalsIgnoreCase("manual")) {
                Book copyAsManual = book.shallowCopy();
                //pole 'genre` jest typu prostego, więc zmiana jego wartości nie wpłynie na pozostałe egzemplarze
                copyAsManual.setGenre("manual");
                manualCatalog.add(copyAsManual);
            }
            if (category.equalsIgnoreCase("thriller")) {
                Book copyAsThriller = book.shallowCopy();
                copyAsThriller.setGenre("thriller");
                thrillerCatalog.add(copyAsThriller);
            }
        }
    }

    //Pobieranie książki po indeksie w katalogu, na potrzeby testów.
    public Book getBook(int index, String category) {
        return switch(category) {
            case "manual" -> manualCatalog.get(index);
            case "thriller" -> thrillerCatalog.get(index);
            default -> throw new IllegalStateException("Nie ma kategorii: " + category);
        };
    }

    //Wyświetlanie krótkich opisów książek z danego katalogu.
    public void showManuals() {
        System.out.println("Dostępne podręczniki:");
        for (Book book : manualCatalog) {
            System.out.println("* " + book.getDescription() + " - " + book.getPages().size() + " stron(y)");
        }
    }

    public void showThrillers() {
        System.out.println("Dostępne thrillery:");
        for (Book book : thrillerCatalog) {
            System.out.println("* " + book.getDescription() + " - " + book.getPages().size() + " stron(y)");
        }
    }
}
