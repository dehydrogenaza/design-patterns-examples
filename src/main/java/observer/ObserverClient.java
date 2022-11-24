package observer;

import observer.developer.*;
import observer.dispatch.*;

import java.util.Random;
import java.util.Scanner;

//Klasa testowa, nie jest częścią wzorca.
public class ObserverClient {
    public static void main(String[] args) {

        //"Nadawca".
        IJobsBoard board = new ProgrammingJobsBoard();

        //"Odbiorcy" (obserwatorzy). Dziedziczą po Developr, więc mogą być przypisani do tej samej tablicy.
        Developer[] devs = new Developer[10];
        devs[0] = new BackendDeveloper(board, "Mateusz", Level.JUNIOR);
        devs[1] = new BackendDeveloper(board, "Rafał", Level.MID);
        devs[2] = new BackendDeveloper(board, "Emil", Level.MID);
        devs[3] = new BackendDeveloper(board, "Piotr", Level.SENIOR);
        devs[4] = new BackendDeveloper(board, "Sławomir", Level.SENIOR);

        devs[5] = new FrontendDeveloper(board, "Krystyna", Level.JUNIOR);
        devs[6] = new FrontendDeveloper(board, "Jarosław", Level.JUNIOR);
        devs[7] = new FrontendDeveloper(board, "Tadeusz", Level.MID);
        devs[8] = new FrontendDeveloper(board, "Anna", Level.MID);
        devs[9] = new FrontendDeveloper(board, "Gerwazy", Level.SENIOR);

        //Do obsługi interfejsu.
        Scanner scanner = new Scanner(System.in);
        Random rng = new Random();
        String[] techs = {"PHP", "C#", "MySQL", "JavaScript", "TypeScript"};

        //Pętla będzie działała, dopóki na liście subskrybentów "tablicy ogłoszeń" są jeszcze jacyś Developerzy.
        while (board.hasSubscribers()) {
            //Losujemy ofertę pracy: tutaj pozycję JUNIOR / MID / SENIOR.
            //Każdy Enum ma metodę .values(), która zwraca tablicę ze wszystkimi możliwymi wartościami enuma.
            int rngPosition = rng.nextInt(Level.values().length);
            Level level = Level.values()[rngPosition];

            //Tu losujemy jedną z technologii. Będzie jako "wymaganie" w ofercie.
            int rngTech = rng.nextInt(techs.length);
            String tech = techs[rngTech];

            System.out.println("SUPER OFERTA dla: " + level + ", wymagane: " + tech);

            //Ta metoda roześle "ofertę" o wylosowanych parametrach do wszystkich subskrybentów.
            board.notifySubscribers(level, tech);

            System.out.println("\n\t(ENTER by wylosować nową ofertę, IMIĘ żeby anulować subskrypcję)");
            String input = scanner.nextLine();
            //Jeśli podamy imię Developera, anulujemy jego subskrypcję metodą .unsubscribe interfejsu IJobsBoard.
            for (Developer dev : devs) {
                if (dev.getName().equalsIgnoreCase(input)) {
                    board.unsubscribe(dev);
                }
            }
        }

        System.out.println("Wszyscy znaleźli pracę!");
    }
}
