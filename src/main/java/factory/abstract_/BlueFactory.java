package factory.abstract_;

import factory.shapes.*;

//jedna z wyspecjalizowanych fabryk
//łączy je wspólny interfejs IShapeFactory
public class BlueFactory implements IShapeFactory {

    @Override
    public IShape create(Type type) {
        return switch (type) {
            case TRIANGLE -> new Triangle("BLUE");
            case RECTANGLE -> new Rectangle("BLUE");
            case DISC -> new Disc("BLUE");
        };
    }
}