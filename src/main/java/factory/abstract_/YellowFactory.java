package factory.abstract_;

import factory.shapes.*;

//jedna z wyspecjalizowanych fabryk
//łączy je wspólny interfejs IShapeFactory
public class YellowFactory implements IShapeFactory {

    @Override
    public IShape create(Type type) {
        return switch (type) {
            case TRIANGLE -> new Triangle("YELLOW");
            case RECTANGLE -> new Rectangle("YELLOW");
            case DISC -> new Disc("YELLOW");
        };
    }
}