package observer.developer;

import observer.dispatch.IJobsBoard;

import java.util.HashSet;
import java.util.Set;

//Klasa bazowa Developerów.
//Implementacja "ProgrammingJobsBoard" będzie przechowywała referencje właśnie typu Developer, uzyskując dostęp do
// jego metod (a zwłaszcza 'considerJob', będącej faktycznym celem wywołania). Można by też użyć interfejsu -
// np. uznać, że wszyscy subskrybenci implementują ISubscriber, definiujący metodę 'considerJob', a w
// "ProgrammingJobsBoard" przechowywać referencje typu ISubscriber.
public abstract class Developer {
    //W przypadku Observera, w odróżnieniu od Mediatora, komponenty "odbierające" nie muszą mieć referencji do
    // "nadawcy", ale oczywiście mogą (zresztą, wzorce te często się przeplatają).
    //protected final IJobsBoard jobsBoard;
    protected final String name;
    protected final Level level;

    //*I have a very particular set of skills...*
    protected final Set<String> skills = new HashSet<>();

    //Oprócz standardowego ustawienia pól, w konstruktorze subskrybujemy także do przekazanego jobsBoard.
    //"Nadawca" MUSI mieć referencję do tego Developera, więc przekazujemy mu 'this'.
    public Developer(IJobsBoard jobsBoard, String name, Level level) {
        this.name = name;
        this.level = level;

        //this.jobsBoard = jobsBoard.subscribe(this);
        jobsBoard.subscribe(this);
    }

    public String getName() {
        return name;
    }

    //Pomocnicza funkcja sprawdzająca, czy ten Developer zna technologie wymagane w ogłoszeniu.
    protected boolean checkRequirements(String... requirements) {
        for (String req : requirements) {
            if (!skills.contains(req)) return false;
        }
        return true;
    }

    //Ta metoda stanowi sposób "odbioru" wiadomości od IJobsBoard, tzn. IJobsBoard (a właściwie konkretna
    // implementacja tego interfejsu) będzie właśnie ją wywoływać.
    //Każda klasa dziedzicząca musi zaimplementować swój własny sposób na "obsługę" żądania.
    public abstract void considerJob(Level level, String... requirements);

}
