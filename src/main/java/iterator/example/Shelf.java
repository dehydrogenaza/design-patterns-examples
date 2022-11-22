package iterator.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


//Shelf jest nową kolekcją. Tak naprawdę wykorzystuję tu "ArrayList", co oczywiście nie ma większego sensu (ArrayList
// jest już Iterable), ale w praktycznym kodzie moglibyście tu zaimplementować jakąś kolekcję zupełnie inną niż
// standardowa (np. jeden z wielu rodzajów grafu).
//Dygresja: Implementacje kolekcji to trochę obszerniejszy temat i jeśli ktoś chce się w to wgłębić, polecam książkę
// "Wprowadzenie do algorytmów" Thomas H. Cormen i inni, wyd. PWN 3. edycja — bagatelka 1251 stron bez indeksu :D
// (ale poszczególne struktury danych są zaskakująco zwięźle i zrozumiale opisane, po prostu jest ich od cholery).
//
//Kluczowa jest u nas implementacja Iterable<T>, gdzie T to typ obiektów zawartych w naszej kolekcji.
//Interfejs Iterable jest częścią bazowej Javy i zawiera tylko jedną metodę, iterator(), która zwraca ITERATOR
// służący do przechodzenia po kolejnych elementach kolekcji. Sam ITERATOR możemy zaimplementować albo jako klasę
// anonimową, albo jako klasę wewnętrzną (tutaj wykorzystałem to drugie podejście).
//
//W Javie implementacja Iterable pozwala klasie działać w pętli "enhanced for" (a.k.a. for-each).
public class Shelf implements Iterable<Bread> {
    //To jest "fejkowa" implementacja naszej nowej kolekcji. Niczym nie różni się od zwykłego ArrayList, a w
    // praktyce zapewne byłoby to *znacznie* bardziej skomplikowane.
    private final List<Bread> shelfContents = new ArrayList<>();

    public void putBread(Bread bread) {
        shelfContents.add(bread);
    }

    //Metoda z interfejsu Iterable. Zwraca Iterator, czyli obiekt odpowiedzialny za poruszanie się po kolekcji.
    //Tutaj zaimplementowałem to z użyciem nazwanej wewnętrznej klasy GenericBreadIterator (która jest de facto
    // naszym iteratorem). Można też użyć klasy anonimowej, w taki sposób:
//    @Override
//    public Iterator<Bread> iterator() {
//        return new Iterator<Bread>() {
//            @Override
//            public boolean hasNext() {
//                //twoja implementacja
//            }
//
//            @Override
//            public Bread next() {
//                //twoja implementacja
//            }
//        };
//    }
    @Override
    public Iterator<Bread> iterator() {
        return new GenericBreadIterator();
    }


    //Sposób wykorzystania wzorca ITERATOR w Javie pozwala na istnienie tylko jednego "głównego" iteratora (co ma
    // sens, bo inaczej zwiększałoby to choćby złożoność składni pętli for). Możemy jednak użyć pewnego "triku" do
    // stworzenia kilku iteratorów: tutaj tak naprawdę zwracamy drugi (inny) Iterator, EveryOtherBreadIterator,
    // "opakowany" w zupełnie nowy, anonimowy typ Iterable — możemy tak zrobić, ponieważ Iterable jest typem
    // funkcyjnym (zatem kompilator sam się domyśli, że "return EveryOtherBreadIterator::new" jest naszą implementacją
    // jedynej metody .iterator() z tego interfejsu).
    //
    //Aby skorzystać z takiego iteratora, musimy wywołać tę "opakowującą" metodę w pętli for. W przykładzie
    // IteratorClient pokazałem, jak to zrobić.
    public Iterable<Bread> iterateEveryOtherBread() {
        return EveryOtherBreadIterator::new;
    }

    //Główny iterator naszej kolekcji.
    //Implementacja interfejsu Iterator WYMAGA następujących metod:
    //      .hasNext() : sprawdza, czy istnieje obecny (tj. wskazany przez "wewnętrzny wskaźnik") element kolekcji
    //      .next() : zwraca obecnie wskazywany element kolekcji i PRZECHODZI do następnego elementu (ustawia
    //          "wewnętrzny wskaźnik" na kolejny element — jego istnienie będzie sprawdzone automatycznie
    //          metodą .hasNext(), zatem nie powinniśmy tego sprawdzać tu ponownie)
    //Ponadto, implementacja MOŻE MIEĆ następujące metody (jeśli ich nie dostarczymy, wykorzystane zostaną DEFAULTY):
    //      .remove() : usuwa ostatnio zwrócony element. Domyślna implementacja jedynie RZUCA WYJĄTEK
    //          UnsupportedOperation — jeśli chcemy mieć możliwość usuwania elementów z kolekcji w trakcie iteracji,
    //          MUSIMY zaimplementować także i tę metodę
    //      .forEachRemaining(Consumer<> consumer) : wykonuje metodę 'consumer' (interfejs funkcyjny!) na każdym
    //          elemencie kolekcji; jeśli nie robimy z naszą kolekcją nic "dziwnego" to domyślna implementacja jest
    //          wystarczająca by to działało
    private class GenericBreadIterator implements Iterator<Bread> {
        //Większość implementacji Iteratora ma jakiś "wewnętrzny wskaźnik" pozwalający na pilnowanie, z którym
        // elementem kolekcji aktualnie pracujemy. U nas jest to zwykły int, ale czasami to może być coś bardziej
        // skomplikowanego.
        private int index = 0;

        //Pozwoliłem sobie założyć, że rozmiar mojej kolekcji nie będzie zmieniał się w trakcie pracy iteratora
        // (np. przez usunięcie elementu w innym wątku); bardziej "kompletna" implementacja powinna to uwzględniać.

        //Czy element wskazany przez 'index' istnieje w obrębie kolekcji?
        @Override
        public boolean hasNext() {
            return index < shelfContents.size();
        }

        //Zwróć "obecny" element i przesuń 'index'.
        @Override
        public Bread next() {
            Bread nextBread = shelfContents.get(index);
            index++;
            return nextBread;
//            return shelfContents.get(index++); //prostszy zapis tego samego, bez zmiennej nextBread
        }

        //Prawidłowa implementacja .remove() jest nieco bardziej skomplikowana, ale w sumie nie jest niezbędna do
        // zrozumienia tego Wzorca, więc sobie darowałem. Jeśli Was ciekawią wewnętrzne szczegóły działania kolekcji
        // w Javie, polecam poczytać sobie klasę ArrayList: wbrew pozorom nie jest *aż tak* trudna.
    }

    //Alternatywny iterator, który przeskakuje po co drugim "chlebku". Innym przykładem dodatkowego
    // iteratora byłby np. "iterator cykliczny", czyli taki, który przechodzi po elementach w kółko, w nieskończoność.
    private class EveryOtherBreadIterator implements Iterator<Bread> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < shelfContents.size();
        }

        @Override
        public Bread next() {
            Bread nextBread = shelfContents.get(index);
            index += 2;
            return nextBread;
        }
    }
}
