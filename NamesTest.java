import java.util.*;
import java.util.stream.Collectors;

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde
class Names{
    Map<String, Integer> nameMap;
    List<String> nameList;

    public Names() {
        this.nameMap = new TreeMap<>();
        this.nameList = new ArrayList<>();
    }
    public void addName(String name){
        nameMap.computeIfAbsent(name, i -> 0);
        nameMap.put(name, nameMap.get(name) + 1);
    }
    public void printN(int n){
        List<String> var = nameMap.keySet().stream().filter(name ->
                nameMap.get(name) >= n).collect(Collectors.toList());
        var.forEach(s -> {
            System.out.println(String.format("%s (%d) %d",
                    s, nameMap.get(s), s.toLowerCase().chars().distinct().count()));
        });
    }
    public String findName(int len, int x){
        nameList = nameMap.keySet().stream().collect(Collectors.toList());
        nameList = nameList.stream()
                .filter(s -> s.length() < len).collect(Collectors.toList());
        return nameList.get(x % nameList.size());
    }
}