# Data Access Object Pattern

Wzorzec DAO polega na stworzeniu dodatkowej warstwy abstrakcji między kodem klienckim a niskopoziomowym dostępem do
tzw. _persistence layer_ (_warstwa źródła danych_, w korpo-pongliszu także _warstwa trwałości_), służącej do
długoterminowego przechowywania danych (tzn. trzymania informacji dłużej niż tylko na czas działania programu). Warstwą
tą może być np. jeden z wielu rodzajów bazy danych lub plików, a nasza aplikacja nie musi nic na ten temat "wiedzieć":
interesuje ją tylko wykorzystanie danych, a nie to, jak są przechowywane.

DAO jest koncepcyjnie powiązany ze wzorcami takimi jak _Fasada_ czy nawet _Adapter_. Można powiedzieć, że stanowi wysoce
wyspecjalizowany przykład tych wzorców.

Choć nie jest to konieczne, przyjęło się (zwłaszcza w środowisku Javy), że DAO powinien organizować operacje opisane
akronimem CRUD:

- **Create**: tworzenie (zapisywanie) danych w warstwie źródła danych
- **Read**: pobieranie (odczytywanie) danych ze źródła
- **Update**: modyfikacja danych zawartych w źródle
- **Delete**: usuwanie danych ze źródła

Każda z tych funkcjonalności może mieć po kilka wersji, odpowiadających naszemu zapotrzebowaniu (np. dotyczących
pojedynczego obiektu lub zakresu obiektów). Implementacja wszystkich z nich nie jest konieczna z punktu widzenia DAO;
jak widać, choćby Update jest już nadmiarowe (bo teoretycznie mogłoby być zastąpione przez Delete i Create, choć w
przypadku baz danych wiązałoby się to ze zwiększonym kosztem dostępu).

### Zalety

- wspiera **enkapsulację** i regułę **Open/Closed**: uniezależnia warstwy "logiki biznesowej" i "źródła danych",
  ułatwiając ich niezależne rozszerzanie
- ułatwia testy jednostkowe (łatwo podstawić fake'a pod źródło danych na potrzeby testów)

### Wady

- może zachęcać do duplikacji kodu przy tworzeniu kilku podobnych DAO pod dany interfejs (unikamy tego, stosując kolejne
  abstrakcje: dodatkowe interfejsy, klasę bazową, klasy pomocnicze)
- źle zaprojektowany interfejs może łatwo stać się bardzo niewydajny; poszczególne rodzaje źródeł danych często mają
  swoje specyficzne "optymalne sposoby pracy", które mogą nie podlegać łatwej abstrakcji przy pomocy wspólnego
  interfejsu (czasami, gdy wydajność jest kluczowa, po prostu nie da się uniknąć pisania metod "pod konkretne źródło")

### Ogólny schemat implementacji

`DAOInterface`

- określa metody, z jakich może korzystać kod kliencki, by wchodzić w interakcję z danymi
- zwykle są to przynajmniej metody `create`, `read` i `delete` (przeważnie też `update`)

```
public interface DAOInterface {
  void create(MyClass myObj);
  MyClass read(MySelector selection);
  void delete(MySelector selection);
}
```

---

`DAO`

- konkretne implementacje interfejsu
- odnoszą się do określonego źródła danych i ukrywają szczegóły związane z komunikacją z tym źródłem
- mają jakiś sposób komunikacji ze źródłem (bezpośrednio lub przez referencję do kolejnej warstwy abstrakcji
  zarządzającej szczegółami tego kontaktu na jeszcze niższym poziomie)
- są wymienialne

```
public class FooDAO {
  private source = FooSource.getInstance();
  
  //some configuration of 'source'

  public void create(MyClass myObj) {
    source.add(myObj);
  }
  
  public MyClass read(MySelector selection) {
    if (source.has(selection))
      return source.sendRequest(selection);

    return null;
  }
  
  public void delete(MySelector selection) {
    source.remove(selection);
  }
}
```