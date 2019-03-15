import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Word vectors test
 */
public class WordVectorsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] words = new String[n];
        List<List<Integer>> vectors = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            words[i] = parts[0];
            List<Integer> vector = Arrays.stream(parts[1].split(":"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            vectors.add(vector);
        }
        n = scanner.nextInt();
        scanner.nextLine();
        List<String> wordsList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            wordsList.add(scanner.nextLine());
        }
        WordVectors wordVectors = new WordVectors(words, vectors);
        wordVectors.readWords(wordsList);
        n = scanner.nextInt();
        List<Integer> result = wordVectors.slidingWindow(n);
        System.out.println(result.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));
        scanner.close();
    }
}



class WordVectors{
    Map<String, List<Integer>> mapWordVector;
    public WordVectors(String[] words, List<List<Integer>> vectors){
        mapWordVector = new TreeMap<>();
        IntStream.range(0, words.length).forEach(i->mapWordVector.put(words[i], vectors.get(i)));
    }
    public void readWords(List<String> words) {
        List<Integer> defaulVector = new ArrayList<>();
        IntStream.range(1, 6).forEach(i -> defaulVector.add(5));
        words.forEach(word -> mapWordVector.computeIfAbsent(word, v -> defaulVector));
    }
    public List<Integer> slidingWindow(int n){

    }
    public int getScalarMax(List<List<Integer>> vectors){

    }
}



