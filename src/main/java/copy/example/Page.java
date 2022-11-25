package copy.example;

//Testowa klasa zawierająca wyłącznie typy proste, do których należą:
//  * typy prymitywne: boolean, short, int, long, float, double, char;
//  * typ String, który stanowi w Javie odrębną, "pośrednią" kategorię między typami prymitywnymi i referencyjnymi.
//W przypadku powyższych typów NIE MA RÓŻNICY między Shallow a Deep copy.
//
//Rozróżnienie jest istotne przy typach złożonych. Samo Page, choć zawiera tylko typy proste, jest już typem
// złożonym, a więc przy kopiowaniu Page miałoby znaczenie, czy wykonujemy Shallow czy Deep copy.
public class Page {
    private final int pageNumber;
    private final String text;

    public Page(int pageNumber, String text) {
        this.pageNumber = pageNumber;
        this.text = text;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getText() {
        return text;
    }
}
