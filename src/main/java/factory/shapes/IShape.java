package factory.shapes;

//wspólna cecha naszych "kształtów", dzięki wspólnemu interfejsowi możemy z nich korzystać zamiennie - nie interesuje
// nas, czy obiekt jest np. typu Triangle, tylko czy implementuje np. metodę .displayName
//
//mogłaby to być też klasa (np. abstrakcyjna), po której dziedziczyłyby kształty
public interface IShape {
    void displayName();
    String getColor();
}
