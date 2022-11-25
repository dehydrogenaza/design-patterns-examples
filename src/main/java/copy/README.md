# Shallow Copy vs. Deep Copy

Kopia Płytka/Głęboka to nie tyle wzorzec projektowy, ile pewien mechanizm występujący w Javie i wynikający ze sposobu
przekazywania argumentów do funkcji w programach. Rozważmy przykład, klasę `Book`.

`Book` jest typem referencyjnym (złożonym). Gdy wywołujemy konstruktor `Book`, gdzieś w pamięci przydzielonej
naszemu programowi powstaje obiekt tego typu (ze wszystkimi polami). "Zmienna" typu `Book` _nie jest obiektem_, a
jedynie referencją, czyli jego adresem w pamięci. Gdy przekazujemy zmienną jako parametr do jakiejś metody (np.
konstruktora innego obiektu), nie przekazujemy samego obiektu, a jedynie adres, który pozwala wywołanej
metodzie odnieść się do naszego `Book`. W ten sposób nigdy nie wykonujemy kopii. Takie zachowanie nazywa się
"przekazywaniem przez referencję" i jest charakterystyczne dla wszystkich typów złożonych w Javie¹ (typy
prymitywne są zaś "przekazywane przez wartość", czyli faktycznie kopiowane).

¹ _Małe zmącenie dla ciekawych: ściśle rzecz biorąc, do metod przekazywana jest referencja "przez wartość" (tzn. sama
referencja - adres - jest kopiowany)._

Jeśli chcemy faktycznie _skopiować_ obiekt, musimy sami zaimplementować mechanizm kopiowania. W Javie istnieje bardzo
stary interfejs `Cloneable`, o którym możecie poczytać, ale jest on obecnie niezalecany. Preferowany jest tzw.
_konstruktor kopiujący_, tj. konstruktor, który jako parametr przyjmuje _oryginał obiektu tego samego typu_ i na jego
podstawie ustawia pola tworzonego obiektu (metodą _głęboką_ lub _płytką_).

```
class Foo {
  //pola klasy
  
  public Foo(Foo original) {
    //this.pole1 = original.pole1;
    //this.pole2 = original.pole2;
    //...
  }
}
```

Istnieją 2 główne warianty kopiowania:

### Shallow Copy

- kopiuje **wyłącznie** pola proste: kopia ma ich własne, niezależne wartości
- kopiuje **same adresy** pól złożonych — te adresy wciąż wskazują na ten sam obiekt w pamięci!
- oznacza to, że jeśli zmodyfikujemy pola złożone _oryginału_, to zmienią się te same pola _każdej kopii_ (i odwrotnie)
- **tworzenie takiej kopii jest szybkie i "tanie"**: jeśli zależy nam na wydajności i nie chcemy duplikować np.
  ogromnych _kolekcji_, powinniśmy rozważyć zastosowanie Shallow Copy, jednakże...
- ...należy pamiętać, że stosowanie Shallow Copy **łamie zasady Enkapsulacji, Single Responsibility oraz Open/Closed**

### Deep Copy

- kopiuje **zawartość w pamięci** dla wszystkich pól
- pola (proste i złożone) kopii funkcjonują całkowicie niezależnie
- w przypadku dużych obiektów (np. zawierających duże `List`y albo `Map`y) może być bardzo kosztowne

Istnieje jeszcze pewna optymalizacja zwana `Lazy Copy`: jest to _płytka kopia_, która zawiera dodatkowy licznik, dzięki
któremu śledzi liczbę kopii elementu. W sytuacji, gdy dochodzi do modyfikacji współdzielonego pola, "na żądanie"
tworzona jest _kopia głęboka_. Wariant ten pozwala zaoszczędzić niepotrzebnego kopiowania kosztownych pól, które
modyfikowane są sporadycznie. Jeśli jednak spodziewamy się, że takie pola będą modyfikowane często, nie ma sensu
wprowadzać tej dodatkowej komplikacji, i trzeba rozważyć albo _Deep Copy_, albo _Shallow Copy_ (jeśli przeważają względy
wydajnościowe).