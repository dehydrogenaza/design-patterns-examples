package factory.abstract_;

import factory.shapes.*;

//wyższy poziom abstrakcji fabryki (tzw. Abstract Factory)
//dzięki wspólnemu interfejsowi, będziemy mogli korzystać z różnych "wersji" fabryki bez zastanawiania się, jakiego
// jest typu (typu fabryki możemy np. pobrać od użytkownika albo z bazy danych)
public interface IShapeFactory {
    IShape create(Type type);
}
