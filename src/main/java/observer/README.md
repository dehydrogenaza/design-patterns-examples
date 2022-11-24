# Observer Pattern

Wzorzec Obserwator, blisko związany z Mediatorem, rozwiązuje sytuację, w której jakaś grupa obiektów musi być
poinformowana o zmianie stanu jakiegoś innego komponentu, ale sama nie musi przekazywać informacji zwrotnej do tego
komponentu. Z punktu widzenia implementacji jest to zazwyczaj "jednostronny Mediator": zamiast Mediatora mamy _Nadawcę_,
który rozsyła wiadomości po _Subskrybentach_ (ale istnieje między tymi wzorcami pewna różnica koncepcyjna, o czym niżej)
.

W czasie pracy programu obiekty (komponenty) powinny mieć możliwość _subskrybowania_ (`subscribe()`) i zwykle także
_anulowania subskrypcji_ (`unsubscribe()`), dzięki czemu mogą "dołącząć do zainteresowanych" lub się z nich "wypisywać",
zależnie np. od tego, co akurat próbuje zrobić użytkownik. W praktyce wzorzec ten dość często bywa wykorzystywany w
interfejsach graficznych (gdzie _Nadawca_ może informować różne elementy GUI o pewnej akcji, co np. pociąga za
sobą konieczność zamknięcia pewnych okien, schowania menu, schowania popupów itd.).

W przedstawionym przykładzie tworzę "tablicę ofert pracy dla programistów" (to będzie nasz _Nadawca_). Subskrybentami są
obiekty `Developer` - w tym przypadku jest to wspólna klasa abstrakcyjna, ale bardzo często jest to interfejs funkcyjny,
definiujący metodę `update()` (nazwa nie jest ściśle przestrzegana). U nas `ProgrammingJobsBoard` wywołuje w `Developer`
metodę `considerJob()` - nasz wariant "update".

### Porównanie z Mediatorem

Observer czasami pozwala na dwustronną komunikację: _Nadawca_ < - > _Subskrybenci_. W takim układzie wydaje się
identyczny co Mediator, ale różnica jest w intencji. **Observer** ma przede wszystkim na celu stworzenie "relacji
zależności", w której jeden obiekt zarządza grupą innych obiektów. **Mediator** ma na celu przede wszystkim abstrakcję
komunikacji między obiektami, a sam nie ma w tym układzie szczególnej roli (jest tylko "posłańcem").

### Zalety

- pozwala zależnie od potrzeb, dynamicznie zmieniać listę komponentów, które otrzymują powiadomienia
- wspiera regułę **Open/Closed**, pod warunkiem wykorzystania interfejsów, a nie konkretnych klas jako _Nadawców_ i
  _Subskrybentów_

### Wady

- jak zwykle: początkowy narzut ilości kodu
- w typowej implementacji nie mamy kontroli nad szczegółami procesu powiadamiania _subskrybentów_ (np. kolejnością)

### Ogólny schemat implementacji

`Nadawca`

- ma referencję do _subskrybentów_
- jeśli ma być wymienny, implementuje "interfejs Nadawcy"
- implementuje przynajmniej metody:
    - `subscribe` do rejestrowania nowego odbiorcy
    - `notifySubscribers` wywoływana z zewnątrz (spoza układu _Nadawca-Subskrybenci_), "nakazuje" _Nadawcy_ powiadomić
      wszystkich _Subskrybentów_
- zazwyczaj implementuje też:
    - `unsubscribe` do wyrejestrowywania odbiorcy

`Subskrybent`

- implementują wspólny interfejs (albo dziedziczą po jednej klasie)
- mają metodę `update` lub podobną, służącą do "reagowania na wiadomość", tj. wywoływaną przez _Nadawcę_
