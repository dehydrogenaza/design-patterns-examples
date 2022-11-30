package facade.kitchen_library;

import java.util.List;

//Element skomplikowanej "zewnętrznej biblioteki", zawierający detale, które mogłyby być potrzebne niektórym jej
// użytkownikom. Naszemu programowi na nich nie zależy — ukrywamy je za Fasadą.
public class Oven {
    private static int temp = 0;
    private static boolean on = false;

    public static void preheat(int targetTemp) {
        if (!on) throw new UnsupportedOperationException("Najpierw należy włączyć piekarnik");

        System.out.print("Nagrzewanie piekarnika.");
        while (temp < targetTemp) {
            System.out.print('.');
            temp += 10;
        }
        System.out.println("\nPiekarnik nagrzany.");
    }

    public static boolean bake(List<Ingredient> ingredients, int requiredTemp) {
        System.out.println("W piekarniku: " + ingredients);
        return temp >= requiredTemp;
    }

    public static void setOn(boolean on) {
        if (on) {
            System.out.println("Piekarnik włączony.");
        } else {
            System.out.println("Piekarnik wyłączony.");
        }
        Oven.on = on;
    }
}
