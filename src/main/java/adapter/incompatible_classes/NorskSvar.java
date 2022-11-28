package adapter.incompatible_classes;

//Klasa niekompatybilna z naszą aktualną koncepcją interfejsu, z której chcielibyśmy jednak korzystać. Może to być
// pozostałość starej wersji programu albo np. zewnętrzna biblioteka, którą musimy jakoś podpiąć pod nasz program.
public class NorskSvar {
    protected final String navn; //Imię "rozmówcy".
    public NorskSvar(String navn) {
        this.navn = navn;
    }

    //Metoda udzielająca krótkiej odpowiedzi na podany String.
    public String svarKort(String melding) {
        return switch (melding) {
            case "hallo" -> "\thei hei!";
            case "takk" -> "\tbare hyggelig";
            case "værsågod" -> "\ttakk!";
            default -> "\tJeg forstår ikke";
        };
    }

    //Metoda udzielająca długiej odpowiedzi na podany String.
    public String svarLangt(String melding) {
        return switch (melding) {
            case "hallo" -> "\thei " + navn + ", hvordan går det?";
            case "takk" -> "\tbare hyggelig, " + navn;
            case "værsågod" -> "\ttakk skal du ha!";
            default -> "\tJeg forstår ikke, kan du gjenta?";
        };
    }
}
