package data_access_object.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

//Klasa reprezentująca "dane" które chcemy przechowywać w zewnętrznym źródle.
public class Album implements Serializable {
    private final String name;
    private final String artist;
    private final int year;
    private final Genre genre;
    private double rating;


    //Wbrew pozorom jest to zupełnie zwyczajny konstruktor. Poniższe adnotacje są wykorzystywane przez bibliotekę
    // Jackson, której używam do zamiany instancji tej klasy na wpisy w pliku JSON (Jackson jest bardzo często
    // stosowany, więc warto się z nim oswoić). Jeśli jednak komuś zależy wyłącznie na zrozumieniu wzorca, to może
    // zupełnie zignorować istnienie tych adnotacji.
    //
    //W każdym razie adnotacje te informują Jackson, w jaki sposób ma przypisywać pola JSONa do pól obiektu. W naszym
    // przypadku prościej się nie da, bo nazwy jednych i drugich pól są po prostu identyczne.
    @JsonCreator
    public Album(@JsonProperty("name") String name,
                 @JsonProperty("artist") String artist,
                 @JsonProperty("year") int year,
                 @JsonProperty("genre") Genre genre,
                 @JsonProperty("rating") double rating) {
        this.name = name;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public Genre getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return artist + " - "
                + name.toUpperCase()
                + " (" + year
                + ")";
    }
}
