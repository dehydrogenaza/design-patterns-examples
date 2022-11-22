# Iterator Pattern

Iterator to wzorzec, który pozwala wydzielić z **kolekcji** pewien obiekt (zwany właśnie **iteratorem**), który
odpowiedzialny jest za _sekwencyjny dostęp do wszystkich elementów kolekcji_ (tzw. iterację). Wzorzec ten umożliwia taką
iterację bez eksponowania szczegółów implementacji kolekcji — dzięki temu możemy łatwiej "podmieniać" kolekcję bez
potrzeby zmieniania całego kodu klienckiego (zachowana jest reguła Single Responsibility).

Ogólnie, Iterator musi umożliwiać dwie rzeczy:

- Podgląd, czy istnieje "kolejny" element kolekcji.
- Pobranie elementu i przejście do następnego.

Ponadto, jest dobrą praktyką (praktycznie wymogiem), by Iterator _uwzględniał obsługę wielowątkową_, tj. umożliwiał
jednoczesną pracę nad kolekcją kilku wątkom. W przypadku iteratorów, które nie obsługują usuwania elementów w czasie
pracy (nie mają opcjonalnej metody `.remove()`) stosunkowo łatwo to zrealizować — jeśli "indeks" obecnego elementu jest
polem nie-statycznym, każdy Iterator ma de facto swoją kopię indeksu, więc różne Iteratory mogą spokojnie robić swoje.
Gorzej, jeśli elementy znikają albo pojawiają się w czasie pracy i zmienia się rozmiar kolekcji, wtedy inne iteratory
mogą "przeskakiwać elementy" albo wychodzić poza zakres...

### Iterator w Javie

W Javie wzorzec Iterator jest poniekąd wbudowany, ponieważ korzysta z niego pętla "enhanced for" (tzw. for-each), czyli
taka:

```
for (Thing thing : things) {
    thing.doTheThing();
}
```

Konsekwencją jest to, że sposób implementacji tego wzorca w Javie jest określony z góry. Aby na naszej kolekcji można
było używać pętli for-each, musi ona implementować interfejs `Iterable<E>`, gdzie `E` to typ obiektów w kolekcji.
Interfejs `Iterable` jest funkcyjny i definiuje tylko jedną metodę, `.iterator()`, która zwraca z kolei implementację _
innego_
interfejsu, `Iterator<E>`. Dokładniejszy opis tych interfejsów jest w przykładzie, w klasie `Shelf`. Może się to wydawać
zamieszane, ale zasadniczo schemat jest taki:

```
MojaKolekcja implements Iterable<TypElementu> {
    @Override
    public Iterator<TypElementu> iterator() {
        return new Iterator<TypElementu> {
            @Override
            public boolean hasNext() {
                //zwróć true jeśli istnieje jeszcze jakiś element do pobrania z kolekcji
            }

            @Override
            public TypElementu next() {
                //zwróć jeden z pozostałych elementów kolekcji i przestaw iterator na kolejny element
                //nie sprawdzaj dodatkowo, czy kolejny element istnieje - zrobi to metoda hasNext()
            }
        };
    }
}
```

W powyższym schemacie użyłem _klasy anonimowej_ do implementacji `Iterator`, ale oczywiście można też użyć klasy
wewnętrznej (tak zrobiłem w przykładzie `Shelf`).

Opcjonalnie, `Iterator` może też implementować metody `.remove()` oraz `.forEachRemaining()`. Nie jest to konieczne,
ponieważ istnieją implementacje domyślne, ale warto zaznaczyć, że domyślne `.remove()` zaledwie rzuca wyjątek (nie ma
żadnej funkcjonalności).

### Zalety

- prawidłowo zaimplementowany iterator umożliwia równoległe przetwarzanie kolekcji przez kilka wątków
- można zaimplementować iterator z funkcją "pauzowania" iteracji, co może być istotne w przypadku ogromnych kolekcji
- wspiera regułę **Single Responsibility**, ponieważ wydziela proces iteracji z samej kolekcji
- wspiera regułę **Open/Closed**, ponieważ zwiększa "wymienialność" kolekcji (kod kliencki może z nich korzystać
  zamiennie, ponieważ eksponują interfejs `Iterable`)

### Wady

- jak zwykle, czasami jest to przerost formy nad treścią; jeżeli naszą "kolekcją" jest prosta tablica, to trzeba
  zastanowić się, czy coś zyskujemy wprowadzając iterator
- iterator dodaje pewien (niewielki) narzut w porównaniu z bardziej "przyziemnymi" sposobami iteracji, co może być ważne
  w krytycznych metodach programu, od których zależy wydajność całości

### Ogólny schemat implementacji

_(przedstawiono powyżej)_