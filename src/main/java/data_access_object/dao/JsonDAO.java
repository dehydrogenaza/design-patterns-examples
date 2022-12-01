package data_access_object.dao;

import data_access_object.data.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//Nieco bardziej "realistyczna" implementacja DAO do źródła danych, którym jest plik JSON (format, który służy
// głównie do komunikacji między serwerem a klientem, ale może być z powodzeniem stosowany jako wygodny i czytelny
// format przechowywania niezbyt obszernych danych).
//
//W tym pliku skupiam się na samym DAO, czyli łączniku między "aplikacją" a "warstwą źródła danych". W klasie
// JsonSource jest wydzielona bardzo prymitywna implementacja źródła danych jako pliku JSON (ale sama w sobie nie
// jest ona częścią wzorca, więc można ją zignorować).
//
//BTW wynikowy plik JSON możecie sobie podejrzeć w katalogu "resources":
//      ŚCIEŻKA_PROJEKTU\src\main\resources\album.json
//(plik powstanie dopiero po pierwszym użyciu JsonDAO; aby go ładnie sformatować można użyć skrótu CTRL + ALT + L)
public class JsonDAO implements IAlbumDataAccess {

    //Referencja do warstwy źródła danych.
    private final JsonSource source;

    //W tym przypadku po prostu "tworzę na nowo" źródło danych, gdy chcę go użyć, co zapewne nie ma wielkiego sensu w
    // praktyce. W prawdziwym projekcie zapewne byłaby tu jakaś logika inicjująca komunikację z warstwą danych.
    public JsonDAO() {
        //ObjectMapper to klasa z biblioteki Jackson, której użyłem do serializacji i deserializacji obiektów. Nie
        // trzeba się nią przejmować, nothing to see here ;-)
        this.source = new JsonSource(new ObjectMapper());
    }

    //* * * * *
    //Poniższe metody "tłumaczą" interfejs IAlbumDataAccess na metody źródła, u nas obsługiwanego klasą JsonSource.
    //* * * * *
    //Tak naprawdę, poniższe implementacje są bardzo prymitywne. Jeśli się w nie wczytacie, to zauważycie, że każda z
    // nich pobiera WSZYSTKIE dane ze źródła i manipuluje nimi lokalnie. Gdyby to miał być prawdziwy program, nie ma
    // szans, by takie rozwiązanie przeszło QA. Zamiast tego musiałbym rozbudować JsonSource tak, by pozwalało
    // pobierać tylko potrzebną część danych, jak również dodawać nowe elementy do istniejących danych (obecnie np.
    // metoda .create() odczytuje WSZYSTKIE dane, dodaje do nich, po czym tworzy cały plik OD NOWA, co jest...
    // niezbyt wydajne).

    @Override
    public void create(Album album) {
        List<Album> albums = source.deserializeAlbums();
        albums.add(album);
        source.serializeAlbums(albums);
    }

    @Override
    public Set<Album> read(Predicate<Album> predicate) {
        List<Album> albums = source.deserializeAlbums();
        return albums.stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Album> read(Genre genre) {
        return read(album -> album.getGenre() == genre);
    }

    @Override
    public void update(Predicate<Album> predicate, Consumer<Album> action) {
        List<Album> albums = source.deserializeAlbums();
        albums.stream()
                .filter(predicate)
                .forEach(action);
        source.serializeAlbums(albums);
    }

    @Override
    public void delete(Predicate<Album> predicate) {
        List<Album> albums = source.deserializeAlbums();
        albums.removeIf(predicate);
        source.serializeAlbums(albums);
    }
}
