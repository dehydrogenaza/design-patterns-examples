# Factory Pattern

Factory to sposób tworzenia **obiektów należących do tej samej grupy/rodziny** (np. mających wspólny interfejs lub
dziedziczących z tej samej klasy), bez zakładania z góry, który **dokładnie** typ będziemy tworzyć. Ma zastosowanie
wtedy,
gdy chcemy stworzyć różne obiekty, ale nie interesują nas ich implementacje,
a jedynie to, co takie obiekty są w stanie robić (czyli jakie metody eksponują).

Factory pozwala nam zdefiniować abstrakcyjny sposób tworzenia grupy obiektów, a klasom na niższym poziomie abstrakcji
"decydować" (dopiero w trakcie wykonania programu) jaki obiekt stworzyć.

Są 2 główne warianty tego wzorca.

### Factory Method

a.k.a. *Static Factory*

Prostsza implementacja z jednym typem fabryki. Do obsługi fabryki używamy tej samej klasy, w której fabrykę
zaimplementowaliśmy; zazwyczaj definiujemy metodę statyczną `.create` lub podobną (używane są też inne nazwy, np. w
samej bibliotece standardowej Javy dość częste jest `.of` i `.valueOf` - jak widzicie takie nazwy to prawdopodobnie
macie właśnie do czynienia z taką fabryką ;) ).

### Abstract Factory

Implementuje **wiele fabryk** mających **wspólny interfejs**. Minusem jest to, że dochodzi nam wiele dodatkowych klas,
a plusem to, że ta wersja pozwala w bardzo wygodny sposób tworzyć obiekty, które są jakby "na przekroju" dwóch cech.
Przykładowo, jeśli mamy rodzinę typów {A, B, C} oraz inną rodzinę {1, 2, 3}, to dzięki fabryce abstrakcyjnej możemy
łatwo tworzyć obiekty będące dowolną ich kombinacją, czyli {A1, A2, A3, B1, B2, B3, C1, C2, C3}.

W obsłudze tego wzorca używamy _interfejsu_ fabryki (a nie bezpośrednio implementacji). Każda implementacja fabryki
zajmuje się jednym wariantem pierwszej "rodziny" (np. A) i może tworzyć wszystkie warianty drugiej rodziny (czyli 1, 2,
3), co w rezultacie daje nam A1, A2 i A3. Kolejna implementacja robi to samo z B itd.

W takich fabrykach metoda `.create` NIE jest statyczna, ponieważ użytkownik będzie zwykle chciał stworzyć instancję
wybranej implementacji (zależnie np. od danych wejściowych programu).

W "prawdziwych" dość skomplikowanych fabrykach, często wydziela się klasę np. `FactoryProvider`, która jest poniekąd 
"fabryką fabryk" i enkapsuluje tworzenie wybranej implementacji fabryki. W tym prostym przykładzie już nie tworzyłem
oddzielnej klasy, tworzenie fabryki jest w `FactoryClient`.

### Zalety

- zwiększona abstrakcja (a zatem elastyczność) tworzenia obiektów
- developer ma pewność, że tworzone obiekty będą ze sobą "kompatybilne" (mogą być stosowane wymiennie)
- ułatwiona rozszerzalność kodu (jeśli chcemy np. dodać kolejny podtyp klasy bazowej), wspiera regułę **Open/Closed**
    - dodawanie podtypów nie psuje istniejącego kodu klienckiego
- wspiera regułę **Single Responsibility** dzięki wyodrębnieniu kodu tworzącego obiekty

### Wady

- dużo dodatkowego kodu, zwłaszcza w wersji Abstract
- zwiększa liczbę zależności między klasami

### Ogólny schemat implementacji

Docelowa grupa klas

- dziedziczą po jednej klasie bazowej...
- ...lub implementują jeden interfejs

Fabryka

- ma metodę `.create` która przyjmuje typ (albo jakiś identyfikator) żądanego obiektu i zwraca stworzoną instancję
    - wartość zwracana jest "abstrakcyjnego" typu, dzięki czemu można tam przypasować dowolny obiekt danej grupy
- w wersji _Abstract_ ma wiele implementacji, z których każda pracuje z inną grupą obiektów, ale wszystkie mają ten sam
  interfejs