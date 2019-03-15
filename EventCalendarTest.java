import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde
class Event{
    private String name;
    private String location;
    private Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd MMM, YYY HH:mm");
        return df.format(date) + " at " + location + ", " + name;
    }
}
class EventCalendar{
    private int year;
    private Set<Event> eventSet;

    public EventCalendar(int year) {
        this.year = year;
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        eventSet = new TreeSet<>(Comparator.comparing(Event::getDate)
                .thenComparing(Event::getName));
    }
    public void addEvent(String name, String location, Date date) throws WrongDateException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(calendar.get(Calendar.YEAR) != this.year)
            throw new WrongDateException(date);
        eventSet.add(new Event(name, location, date));
    }
    public void listEvents(Date date){
        Predicate<Event> chechDate = event -> (event.getDate().getDay() == date.getDay()) && (event.getDate().getMonth() == date.getMonth());
        List<Event> var = eventSet.stream().filter(chechDate).collect(Collectors.toList());
        if(var.isEmpty())
            System.out.println("No events on this day!");
        else var.forEach(System.out::println);
    }
    public void listByMonth(){
        Map<Integer, Long> var = eventSet.stream().collect(groupingBy(event -> event.getDate().getMonth() + 1, counting()));
        IntStream.range(1, 13).forEach(i -> {
            var.computeIfAbsent(i, k -> Long.valueOf(0));
            System.out.println(i + " : " + var.get(i));
        });
    }
}
class WrongDateException extends Exception{
    public WrongDateException(Date date) {
        super("Wrong date: " + date);
    }
}