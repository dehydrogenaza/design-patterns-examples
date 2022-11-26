# State Pattern (State Machine Pattern)

**Stan** to wzorzec wydzielający pewne zachowania klasy do grupy oddzielnych klas mających wspólny interfejs. Przydatny
jest wtedy, gdy zachowanie jakiegoś komponentu ma zmieniać się w zależności od tego, co uprzednio wydarzyło się w
programie.

Klasa początkowa, zwana **Kontekstem**, eksponuje pewne metody, jednak _konkretne zachowanie tych metod zależy od
wcześniejszych czynników_. W tym celu, zamiast implementować metody bezpośrednie w **Kontekście**, implementujemy
poszczególne zachowania w klasach **Stanów**, a następnie przypisujemy jeden ze **Stanów** do **Kontekstu** jako
"aktualny". **Kontekst** wywołuje konkretne implementacje z **aktualnego Stanu**, a to, który **Stan** jest aktualny,
może zmieniać się w zależności od pewnych wydarzeń (akcji użytkownika, wyniku obliczeń, uzyskania dostępu do bazy
danych itp.). Zmiany te nadzoruje albo **Kontekst**, albo same **Stany**, albo nawet aplikacja kliencka (wzorzec nie
narzuca
tego szczegółu implementacji, ale rekomendowane jest, by być konsekwentnym i nie rozrzucać tej odpowiedzialności po
całym kodzie bez dobrego powodu).

Wzorzec _State_ jest blisko związany z tzw. _State Machine_ i matematyczną koncepcją _automatów skończonych_, do tego
stopnia, że pojęcia te bywają traktowane jak synonimy. W ściślejszym znaczeniu _maszyna stanu_ to sposób implementacji
wzorca Stanu (obejmuje Stany, Kontekst, jak i sposób przechodzenia między Stanami), natomiast _automat skończony_ to
matematyczny opis _maszyny stanu_.

Maszyna Stanu przedstawiona w przykładzie jest "sklepem internetowym" i rozróżnia 3 stany "weryfikacji danych
użytkownika". Działa w następujący sposób:

1. Zaczyna w `NotAuthenticatedState`:
   1. metody `signOut` oraz `checkout` wyświetlają komunikat o konieczności zalogowania
   2. po udanym zalogowaniu `signIn`:
   3. jeśli podany został adres, przechodzi do `ReadyForCheckoutState`
   4. jeśli adresu brakuje, przechodzi do `NeedsShippingAddressState`
2. W `NeedsShippingAddressState`:
   1. jeśli zostanie podany adres, przechodzi do `ReadyForCheckoutState`
   2. metoda `signOut` przechodzi do `NotAuthenticatedState`
   3. nie pozwala ponownie się zalogować `signIn`
   4. metoda `checkout` wyświetla komunikat o konieczności podania adresu
3. W `ReadyForCheckoutState`:
   1. metoda `signOut` przechodzi do `NotAuthenticatedState`
   2. metoda `checkout` przetwarza zamówienie
   3. nie pozwala ponownie się zalogować `signIn`

### Zalety

- pozwala usunąć z kodu piętrzące się `if-else` i `switch`, zastępując je odwołaniem do metody aktualnego Stanu
- wspiera regułę **Single Responsibility**, wyodrębniając kod z Kontekstu do Stanów
- wspiera regułę **Open/Closed**, ponieważ znacznie ułatwia późniejsze dodawanie nowych zachowań, jak również pozwala
  zamknąć pewne kluczowe fragmenty kodu w klasie Kontekstu (która powinna być modyfikowana rzadko)
- pomaga zredukować duplikację kodu, w sytuacji, gdy kilka różnych zachowań jest _podobnych_, ale nie identycznych (
  klasy Stanów mogą polegać na dziedziczeniu, by uniknąć duplikacji, podczas gdy w oryginalnej klasie jedynym
  praktycznym rozwiązaniem może być tworzenie metod pomocniczych, co może bardzo szybko zwiększyć objętość klasy)

### Wady

- początkowy wzrost komplikacji kodu, istotny zwłaszcza w przypadku _maszyn stanu_ o niewielkiej liczbie Stanów
- wzorzec ten jest stosunkowo łatwo "zepsuć", jeśli nie uważamy, by zaplanować przejścia między Stanami w logiczny i
  łatwy do zrozumienia sposób

### Ogólny schemat implementacji

`Kontekst`

- posiada jeden "aktualny" Stan, który określa jego zachowanie
- ma `setter` do ustawiania nowego Stanu
- wykorzystywany bezpośrednio przez kod kliencki
- w odpowiedzi na żądania kodu klienckiego wywołuje metodę "aktualnego" Stanu

`Stany`

- opisują pożądane zachowanie jakiejś metody (lub grupy metod) na danym etapie programu
- zwykle mają referencję do `kontekstu`: dzięki temu mogą uzyskać dostęp do jego pól i metod, jak również zarządzać
  zmianą "aktualnego" Stanu
- implementują wspólny interfejs, dzięki któremu `kontekst` może korzystać z nich wymiennie