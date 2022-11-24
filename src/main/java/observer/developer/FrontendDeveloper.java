package observer.developer;

import observer.dispatch.IJobsBoard;

public class FrontendDeveloper extends Developer {

    public FrontendDeveloper(IJobsBoard jobsBoard, String name, Level level) {
        super(jobsBoard, name, level);

        skills.add("JavaScript");
        if (Math.random() > 0.5) {
            skills.add("TypeScript");
        }
    }

    @Override
    public void considerJob(Level level, String... requirements) {
        System.out.print(" * " + name + " (frontend, " + this.level + ", umie: " + skills + ") - ");
        if (this.level == level && checkRequirements(requirements)) {
            System.out.print("ZAINTERESOWANY OFERTĄ\n");
        } else {
            System.out.print("to nie dla mnie\n");
        }
    }

}
