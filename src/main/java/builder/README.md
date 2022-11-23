# Builder Pattern

Builder to klasa, która upraszcza tworzenie bardzo skomplikowanych obiektów. Najprostszym zastosowaniem są klasy o
bardzo dużej liczbie pól, które musielibyśmy uzupełnić w konstruktorze. Zamiast tego, do konstruktora takiej klasy
wrzucamy jedynie specjalnie stworzonego Buildera, którego wcześniej w wygodny sposób inicjalizujemy w wielu metodach. W
pewnym sensie "rozbijamy" ogromny konstruktor na serię mniejszych metod.

W tworzeniu Buildera przyjęło się, że każda "cząstkowa" metoda budująca zwraca referencję do "swojego" obiektu (
czyli `this`), co umożliwia *chaining* metod. Reszta to detale implementacyjne (jak w każdym Patternie, jest kilka
sub-wariantów), ja najbardziej lubię *builder wewnętrzny (statyczny)*, czyli taki, który definiowany jest wewnątrz klasy
docelowej. Zaletą jest to, że można wtedy ustawić konstruktor jako prywatny (bo Builder i tak będzie go "widział" z
wnętrza klasy), wadą że kod z wewnętrznymi klasami jest potencjalnie mniej czytelny (choć to kwestia dyskusyjna, mi się
np. podoba sposób wywołania buildera wewnętrznego ze statycznego kontekstu).

### Zalety

- łatwiejsza praca z późniejszym kodem
- większa elastyczność konstrukcji: ułatwia m.in. implementację _wartości domyślnych_ oraz _walidacji_
- można tworzyć obiekt etapami
- można wielokrotnie wykorzystywać "częściowo utworzony" builder object
- wspiera regułę **Single Responsibility**: wydziela odpowiedzialność "konstrukcji obiektu" od reszty obiektu

### Wady

- zwiększa stopień komplikacji początkowego kodu (czasami nie opłaca się go stosować)
- większość implementacji narusza regułę **Don't Repeat Yourself**, ponieważ wymaga pewnej duplikacji kodu; pewnym
  rozwiązaniem od Javy 14+ są _rekordy_, ale nie wszędzie można je zastosować

### Ogólny schemat implementacji

W docelowej klasie `MyObject`:

- prywatny konstruktor przyjmujący obiekt `Builder` jako parametr
- statyczna klasa wewnętrzna `Builder`

W klasie `Builder`:

- te same pola, co w `MyObject`
- metody do ustawiania parametrów
  - nazwa zwykle zaczyna się od `with`, np. `.withFeature(MyFeature feature)`
  - zwraca `this`
- metoda `.create` do tworzenia obiektu docelowego
  - zwraca obiekt docelowy

Użycie:
```
MyObject obj = new MyObject.Builder()
        .withFeature(feature)
        .withSomeValue(value)
        .create();
```