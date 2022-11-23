# Mediator Pattern

Wzorzec Mediatora pomaga zrealizować komunikację między różnymi elementami programu. Przydatny jest wtedy, gdy
mamy wiele różnych obiektów, które muszą przekazywać między sobą dane albo wywoływać metody. Jeśli tych obiektów jest
wiele, a każdy z nich ma mieć możliwość komunikacji z każdym innym, to bardzo szybko robi nam się niemożliwa do
ogarnięcia, poplątana sieć zależności, w rodzaju:

```
class ComponentA {
    ComponentB b;
    ComponentC c;
    ComponentD d1, d2;
    List<ComponentE> es;
    
    boolean sendRequestToB() {
        return b.handleRequestFromA();
    }
    
    boolean handleRequestFromB() {
        return true;
    }
    
    //itd.
}
```

```
class ComponentB {
    ComponentA a;
    ComponentC c;
    ComponentD d1, d2;
    List<ComponentE> es;
    
    boolean sendRequestToA() {
        return a.handleRequestFromB();
    }
    
    boolean handleRequestFromA() {
        return false;
    }
    
    //itd.
}
```

`//kolejne komponenty`

Im więcej elementów, tym bardziej skomplikowana sieć, a sprawa dodatkowo się komplikuje, jeśli komponenty składowe są
troszkę bardziej złożone i np. implementują jakieś interfejsy.

Mediator to klasa, która pośredniczy w komunikacji między komponentami. Zawiera metodę służącą do "odbierania"
żądań (wywoływaną przez "żądające" komponenty) oraz odniesienia do każdego komponentu, dzięki czemu może po
przetworzeniu
przekazać żądanie dalej (do jednego lub wielu składowych). Każdy komponent składowy zawiera z kolei odniesienie do
obiektu mediatora (dzięki czemu może wywoływać jego metodę "odbiorczą") oraz własną metodę "odbierania" żądań (
wywoływaną z kolei przez mediator).

```
class ComponentA {
    Mediator mediator;
    
    boolean sendRequest() {
        mediator.handleRequest(this);
    }
    
    boolean foo() { return false; }
    boolean bar() { return false; }
}
```

```
class ComponentB {
    Mediator mediator;
    
    boolean sendRequest() {
        mediator.handleRequest(this);
    }
    
    boolean bar() { return true; }
}
```

`//kolejne komponenty`

```
class Mediator {
    ComponentA a;
    ComponentB b;
    //...
    
    handleRequest(Type sender) {
        //invoke some method(s), depending on the sender
        //a.foo();
        //a.bar();
        //b.bar();
    }
}
```

Bardzo ważne jest, by komponenty _nie musiały nic o sobie wiedzieć_: mają tylko wysyłać wiadomości do mediatora, nigdy
bezpośrednio do innych komponentów. Dzięki temu można łatwo rozszerzać i modyfikować taki układ.

### Zalety

- zmniejsza ilość zależności między klasami
- przy dużych sieciach zależności może prowadzić do _mniejszej_ ilości kodu
- wspiera regułę **Single Responsibility**, wydzielając warstwę komunikacji z samych komponentów systemu
- wspiera regułę **Open/Close**, umożliwiając podmianę mediatorów bez wpływu na komponenty, a także znacznie ułatwiając
  rozszerzanie "sieci"

### Wady

- ma niebezpieczną tendencję do rozrastania się w nieskończoność i finalnie prowadzi do klasy o ogromnym stopniu
  złożoności (trzeba tego pilnować)
- jak w przypadku każdego wzorca, nie zawsze "opłaca się" go stosować, choć tutaj "narzut" jest stosunkowo mały w
  porównaniu z niektórymi wzorcami

### Ogólny schemat implementacji

`Mediator`:

- implementuje interfejs działania mediatora, np. `IMediator`, a nie stanowi sztywno ustawionego typu
- ma referencje do wszystkich komponentów
- implementuje metody do _rozsyłania_/_rozdzielania_ żądań

`Komponenty`:

- mogą być bardzo różne, ale implementują interfejs obsługi mediatora, np. `IComponent` (w przykładzie zamiast tego
  dziedziczą po jednej klasie)
- każdy ma swoją referencję do mediatora i nie ma referencji do pozostałych komponentów
- implementują metody do _wysyłania_ żądań do mediatora