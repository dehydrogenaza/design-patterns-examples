package factory.shapes;

public class Disc implements IShape{

    private final String color;

    public Disc(String color) {
        this.color = color;
    }

    @Override
    public void displayName() {
        System.out.print("I'm a DISC (sometimes known as a 'filled' CIRCLE).");
    }

    @Override
    public String getColor() {
        return color;
    }
}
