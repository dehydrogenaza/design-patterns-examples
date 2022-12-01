package data_access_object.dao;

import data_access_object.data.*;

import java.util.Set;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//Niezbyt "realistyczna" implementacja wykorzystująca HashSet jako "źródło danych". Podstawowym "problemem logicznym"
// takiej "persistence layer" jest to, że nie jest "persistent" ;-) Innymi słowy, dane znikają po zakończeniu
// programu, więc takie źródło jest przydatne tylko jako łatwy do napisania przykład.
public class HashSetDAO implements IAlbumDataAccess {

    //Wewnętrzna reprezentacja danych.
    private final Set<Album> items = new HashSet<>();

    //* * * * *
    //Poniższe metody "tłumaczą" interfejs IAlbumDataAccess na metody HashSetu.
    //* * * * *

    @Override
    public void create(Album album) {
        items.add(album);
    }

    @Override
    public Set<Album> read(Predicate<Album> predicate) {
        //Kolejno:
        //  - zamieniamy Set wszystkich elementów na Stream
        //  - zostawiamy tylko te elementy, które "spełniają predykat", czyli ewaluują jako 'true'
        //  - zamieniamy Stream na Set
        return items.stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Album> read(Genre genre) {
        //Odwołujemy się do "szerszej" metody, by nie duplikować kodu. Ta metoda jest tylko przykładem przydatnego
        // skrótu, "syntactic sugar", ułatwiającego korzystanie z DAO.
        //Równie dobrze w kodzie klienckim zamiast:
        //      dao.read(Genre.ROCK);
        //moglibyśmy napisać:
        //      dao.read(album -> album.getGenre() == Genre.ROCK);
        //(btw porównanie z użyciem == jest w powyższej linijce poprawne, ponieważ porównujemy Enumy)
        return read(album -> album.getGenre() == genre);
    }

    @Override
    public void update(Predicate<Album> predicate, Consumer<Album> action) {
        //Kolejno:
        //  - zamieniamy Set wszystkich elementów na Stream
        //  - zostawiamy tylko te elementy, które "spełniają predykat"
        //  - wykonujemy wskazaną metodę ('action') na każdym pozostałym elemencie
        items.stream()
                .filter(predicate)
                .forEach(action);
    }

    @Override
    public void delete(Predicate<Album> predicate) {
        //Metoda .removeIf() z interfejsu Collection usuwa wszystkie elementy kolekcji, które spełniają predykat
        items.removeIf(predicate);
    }
}
