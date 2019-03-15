import java.util.*;
import java.util.stream.IntStream;

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

class Sector{
    String code;
    int size;
    int availableSeatsLeft;
    int type;
    Set<Integer> takenSeats;

    public Sector(String code, int size) {
        this.code = code;
        this.size = size;
        this.availableSeatsLeft = size;
        this.type = 0;
        this.takenSeats = new HashSet<>();
    }

    public float getPercent(){
        return ((float)(size - availableSeatsLeft) / size) * 100;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%",
                code, availableSeatsLeft, size, getPercent());
    }

    public String getCode() {
        return code;
    }

    public int getAvailableSeatsLeft() {
        return availableSeatsLeft;
    }
}

class Stadium{
    private String name;
    private Map<String, Sector> sectorMap;

    public Stadium(String name) {
        this.name = name;
        sectorMap = new HashMap<>();
    }
    public void createSectors(String[] sectorNames, int[] sizes){
        IntStream.range(0, sizes.length).forEach(i ->{
            sectorMap.put(sectorNames[i], new Sector(sectorNames[i], sizes[i]));
        });
    }
    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        if(sectorMap.get(sectorName).takenSeats.contains(seat)){
            throw new SeatTakenException();
        }
        int sectorType = sectorMap.get(sectorName).type;
        if(type != 0 && sectorType != 0 && sectorType != type)
            throw new SeatNotAllowedException();
        if(sectorType == 0)
            sectorMap.get(sectorName).type = type;
        sectorMap.get(sectorName).takenSeats.add(seat);
        sectorMap.get(sectorName).availableSeatsLeft--;
    }
    public void showSectors(){
        sectorMap.values().stream()
                .sorted(Comparator.comparing(Sector::getAvailableSeatsLeft).reversed()
                .thenComparing(Sector::getCode)).forEach(System.out::println);
    }

}
class SeatTakenException extends Exception{
    public SeatTakenException() {
    }
}
class SeatNotAllowedException extends Exception{
    public SeatNotAllowedException() {
    }
}