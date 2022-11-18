package factory.shapes;

public class Triangle implements IShape {

    private final String color;

    public Triangle(String color) {
        this.color = color;
    }

    @Override
    public void displayName() {
        System.out.print("I'm a TRIANGLE.");
    }

    @Override
    public String getColor() {
        return color;
    }
}
