package copy.example;

public class Photocopier {

    //Metoda wykorzystuje KONSTRUKTOR KOPIUJĄCY klasy Book do stworzenia KOPII GŁĘBOKIEJ, w której wszystkie pola
    // (także indywidualne strony) będą "na wyłączność" nowego obiektu.
    public Book photocopy(Book book) {
        return new Book(book);
    }
}
