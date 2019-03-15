import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

// vashiot kod ovde
class Movie{
    private String title;
    private List<Integer> ratings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = new ArrayList<>();
        for (int rating : ratings) {
            this.ratings.add(rating);
        }
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getRatings() {
        return ratings;
    }
    public double averageRating(){
        return ratings.stream().mapToInt(Integer::intValue).average().getAsDouble();
    }
    public double getCoefofMovie(int i){
        return (averageRating() * ratings.size()) / i;
    }
    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings",
                title, averageRating(), ratings.size());
    }

}

class MoviesList{
    List<Movie> movies;

    public MoviesList() {
        movies = new ArrayList<>();
    }
    public void addMovie(String title, int[] ratings){
        movies.add(new Movie(title, ratings));
    }
    public List<Movie> top10ByAvgRating(){
        return movies.stream()
                .sorted(Comparator.comparing(Movie::averageRating).reversed()
                .thenComparing(Movie::getTitle))
                .limit(10).collect(Collectors.toList());
    }
    public List<Movie> top10ByRatingCoef(){
        //int maxRating = movies.stream().mapToInt(i -> i.getRatings().size()).max().getAsInt();
        int maxRating = (int) movies.stream().flatMap(movie -> movie.getRatings().stream()).count();
        return movies.stream().sorted((m1, m2) ->
                Double.compare(m1.getCoefofMovie(maxRating), m2.getCoefofMovie(maxRating)) == 0 ?
                m1.getTitle().compareTo(m2.getTitle()) :
                Double.compare(m2.getCoefofMovie(maxRating), m1.getCoefofMovie(maxRating)))
                .limit(10).collect(Collectors.toList());
    }
}