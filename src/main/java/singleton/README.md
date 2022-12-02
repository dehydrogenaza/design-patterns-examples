# Singleton Pattern

Singleton, w oryginalnym znaczeniu, jest kontrowersyjnym wzorcem projektowym, pozwalającym stworzyć _unikalny obiekt_
(tylko jeden na program) o _globalnym dostępie_ (można odnieść się do niego z każdego miejsca w programie/module/paczce)
. Czasami tym terminem określa się też wzorce, które realizują tylko pierwsze z tych zadań.

Singleton jest bardzo często spotykany w praktyce (ze względu na jego wygodę), jednak nie jest zalecany, ponieważ łamie
szereg zasad dobrego stylu.

W Javie singletona można skonstruować na kilka sposobów. Ich cechą wspólną jest to, że mają **prywatny konstruktor**
(co uniemożliwia utworzenie dodatkowych instancji klasy z innych miejsc w kodzie), oraz **statyczną metodę pozyskiwania
instancji**. Implementacje dzielą się na kilka rodzajów:

### a. Eager Singleton

- instancja (jedyna) Singletona jest tworzona od razu po uruchomieniu programu
- instancja "zaśmieca" zasoby programu od uruchomienia, nawet jeśli potrzebna jest znacznie później (lub wcale, np.
  jeśli określony Singleton wykorzystywany jest tylko w pewnym rzadkim typie operacji)
- ponieważ specyfikacja Javy zapewnia sekwencyjny, deterministyczny sposób inicjalizacji pól statycznych, ta wersja
  Singletona jest bezpieczna w zastosowaniach wielowątkowych
- najprostszy w implementacji
- w Javie można go bardzo łatwo zrealizować przy użyciu Enuma

### b. Lazy Singleton

- instancja Singletona tworzona jest w momencie pierwszego jego użycia (więc wcześniej nie zajmuje zasobów)
- każde kolejne użycie wykorzystuje pierwotną instancję
- takiego Singletona **nie można używać wielowątkowo**, ponieważ dwa wątki mogą równocześnie stworzyć swoje wersje "
  pierwszej instancji", tym samym łamiąc całą ideę posiadania "jedynej instancji"

### c. Lazy Synchronized Singleton

- znany także jako _thread-safe singleton_ lub _double-checked singleton_
- wariant Lazy Singleton wykorzystujący blok `synchronized` do kontroli dostępu do inicjalizacji, dzięki czemu
  niemożliwe jest przypadkowe powstanie kilku obiektów w wyniku jednoczesnego wywołania z wielu wątków
- nadaje się do stosowania w aplikacjach wielowątkowych
- niesie za sobą pewien narzut wydajnościowy
- w Javie powszechny, ale **niezalecany** — preferowane jest rozwiązanie poniższe

### d. Lazy Holder Singleton

- znany także jako _initialization-on-demand holder idiom_
- jest **charakterystyczny dla Javy** (nie można na nim polegać w dowolnym innym języku), ponieważ wykorzystuje
  specyficzne zachowanie JVM wynikające ze specyfikacji Javy
- jest bezpieczny w zastosowaniach wielowątkowych i ma mniejszy narzut wydajnościowy niż _Lazy Synchronized Singleton_
- wykorzystuje fakt, że JVM inicjalizuje klasy wewnętrzne dopiero w momencie ich pierwszego wywołania
- NIE MOŻE być stosowany w przypadku, jeśli zakładamy, że konstrukcja pierwotnej instancji może się z jakiegoś powodu
  nie powieść (będzie tylko jedna próba inicjalizacji)
- jest rzadziej stosowany (m.in. dlatego, że nie występuje w wielu innych językach), dlatego może zmniejszać czytelność
  kodu

---

## Zalety

- możemy mieć pewność, że dany obiekt występuje tylko w jednym egzemplarzu
- zapewniamy łatwy dostęp do obiektu, z którego korzystamy w większości programu
- jest _nieco_ lepszy od zmiennych globalnych
- ułatwia realizację innych wzorców projektowych (z którymi często jest łączony), m.in. **Fasady** i **Fabryki**

## Wady

- łamie regułę **Single Responsibility** (Singleton realizuje _dwie_ rzeczy: wyjątkowość instancji oraz globalny dostęp)
- łamie regułę **Open/Closed**, wprowadzając możliwość niekontrolowanej modyfikacji instancji z wielu miejsc w
  programie, a jednocześnie utrudniając rozszerzanie programu przez uzależnienie całości od jednego "superobiektu"
- utrudnia testy jednostkowe (na dwa sposoby: zwiększając współzależności między komponentami, a także utrudniając "
  symulację" instancji Singletona)