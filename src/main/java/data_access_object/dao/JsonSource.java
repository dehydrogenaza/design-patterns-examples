package data_access_object.dao;

import data_access_object.data.Album;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//Pomocnicza, bardzo uproszczona klasa ukrywająca szczegóły serializacji i deserializacji obiektów w pliku JSON.
//Wykorzystuje popularną bibliotekę Jackson, z którą prawdopodobnie każdy z nas będzie miał do czynienia.
public class JsonSource {

    //Na stałe określona ścieżka pliku.
    private final static File FILEPATH = new File("src/main/resources/album.json");

    //Obiekt (fasada!) robiący "prawie wszystko" w Jacksonie.
    private final ObjectMapper mapper;

    public JsonSource(ObjectMapper mapper) {
        this.mapper = mapper;

        if (FILEPATH.exists()) {
            //Jeśli istnieje plik z poprzedniego uruchomienia, usuwamy go. Oczywiście nie robilibyśmy tego w
            // "prawdziwym" programie.
            if (!FILEPATH.delete()) {
                throw new RuntimeException("Problem z dostępem do plików.");
            }
        }
    }

    //Zamienia JSON na obiekty Javy.
    public List<Album> deserializeAlbums() {
        List<Album> albums;
        if (FILEPATH.exists()) {
            try {
                albums = mapper.readValue(FILEPATH, new TypeReference<>() {});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            albums = new ArrayList<>();
        }
        return albums;
    }

    //Zamienia obiekty Javy na JSON.
    public void serializeAlbums(List<Album> albums) {
        try {
            mapper.writeValue(FILEPATH, albums);
        } catch (IOException e) {
            System.out.println("\nPrawdopodobnie brak dostępu do systemu plików.");
            System.out.println("Spróbuj uruchomić z podwyższonymi uprawnieniami.\n");

            throw new RuntimeException(e);
        }
    }
}
