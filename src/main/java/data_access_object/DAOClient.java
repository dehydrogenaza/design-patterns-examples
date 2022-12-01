package data_access_object;

import data_access_object.dao.*;
import data_access_object.data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//Klasa do testów. Nie jest częścią wzorca.
//
//W poniższym przykładzie operujemy na źródle danych zawierającym serię albumów muzycznych. Źródło może przyjmować
// dwie formy: albo być HashSetem (co nie jest typową reprezentacją, a jedynie uproszczeniem na potrzeby przykładu),
// albo znajdować się w pliku JSON (co już jest spotykane — choć zdecydowanie najczęstszymi źródłami danych są różne
// relacyjne bazy danych, zbyt skomplikowane, by je tu wprowadzać).
//
//Nasza klasa testowa nie musi nic wiedzieć na temat źródła danych. Posługuje się interfejsem IAlbumDataAccess, by
// pobierać interesujące ją informacje (czyli zestawy albumów), a poszczególne implementacje tego interfejsu dbają o
// to, by komunikować się z konkretnymi rodzajami źródeł w "zrozumiały" dla nich sposób.
//
//Aby zmienić źródło, po prostu zakomentujcie/odkomentujcie odpowiednią linijkę IAlbumDataAccess dao = new XXXXXXX();

public class DAOClient {

    public static void main(String[] args) {
        IAlbumDataAccess dao = new HashSetDAO(); //wykorzysta HashSet do symulacji warstwy źródła danych
                                                //(wymaga zakomentowania kolejnej linijki)
        //IAlbumDataAccess dao = new JsonDAO(); //wykorzysta plik JSON do symulacji warstwy źródła danych
                                                //(wymaga zakomentowania poprzedniej linijki)
                                                //(jeśli nie działa, prawdopodobnie problem jest z systemem plików)
                                                //(nie robiłem zbyt wyszukanej obsługi nietypowych przypadków)

        System.out.println("\nUŻYWASZ TERAZ: " + dao.getClass().getSimpleName());
        System.out.println("======================");

//        Zapisywanie albumów do bazy danych (z naszego punktu widzenia działa dokładnie tak samo, niezależnie od
//        tego, którą reprezentację warstwy źródła danych wybierzemy)
        for (Album album : makeSomeAlbums()) {
            dao.create(album);
        }

        System.out.println("\nPOBIERZ WSZYSTKIE ALBUMY:");
        //predykat zwracający 'true' dla wszystkich wartości, gdybyśmy często chcieli pobierać "wszystkie" dane ze
        // źródła, moglibyśmy rozważyć wprowadzenie dodatkowej metody np. readAll() do interfejsu IAlbumDataAccess
        Set<Album> allAlbums = dao.read(album -> true);
        displayAlbums(allAlbums);

        System.out.println("\nPOBIERZ ALBUMY MUZYKI ALTERNATYWNEJ (bez modyfikacji bazy danych):");
        Set<Album> nonMetalAlbums = dao.read(Genre.ALTERNATIVE);
        displayAlbums(nonMetalAlbums);

        System.out.println("\nUSUŃ Z BAZY WSZYSTKIE ALBUMY Z OCENĄ < 8.5");
        dao.delete(album -> album.getRating() < 8.5); //usuwanie
        Set<Album> greatAlbums = dao.read(album -> true); //pobieramy wszystko ze źródła, już po usunięciu części danych
        displayAlbums(greatAlbums);

        System.out.println("\nOBNIŻ OCENĘ WSZYSTKICH ALBUMÓW FLOYDÓW O 7 PKT");
        dao.update(album -> "Pink Floyd".equalsIgnoreCase(album.getArtist()),
                album -> album.setRating(album.getRating() - 7)); //modyfikacja
        Set<Album> updatedAlbums = dao.read(album -> true); //pobieramy wszystko, już po modyfikacjach
        displayAlbums(updatedAlbums);

        System.out.println("\nPOBIERZ ALBUMY WYDANE PRZED 2000 R. (bez modyfikacji bazy danych):");
        Set<Album> oldAlbums = dao.read(album -> album.getYear() < 2000); //pobieramy zgodnie z kryterium
        //(baza danych pozostaje bez zmian, tutaj wyświetlamy naszą lokalną kopię danych)
        displayAlbums(oldAlbums);

    }

    //Pomocnicza metoda do ładniejszego wyświetlania wyników zwracanych ze źródła danych.
    private static void displayAlbums(Set<Album> albums) {
        for (Album album : albums) {
            System.out.println("\t- " + album + " - ocena: " + album.getRating());
        }
    }

    //Pomocnicza metoda tworząca przykładowe dane do testów.
    private static List<Album> makeSomeAlbums() {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album("Meddle", "Pink Floyd", 1971, Genre.ROCK, 7.0));
        albums.add(new Album("The Dark Side of the Moon", "Pink Floyd", 1973, Genre.ROCK, 10.0));
        albums.add(new Album("Wish You Were Here", "Pink Floyd", 1975, Genre.ROCK, 9.0));
        albums.add(new Album("Judgment", "Anathema", 1999, Genre.ROCK, 8.5));
        albums.add(new Album("Hvis lyset tar oss", "Burzum", 1994, Genre.METAL, 10.0));
        albums.add(new Album("Filosofem", "Burzum", 1996, Genre.METAL, 8.5));
        albums.add(new Album("Omnio", "In the woods...", 1997, Genre.METAL, 10.0));
        albums.add(new Album("The Mantle", "Agalloch", 2002, Genre.METAL, 10.0));
        albums.add(new Album("The White", "Agalloch", 2008, Genre.ROCK, 8.0));
        albums.add(new Album("Marrow of the Spirit", "Agalloch", 2010, Genre.METAL, 6.5));
        albums.add(new Album("Clearing the Path to Ascend", "Yob", 2014, Genre.METAL, 8.5));
        albums.add(new Album("Salvation", "Cult of Luna", 2004, Genre.METAL, 7.5));
        albums.add(new Album("Lateralus", "Tool", 2001, Genre.METAL, 9.5));
        albums.add(new Album("10,000 Days", "Tool", 2006, Genre.METAL, 7.0));
        albums.add(new Album("The Apostasy", "Behemoth", 2007, Genre.METAL, 5.0));
        albums.add(new Album("Fever Ray", "Karin Dreijer", 2009, Genre.ALTERNATIVE, 10.0));
        albums.add(new Album("Plunge", "Karin Dreijer", 2017, Genre.ALTERNATIVE, 4.5));
        albums.add(new Album("Dummy", "Portishead", 1994, Genre.ALTERNATIVE, 6.5));
        albums.add(new Album("Dead Magic", "Anna von Hausswolff", 2018, Genre.ALTERNATIVE, 9.5));
        albums.add(new Album("Some Heavy Ocean", "Emma Ruth Rundle", 2014, Genre.ALTERNATIVE, 7.0));
        albums.add(new Album("Anastasis", "Dead Can Dance", 2012, Genre.ALTERNATIVE, 8.0));
        albums.add(new Album("Untrue", "Burial", 2007, Genre.ALTERNATIVE, 9.5));
        albums.add(new Album("Bitches Brew", "Miles Davis", 1970, Genre.JAZZ, 9.0));
        albums.add(new Album("A Love Supreme", "John Coltrane", 1965, Genre.JAZZ, 8.5));
        albums.add(new Album("The Way Up", "Pat Metheny", 2005, Genre.JAZZ, 6.0));

        return albums;
    }


}
