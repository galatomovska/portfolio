import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) throws IOException {
        // Vasiod kod ovde
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String> list = new ArrayList<>();
        br.lines().forEach(s -> list.add(s));
        print(getMapped(list));
    }
    public static String getAsSorted(String word){
        char [] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
    public static Map<String, TreeSet<String>> getMapped(List<String> list){
        Map<String, TreeSet<String>> map = new TreeMap<>();
        list.stream().forEach(s -> {
            map.computeIfAbsent(getAsSorted(s), k-> new TreeSet<>());
            map.get(getAsSorted(s)).add(s);
        });

        return map;
    }
    public static void print(Map<String, TreeSet<String>> map){
        map.values().stream()
                .sorted(Comparator.comparing(set -> set.first()))
                .filter(set -> set.size() >= 5).forEach(set -> {
            set.forEach(s -> {
                if(set.last().equals(s))
                    System.out.print(s + "\n");
                else
                    System.out.print(s + " ");
            });
        });
    }
}