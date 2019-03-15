import java.util.*;
import java.util.stream.Collectors;

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for(int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for(int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}


class BlockContainer<T>{
    Map<Integer, TreeSet<T>> map;
    private int sizeLimit;
    private int last;
    public BlockContainer(int n) {
        map = new TreeMap<>();
        sizeLimit = n;
        last = 0;
    }
    public void add(T a){
        if(map.isEmpty()) {
            map.put(0, new TreeSet<>());
        }
        if(map.get(last).size() < sizeLimit)
            map.get(last).add(a);
        else{
            map.put(last + 1, new TreeSet<>());
            last++;
            map.get(last).add(a);
        }

    }
    public boolean remove(T a){
        if(!map.get(last).contains(a))
            return false;
        map.get(last).remove(a);
        if(map.get(last).isEmpty()){
            map.remove(last);
            last--;
        }
        return true;
    }

    public void sort(){

        Set<T> var = map.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        map = new TreeMap<>();
        last = 0;
        var.stream().sorted().forEach(t -> this.add(t));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        map.values().forEach(set->{
            if(map.get(last).equals(set))
                stringBuilder.append(set.toString());
            else
                stringBuilder.append(set.toString() + ",");
        });
        return stringBuilder.toString();
    }
}



