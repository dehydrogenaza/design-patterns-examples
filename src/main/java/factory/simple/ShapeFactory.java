package factory.simple;

import factory.shapes.*;

//"prostsza" implementacja, wystarczająca do większości zastosowań, ale potencjalnie mniej elastyczna
//tę wersję wzorca nazywa się Factory Method albo czasami Static Factory
public class ShapeFactory {

    //zależnie od podanego typu (który jest enumem), wywołuje odpowiedni konstruktor
    //parametr "color" przekazywany jest z metody do konstruktora
    //
    //szczegół: ponieważ używamy enuma jako typu, nie musimy podawać opcji "default" - sam enum nam gwarantuje, że
    // metoda nie otrzyma wartości innej niż w nim wyszczególnione
    public static IShape create(Type type, String color) {
        return switch (type) {
            case TRIANGLE -> new Triangle(color);
            case RECTANGLE -> new Rectangle(color);
            case DISC -> new Disc(color);
        };
    }
}
