# Chain of Responsibility Pattern

CoR (w ścisłym znaczeniu) to łańcuszek obiektów, z których każdy może posłużyć ALTERNATYWNIE do realizacji (obsługi)
pewnego zadania. Przykładowo, załóżmy że aplikacja kliencka żąda od nas jakichś danych. Możemy dostarczyć je na kilka
sposobów: z pliku, z bazy danych, z "chmury", albo użyć wartości domyślnej. Zależy nam na takiej kolejności wykonywania:

1. Jeśli mamy dane lokalnie w pliku, przekazujemy je i nie wywołujemy niepotrzebnie pozostałych metod.
2. Jeśli nie ma danych w pliku, próbujemy z bazą danych. Jeśli są w niej dane, przekazujemy je i nie wywołujemy reszty
   metod.
3. Jeśli powyższe nie przyniosło rezultatów, szukamy danych w "chmurze". Jeśli tam są, pobieramy je.
4. W ostateczności przekazujemy domyślne dane.

Każde ogniwo naszego łańcucha przedstawia jedną WYKLUCZAJĄCĄ próbę obsługi zadania: jeśli się uda, pozostałe próby są
niepotrzebne.

W _luźniejszym_ znaczeniu, CoR oznacza także dowolny proces składający się z wielu etapów pośrednich (A -> B -> C). Choć
taka implementacja jest niemal identyczna, NIE POWINNO SIĘ stosować wobec niej nazwy "Chain of Responsibility", ponieważ
w CoR ważna jest "niezależność" ogniw, tj. fakt, że każde z nich stawowi niejako kolejną, odrębną próbę rozwiązania
problemu.

W moim przykładzie próbuję zdobyć jedzenie, kolejno: szukając odgrzewek z wczoraj, próbując usmażyć jajka, albo
zamawiając pizzę.

### Zalety

- pozwala "opanować" skomplikowaną, sekwencyjną procedurę
- pozwala łatwo dynamicznie manipulować kolejnością wykonywania etapów, np. zależnie od preferencji użytkownika
- przydatne jeśli wiemy, że będziemy musieli obsługiwać konkretne zadanie, ale jeszcze nie wiemy, jakie będą
  implementacje
- wspiera regułę **Single Responsibility**: oddziela _wywoływanie_ od _wykonywania_
- wspiera regułę **Open/Closed**: bardzo łatwo dodawać/zmieniać ogniwa łańcucha, bez psucia istniejącego kodu

### Wady

- rozrost kodu
- typowa implementacja nie gwarantuje, że żądanie zostanie obsłużone (żadna z metod łańcucha może nie być właściwa)

### Ogólny schemat implementacji

Interfejs `IHandler`

- interfejs funkcyjny
- metoda `findFood` (typowo zwana `execute`) usiłująca zrealizować zadanie
- konkretne implementacje przedstawiają "kompletny" sposób obsługi żądania

Klasa `ChainElement`

- spina ze sobą konkretną implementację `IHandler` oraz referencję do kolejnego `ChainElement`
- każde "ogniwo" próbuje obsłużyć zadanie: jeśli się uda, kończy łańcuszek, jeśli nie, przekazuje odpowiedzialność do
  kolejnego elementu, jeśli ten istnieje