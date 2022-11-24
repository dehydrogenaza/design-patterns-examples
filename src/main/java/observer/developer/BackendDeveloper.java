package observer.developer;

import observer.dispatch.IJobsBoard;

//W przykładzie różnice między klasami dziedziczącymi po Developer są minimalne i sprowadzają się do "hard-coded"
// zestawu umiejętności i trochę innej wiadomości w odpowiedzi na ogłoszenie.
//
//Oczywiście, gdybyśmy próbowali zamodelować taki system w prawdziwym projekcie, to pewnie zależałoby nam na
// przekazywaniu skilli w konstruktorze, a różnice między klasami albo byłyby większe, albo byłaby to po prostu jedna
// klasa.
// W przykładzie celowo nie chciałem pisać "nadawcy" pod konkretną implementację, tylko pod klasę abstrakcyjną
// bądź interfejs.
public class BackendDeveloper extends Developer {

    public BackendDeveloper(IJobsBoard jobsBoard, String name, Level level) {
        super(jobsBoard, name, level);

        skills.add(Math.random() > 0.5 ? "C#" : "PHP");
        skills.add("MySQL");
    }

    @Override
    public void considerJob(Level level, String... requirements) {
        System.out.print(" * " + name + " (backend, " + this.level + ", umie: " + skills + ") - ");
        if (this.level == level && checkRequirements(requirements)) {
            System.out.print("ZAINTERESOWANY OFERTĄ\n");
        } else {
            System.out.print("to nie dla mnie\n");
        }
    }
}