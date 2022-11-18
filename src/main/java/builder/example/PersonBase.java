package builder.example;

import java.time.LocalDate;

//Dla zmniejszenia duplikacji kodu dwie wersje "Person" dziedziczą te same pola, które trzeba uzupełnić, oraz
// metodę .toString.
//
//Jak widać, pól jest sporo i wywołanie takiego konstruktora byłoby uciążliwe, a na dodatek mało elastyczne
// (wyobraźcie sobie, że musicie stworzyć konstruktory dla każdego możliwego wariantu podawanych danych, bo np. nie
// wszystkie są zawsze dostępne/potrzebne).
abstract public class PersonBase {
    protected int id;
    protected String description;
    protected LocalDate dateOfBirth;
    protected String[] firstNames;
    protected String[] lastNames;
    protected String mothersMaidenName;
    protected String title;
    protected String nationality;
    protected String[] citizenship;

    protected double heightInCMs;
    protected double weightInKGs;


    //metoda jest przydługa wyłącznie po to, by się ładnie wyświetlało :)
    //to nie jest część Builder Pattern
    @Override
    public String toString() {
        String txt = "ID " + id +
                ": " + description +
                "\n\tBORN:\t\t\t" + dateOfBirth +
                "\n\tFULL NAME:\t\t";

        if (firstNames == null && lastNames == null) {
            txt += "<unknown>";
        } else {
            if (title != null) {
                txt += title + " ";
            }
            if (firstNames != null) {
                txt += parseSubstrings(firstNames, ' ') + " ";
            }
            if (lastNames != null) {
                txt += parseSubstrings(lastNames, '-');
            }
        }

        if (mothersMaidenName != null) {
            txt += "\n\tOF MOTHER:\t\t" + mothersMaidenName;
        }

        txt += "\n\tNATIONALITY:\t";
        if (nationality != null && citizenship != null) {
            txt += nationality;
            txt += ", citizenship " + parseSubstrings(citizenship, '/');
        } else {
            txt += "<unknown>";
        }

        txt += "\n\tMEASUREMENTS:\t";
        if (heightInCMs != 0.0 && weightInKGs != 0.0) {
            txt += heightInCMs + " cm, ";
            txt += weightInKGs + " kg";
        } else {
            txt += "<unknown>";
        }

        return txt + "\n";
    }

    //pomocnicza metoda do wyświetlania
    //to nie jest część Builder Pattern
    private String parseSubstrings(String[] subs, char connector) {
        if (subs.length == 0) {
            return "";
        }

        StringBuilder parsed = new StringBuilder(subs[0]);
        for (int i = 1; i < subs.length; i++) {
            parsed.append(connector).append(subs[i]);
        }

        return parsed.toString();
    }
}
