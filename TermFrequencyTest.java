import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TermFrequencyTest {
    public static void main(String[] args) throws IOException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in, stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde

class TermFrequency {
    private Map<String, Integer> wordFreq;
    private int count;
    TermFrequency(InputStream inputStream, String[] stopWords){
        this.wordFreq = new TreeMap<>();
        this.count = 0;
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(new InputStreamReader(inputStream));
        while(scanner.hasNext()){
            String word = scanner.next();
            word = word.toLowerCase().replace(".", "").replace(",", "").trim();
            if(!Arrays.asList(stopWords).contains(word) && !word.isEmpty()){
                int freq = wordFreq.computeIfAbsent(word, i -> 0);
                wordFreq.replace(word, ++freq);
                count++;
            }
        }
    }
    public int countTotal(){
        return count;
    }
    public int countDistinct(){
        return wordFreq.size();
    }
    public List<String> mostOften(int k){
        return wordFreq.keySet().stream().sorted(Comparator.comparing(word->wordFreq.get(word)).reversed())
                .limit(10).collect(Collectors.toList());
    }
}
