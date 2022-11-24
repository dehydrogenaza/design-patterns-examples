package factory.shapes;

//pomocniczy enum który określa nam, jaki typy "IShape"ów są obsługiwane
//nie jest to konieczne w Factory Pattern, ale często stosowane
public enum Type {
    RECTANGLE,
    DISC,
    TRIANGLE
}
//w inny sposób (dla pokazania) zrealizowałem kolory: są to po prostu Stringi (a też mógłby być enum), minus jest
// taki że developer musi pamiętać jakie Stringi są dozwolone (IDE zwykle nie umie podpowiedzieć), a ponadto trzeba
// sprawdzać czy podany String na pewno jest obsługiwany (enum robi to za nas)
