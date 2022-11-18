package factory;

import factory.abstract_.*;
import factory.shapes.*;
import factory.simple.ShapeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//do testowania, samo w sobie nie jest częścią Patterna
public class FactoryClient {

    public static void main(String[] args) {
        System.out.println("\nLOSOWE KSZTAŁTY STWORZONE PROSTĄ FABRYKĄ");
        System.out.println("\t>w tej implementacji, każdy obiekt ma z góry określony kolor (np. prostokąty są zawsze " +
                "niebieskie), który przekazywany jest jako parametr do fabryki");
        List<IShape> listOfShapesWithFixedColors = generateRandomShapesWithStaticFactory(10);
        displayList(listOfShapesWithFixedColors);


        System.out.println("\nLOSOWE KSZTAŁTY STWORZONE FABRYKĄ ABSTRAKCYJNĄ");
        System.out.println("\t>możemy wybrać fabrykę określonego koloru, a każda z nich może tworzyć każdy rodzaj " +
                "kształtu");

        System.out.println("Niebieskie:");
        List<IShape> listOfBlueShapes = generateRandomShapesWithAbstractFactory(5, "BLUE");
        displayList(listOfBlueShapes);

        System.out.println("Czerwone:");
        List<IShape> listOfRedShapes = generateRandomShapesWithAbstractFactory(5, "RED");
        displayList(listOfRedShapes);

        System.out.println("Żółte:");
        List<IShape> listOfYellowShapes = generateRandomShapesWithAbstractFactory(5, "YELLOW");
        displayList(listOfYellowShapes);
    }

    //tworzymy listę która zawiera losowe kształty różnych typów; możemy je umieścić razem ponieważ wszystkie
    // implementują IShape (mogłyby też dziedziczyć po wspólnej klasie np. abstrakcyjnej - zależy jakiej
    // funkcjonalności oczekujemy)
    private static List<IShape> generateRandomShapesWithStaticFactory(int amount) {
        List<IShape> listOfShapes = new ArrayList<>();
        Random rng = new Random();

        for (int i = 0; i < amount; i++) {
            //Szczegół implementacyjny; stworzyłem 3 typy Shape więc losuję liczby 0, 1 lub 2 i przypisuję im typ.
            //W produkcyjnym kodzie sprawdzałbym pewnie liczbę wartości enuma Type (z pomocą metody .values) i używał
            // jej jako parametru do losowania, ale nie chciałem komplikować
            int randomType = rng.nextInt(3);

            //ShapeFactory ma statyczną metodę create, która zwraca obiekt zadanego typu.
            //Wartość "color" przekazywana jest w tej wersji jako parametr do konstruktora
            IShape newShape = switch (randomType) {
                case 0 -> ShapeFactory.create(Type.RECTANGLE, "BLUE");
                case 1 -> ShapeFactory.create(Type.TRIANGLE, "RED");
                case 2 -> ShapeFactory.create(Type.DISC, "YELLOW");
                default -> throw new UnsupportedOperationException("?? Coś się pochrzaniło z losowaniem / zły typ ??");
            };

            listOfShapes.add(newShape);
        }

        return listOfShapes;
    }

    //OCH NIE DUPLIKACJA KODU xD
    //spokojnie, to tylko do testów ;)
    //oczywiście gdyby takie podobne metody miały istnieć w kodzie produkcyjnym, trzeba by wyodrębnić z nich część
    // wspólną / zwiększyć poziom abstrakcji
    private static List<IShape> generateRandomShapesWithAbstractFactory(int amount, String factoryType) {
        List<IShape> listOfShapes = new ArrayList<>();
        Random rng = new Random();

        //tutaj mamy wyższą abstrakcję Factory: 3 różne fabryki (po 1 dla koloru), możemy ich używać w ten sam
        // sposób, ponieważ interesuje nas tylko to, że implementują ten sam interfejs
        IShapeFactory factory = switch (factoryType.toUpperCase()) {
            case "BLUE" -> new BlueFactory();
            case "RED" -> new RedFactory();
            case "YELLOW" -> new YellowFactory();
            default -> throw new UnsupportedOperationException("Podano nieistniejący typ fabryki.");
        };

        for (int i = 0; i < amount; i++) {
            int randomType = rng.nextInt(3);

            //w tej implementacji nie przekazujemy "color" jako parametru, ponieważ mamy wyspecjalizowane fabryki
            // które tworzą obiekty określonego koloru (każda z tych fabryk może tworzyć każdą implementację IShape,
            // ale tylko w jednym kolorze)
            IShape newShape = switch (randomType) {
                case 0 -> factory.create(Type.RECTANGLE);
                case 1 -> factory.create(Type.TRIANGLE);
                default -> factory.create(Type.DISC);
            };

            listOfShapes.add(newShape);
        }

        return listOfShapes;
    }

    //pomocnicza metoda do wyświetlenia wygenerowanych kształtów, jak widać korzysta z metod interfejsu IShape, dzięki
    // czemu NIE MUSI WIEDZIEĆ JAKI DOKŁADNIE OBIEKT ZNAJDUJE SIĘ NA LIŚCIE
    // co w sumie było celem użycia tego wzorca ;)
    private static void displayList(List<IShape> shapes) {
        for (IShape shape : shapes) {
            shape.displayName();
            System.out.println(" I'm " + shape.getColor() + ".");
        }
    }
}
