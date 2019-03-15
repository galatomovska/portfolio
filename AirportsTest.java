import java.util.*;
import java.util.stream.IntStream;

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde
class Flight implements Comparable<Flight>{
    private String from;
    private String to;
    private int time;
    private int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int compareTo(Flight o) {
        return Comparator.comparing(Flight::getTo)
                .thenComparing(Flight::getTime).compare(this, o);
    }

    public String getTimeFormat(int time){
        int h = (time / 60)%24;
        int min = time % 60;
        return String.format("%02d:%02d", h, min);
    }
    public String getDurationString(int duration){
        int h = duration/60;
        int min = duration%60;
        if(Integer.parseInt(getTimeFormat(this.time).substring(0, 2)) >
                Integer.parseInt(getTimeFormat(this.time + this.duration).substring(0, 2)))
            return String.format("+1d %dh%02dm", h, min);
        return String.format("%dh%02dm", h, min);
    }
    @Override
    public String toString() {
        return from + "-" + to + " " + getTimeFormat(time) + "-" + getTimeFormat(time + duration) + " " + getDurationString(duration);
    }
}
class Airport{
    private String name;
    private String country;
    private String code;
    private int passengers;
    private Set<Flight> arrivalFlights;
    private Set<Flight> departureFlights;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        this.arrivalFlights = new TreeSet<>();
        this.departureFlights = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public int getPassengers() {
        return passengers;
    }

    public Set<Flight> getArrivalFlights() {
        return arrivalFlights;
    }

    public Set<Flight> getDepartureFlights() {
        return departureFlights;
    }
}
class Airports{
    private Map<String, Airport> airportMap;

    public Airports() {
        airportMap = new HashMap<>();
    }
    public void addAirport(String name, String country, String code, int passengers){
        airportMap.put(code, new Airport(name, country, code, passengers));
    }
    public void addFlights(String from, String to, int time, int duration){
        airportMap.get(from).getDepartureFlights().add(new Flight(from, to, time, duration));
        airportMap.get(to).getArrivalFlights().add(new Flight(from, to, time, duration));
    }
    public void showFlightsFromAirport(String code){
        System.out.println(String.format("%s (%s)\n%s\n%d",
                airportMap.get(code).getName(), airportMap.get(code).getCode(),
                airportMap.get(code).getCountry(), airportMap.get(code).getPassengers()));
        //airportMap.get(code).getDepartureFlights().stream().
//        IntStream.range(1, airportMap.get(code).getDepartureFlights().size()).forEach(i->{
//            airportMap.get(code).getDepartureFlights().forEach(flight -> System.out.println(i+". " + flight.toString()));
//        });
        int i = 1;
        for(Flight flight: airportMap.get(code).getDepartureFlights()){
            System.out.println(i++ + ". " + flight.toString());
        }
    }
    public void showDirectFlightsFromTo(String from, String to){
        airportMap.get(from).getDepartureFlights()
                .stream().filter(flight -> flight.getTo().equals(to)).forEach(System.out::println);
        if(airportMap.get(from).getDepartureFlights()
        .stream().filter(flight -> flight.getTo().equals(to)).count() == 0)
            System.out.println(String.format("No flights from %s to %s", from, to));
    }
    public void showDirectFlightsTo(String to){
        airportMap.get(to).getArrivalFlights()
                .forEach(System.out::println);
    }
}