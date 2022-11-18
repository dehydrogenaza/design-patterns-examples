package factory.shapes;

public class Rectangle implements IShape{
    private final String color;

    public Rectangle(String color) {
        this.color = color;
    }

    @Override
    public void displayName() {
        System.out.print("I'm a RECTANGLE.");
    }

    @Override
    public String getColor() {
        return color;
    }
}
