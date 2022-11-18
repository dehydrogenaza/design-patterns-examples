package factory.abstract_;

import factory.shapes.*;

//jedna z wyspecjalizowanych fabryk
//łączy je wspólny interfejs IShapeFactory
public class RedFactory implements IShapeFactory {

    @Override
    public IShape create(Type type) {
        return switch (type) {
            case TRIANGLE -> new Triangle("RED");
            case RECTANGLE -> new Rectangle("RED");
            case DISC -> new Disc("RED");
        };
    }
}
