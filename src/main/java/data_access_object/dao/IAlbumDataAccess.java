package data_access_object.dao;

import data_access_object.data.*;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;


//Interfejs używany przez aplikację kliencką do obsługi dowolnego źródła danych, implementujący metody CRUD.
public interface IAlbumDataAccess {
    //Dodawanie nowego wpisu do źródła danych.
    void create(Album album);

    //Pobieranie danych spełniających kryteria predykatu (czyli funkcji ewaluującej true/false dla każdego
    // indywidualnego obiektu).
    Set<Album> read(Predicate<Album> predicate);

    //Pomocnicza metoda pobierania danych o z góry ustalonym kryterium (tutaj: wszystkie albumy podanego gatunku).
    //Możemy definiować dowolną liczbę takich pomocniczych metod dla późniejszej wygody stosowania, jednak trzeba
    // pamiętać, że utrudnia to późniejszą implementację tego interfejsu, więc nie jest "darmowe".
    Set<Album> read(Genre genre);

    //W naszej wersji, .update() wykonuje pewną metodę 'action' na wszystkich danych spełniających 'predicate'.
    //Metoda .update() w systemie CRUD zwykle ma jakiś sposób wybierania obiektu (u nas jest to predykat, ale mogłoby
    // być np. unikalne ID) i w jakiś sposób określa zmianę lub zastąpienie tego obiektu (u nas — zmianę, ale dobrą
    // strategią jest też podawanie zupełnie nowego, zastępczego obiektu, jeśli zależy nam, by wszystkie pola "klasy
    // danych" były finalne.
    void update(Predicate<Album> predicate, Consumer<Album> action);

    //Usuwanie danych spełniających predykat.
    void delete(Predicate<Album> predicate);
}
