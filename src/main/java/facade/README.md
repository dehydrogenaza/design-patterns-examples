# Facade Pattern

Fasada to jeden z najczęstszych wzorców projektowych, stosowany czasem niemal "bezwiednie". Polega na stworzeniu klasy
upraszczającej dostęp do skomplikowanego podsystemu, którym może być np. _framework_ czy _biblioteka_.

Gdy pracujemy z _zewnętrznymi frameworkami/bibliotekami_, bardzo rzadko jest tak, że potrzebujemy wszystkich możliwości
oferowanych przez te narzędzia. Ponadto, chcielibyśmy, by nasz kod był jak najbardziej "odporny" na zmiany w kodzie
zewnętrznym. **Fasada** pozwala zrealizować oba te cele. Jest to klasa (a czasami, przy naprawdę dużych frameworkach,
cała rodzina klas), która "obsługuje" logikę zewnętrznego narzędzia i jednocześnie eksponuje uproszczony (przeważnie
okrojony) zestaw metod do stosowania w naszym programie.

### Zalety

- upraszcza późniejszą pracę z kodem
- wspiera regułę **DRY**: skomplikowany _kod zewnętrzny_ wywołujemy tylko raz
- wspiera regułę **Open/Closed**: pośredniczy między kodem naszym a _zewnętrznym_, uniezależniając je od siebie

### Wady

- początkowa komplikacja kodu
- w rzadkich sytuacjach może utrudniać rozszerzanie kodu, jeśli ukryliśmy pewne szczegóły konfiguracyjne "za fasadą", a
  później chcemy, by nasz program jednak wykorzystywał ich szerszy zakres (tzn. szerzej korzystał z _zewnętrznego_ kodu)
- ma tendencję do rozrastania się w nieskończoność (aby tego uniknąć, w dużych projektach jedna biblioteka może mieć
  wiele fasad, a poszczególne komponenty korzystają tylko z jednej z nich)

### Ogólny schemat implementacji

`kod zewnętrzny`:

- skomplikowany kod, zależny bądź niezależny od nas
- przydatny w obecnym projekcie/komponencie, ale wykraczający poza jego zakres
- nie powinien być bezpośrednio wywoływany przez _kod kliencki_ (czyli nasz projekt/komponent)

```
public ComplexFoo {
  public void method1() { .. }
  public void method2() { .. }
  public void method3() { .. }
  public void method4() { .. }
  public void method5() { .. }
}
```

`Facade`:

- eksponuje tylko metody bezpośrednio potrzebne w projekcie/komponencie
- korzysta z `kodu zewnętrznego` by realizować wywoływane metody
- może stanowić grupę różnych Fasad

```
public FooFacade {
  private ComplexFoo foo = new ComplexFoo( .. );

  public void usefulFoo() {
    foo.method1();
  }
  
  public void usefulBar() {
    foo.method5();
    foo.method3();
  }
}
```