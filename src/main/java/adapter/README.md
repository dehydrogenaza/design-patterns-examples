# Adapter Pattern

Wzorzec **Adaptera** polega na utworzeniu łącznika między dwiema klasami eksponującymi niezgodne ze sobą interfejsy
(metody). W skrócie polega on na stworzeniu trzeciej klasy, implementującej docelowy interfejs (wybrany spośród tych
dwóch zgodnie z naszymi preferencjami i zapotrzebowaniami projektu) i "tłumaczącej" go na metody drugiego obiektu, do
którego dostęp uzyskujemy albo dzięki dziedziczeniu, albo przez kompozycję (czyli po prostu umieszczenie referencji do
obiektu wewnątrz naszego Adaptera).

Prostym przykładem jest sytuacja, gdy jedna z klas (adaptowana) operuje np. _plain textem_, a druga (docelowa) potrafi
odczytywać wyłącznie prawidłowo sformatowany XML. W takim przypadku Adapter pozwoli nam "ukryć" szczegóły tej konwersji,
a kod kliencki będzie mógł korzystać z docelowej klasy tak, jak dotychczas, nie przejmując się różnicą w formacie
danych.

Wzorzec ten znajduje zastosowanie, gdy chcemy dołączyć do projektu zewnętrzne biblioteki (które z oczywistych względów
będą niezgodne z naszymi założeniami, a ich bezpośrednia modyfikacja czasami jest wręcz niemożliwa ze względu na brak
dostępu do kodu źródłowego), lub gdy adaptujemy nasze własne stare rozwiązania do nowej wersji projektu, a nie możemy
(lub nie chcemy) sobie pozwolić na całkowitą eliminację starego komponentu.

W przykładzie stworzyłem "zewnętrzną" klasę `NorskSvar`, która "odpowiada" na wiadomości typu String, oraz nowy
interfejs `IResponsive`, który realizuje tę funkcjonalność w niekompatybilny sposób.

### Zalety

- jest dogodnym, a w praktyce często jedynym sposobem połączenia dwóch niezgodnych komponentów
- wspiera regułę **Single Responsibility**, wydzielając konwersję interfejsów z samych klas
- wspiera regułę **Open/Closed** na 2 sposoby:
    - ułatwia rozszerzanie użyteczności istniejących klas, integrując je z nowymi systemami
    - jednorazowe stworzenie solidnego interfejsu Adaptera ułatwia w przyszłości stworzenie kolejnych Adapterów i dalsze
      rozszerzanie programu
- wspiera regułę **Dependency Inversion**, ponieważ umożliwia aplikacji klienckiej pracę z komponentem przez jego
  Adapter, bez znajomości szczegółów implementacji stojących za Adapterem

### Wady

- wzrost komplikacji kodu, który w prostych przypadkach nie jest wart zachodu, zwłaszcza jeśli mamy możliwość "łatwej"
  modyfikacji klasy adaptowanej i nie spodziewamy się konieczności tworzenia dodatkowych Adapterów
- czasami klasy mogą okazać się na tyle różne, że stworzenie Adaptera byłoby czasowo nieopłacalne
- jeżeli nie potrzebujemy _wszystkich_ funkcjonalności klasy adaptowanej, warto rozważyć zastosowanie wzorca **Fasady**
  zamiast Adaptera

### Ogólny schemat implementacji

`Klasa adaptowana`:

- niekompatybilna (stara/zewnętrzna) klasa, którą chcemy dołączyć do projektu
- ma pewne metody, z których chcemy korzystać

```
class ExternalTool {
    public String foo() {
        //niekompatybilna metoda
        return (someCondition) ? "one" : "zero";
    }
    //etc.
}
```

---

`Nowy interfejs`:

- docelowy sposób, w jaki chcemy korzystać z funkcjonalności `klasy adaptowanej`

```
interface INewToolset {
    int bar(); //preferowany sposób korzystania z ExternalTool w obecnej wersji projektu
    //niestety, klasa ExternalTool zwraca String, a nam zależy koniecznie na int
}
```

---

Istnieją **dwa główne warianty** tego wzorca:

> #### Z zastosowaniem kompozycji

`Adapter`:

- implementuje `nowy interfejs`
- posiada pole z referencją do instancji `klasy adaptowanej`
- w metodach z `nowego interfejsu` wywołuje odpowiednie metody na obiekcie `klasy adaptowanej`
- ma dostęp do publicznych pól/metod `klasy adaptowanej`
- nie korzysta z dziedziczenia, co na ogół upraszcza strukturę kodu w dużych projektach

```
class CompositionAdapter implements INewToolset {
    private ExternalTool externalTool;
    
    //konstruktor przekazuje externalTool jako parametr

    public int bar() { //implementacja interfejsu INewToolset
        if (externalTool.foo().equals("one")) return 1;
        return 0;
    }
}
```

> #### Z zastosowaniem dziedziczenia

`Adapter`:

- implementuje `nowy interfejs`
- dziedziczy po `klasie adaptowanej`
- w metodach z `nowego interfejsu` wywołuje odpowiednie metody odziedziczone z `klasy adaptowanej`
- ma dostęp do publicznych oraz _chronionych_ pól/metod `klasy adaptowanej`
- może być trudniejszy w zarządzaniu, zwłaszcza jeśli później będziemy chcieli tworzyć kolejne warstwy dziedziczenia po
  Adapterze

```
class InheritanceAdapter extends ExternalTool implements INewToolset {
    //dzięki dziedziczeniu InheritanceAdapter ma dostęp do pól/metod public/protected z ExternalTool
    //nie jest konieczna referencja do dodatkowego obiektu ExternalTool

    public int bar() {
        if (foo().equals("one")) return 1;
        return 0;
    }
}
```