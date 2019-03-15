import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.function.Predicate;
import java.util.stream.IntStream;


public class Speluvanje {
    public static void main(String[] args) throws IOException {
        //OBHT<Zbor, String> tabela;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        //---Vie odluchete za goleminata na hesh tabelata----
        //tabela = new OBHT<Zbor,String>(???);

        /*
         *
         * Vashiot kod tuka....
         *
         */
        Hashtable<String,Boolean> dictionary = new Hashtable<>();
        IntStream.range(0,N).forEach(i->{
                    String word = null;
                    try {
                        word = br.readLine();
                    } catch (IOException e) {
                    }
                    dictionary.computeIfAbsent(word, v->true);
                }
        );
        String text = br.readLine();
        ArrayList<String> words =  new ArrayList<>();
        Arrays.asList(text.split("\\s+")).forEach(s->words.add(s));
        ArrayList<String> finalList = new ArrayList<>();
        //Predicate<String> predicate = word-> word.endsWith(".") || word.endsWith(",") || word.endsWith("?") || word.endsWith("!");
        words.forEach(word -> {
            if(!Character.isAlphabetic(word.charAt(word.length()-1)))
                word = word.replace(word.charAt(word.length()-1), '\0').toLowerCase().trim();
            if(!dictionary.containsKey(word.toLowerCase())&&!word.isEmpty()){
                finalList.add(word);
            }
        });
        if(finalList.isEmpty())
            System.out.println("Bravo");
        else {
            finalList.forEach(System.out::println);
        }
    }
}
