import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Discounts
 */
public class DiscountsTest {
    public static void main(String[] args) throws IOException {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        //discounts.byTotalDiscount().forEach(System.out::println);
    }
}

// Vashiot kod ovde
class Store implements Comparable<Store>{
    private List<String> discountPairs;
    private String name;
    public Store(String line) {
        discountPairs = new ArrayList<>();
        List<String> temp = Arrays.asList(line.split("\\s+"));
        name = temp.get(0);
        //may need to use addAll();
        discountPairs = temp.subList(1, temp.size());
    }
    public double averageDiscount(){
        return discountPairs.stream().mapToInt(i->getDiscount(i)).average().getAsDouble();

    }
    public int totalDiscount(){
        return getTotalPriceNODiscount() - getTotalPriceWithDiscount();
    }
    public int getDiscount(String s){
        String[] parts = s.split(":");
        return ((Integer.parseInt(parts[1]) - Integer.parseInt(parts[0]))
                / Integer.parseInt(parts[1])) * 100;
    }
    public int getTotalPriceWithDiscount(){
        List<String> var = discountPairs.stream().map(s -> (s.split(":")[0])).collect(Collectors.toList());
        return var.stream().mapToInt(Integer::parseInt).sum();
    }
    public int getTotalPriceNODiscount(){
        List<String> var = discountPairs.stream().map(s -> (s.split(":")[1])).collect(Collectors.toList());
        return var.stream().mapToInt(Integer::parseInt).sum();
    }

    public List<String> getDiscountPairs() {
        return discountPairs;
    }

    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s\nAverageDiscount: %.1f%%\nTotal discount: %d\n",
        name, averageDiscount(), totalDiscount()));
        discountPairs.forEach(s -> stringBuilder.append(String.format("%02d%% %s", getDiscount(s), s)));
        return stringBuilder.toString();

    }

    @Override
    public int compareTo(Store o) {
        return Comparator.comparing(Store::averageDiscount).thenComparing(Store::getName)
                .compare(this, o);
    }

    @Override
    public String toString() {
        return String.format("%s\nAverage discount: \nTotal discount: ", name);
    }
}

class Discounts{
    Set<Store> stores;
    public int readStores(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        stores = new TreeSet<>();
        String line;
        while((line = br.readLine()) != null){
            stores.add(new Store(line));
        }
        //System.out.println(stores);
        return stores.size();
    }
    public List<Store> byAverageDiscount(){
        return stores.stream()
                .sorted(Comparator.comparing(Store::averageDiscount).thenComparing(Store::getName))
                .collect(Collectors.toList());
    }
}