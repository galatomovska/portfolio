import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class F1Test {

    public static void main(String[] args) throws IOException {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Race {
    // vashiot kod ovde
    TreeSet<Racer> racers;
    void readResults(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while((line = br.readLine()) != null && line.length() != 0){
            lines.add(line);
        }
        racers = new TreeSet<>();
        lines.forEach(s -> racers.add(new Racer(s)));
        //System.out.print(racers.toString());
    }
    void printSorted(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
        ArrayList<Racer> racerList = (ArrayList<Racer>) racers.stream().collect(Collectors.toList());
        IntStream.range(0, racerList.size()).forEach(i->
                //racers.forEach(racer->printWriter.println(i + ". " + racer.toString())));
                printWriter.println(i + 1 + ". " + racerList.get(i)));
        printWriter.flush();
    }

}
class Racer implements Comparable<Racer>{
    private String name;
    private List<String> laps;

    public Racer(String line) {
        String[] parts = line.split(" ");
        name = parts[0];
        laps = new ArrayList<>();
        IntStream.range(1, 4).forEach(i->laps.add(parts[i]));
    }
    public long getTimeInMs(String lap){
        String[] parts = lap.split(":");
        return Integer.parseInt(parts[0]) * 60000 + Integer.parseInt(parts[1]) * 1000 + Integer.parseInt(parts[2]);
    }
    public String getBestLap(){
        return laps.stream().sorted(Comparator.comparing(lap -> getTimeInMs(lap))).findFirst().get();
//        long temp = laps.stream().mapToLong(lap->getTimeInMs(lap)).min().getAsLong();
//        return laps.stream().filter(lap->getTimeInMs(lap) == temp).findFirst().get();
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s", name, getBestLap());
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Racer o) {
        return Comparator.comparing(Racer::getBestLap).compare(this, o);
    }
}