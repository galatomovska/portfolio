import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;

public class SubtitlesTest {
    public static void main(String[] args) throws IOException {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

class Subtitle{
    private String text;
    private int startTime;
    private int endTime;
    private int order;

    public Subtitle(String text, int startTime, int endTime, int order) {
        this.text = text;
        this.startTime = startTime;
        this.endTime = endTime;
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getOrder() {
        return order;
    }
    public String durationString(int time){
        int h = time / 3600000;
        int m = (time % 3600000) / 60000;
        int s = (time % 60000) / 1000;
        int ms = time % 1000;
        return String.format("%02d:%02d:%02d,%03d", h, m, s, ms);
    }
    public void shift(int ms){
        startTime += ms;
        endTime += ms;
    }
    @Override
    public String toString() {
        return order + "\n" + durationString(startTime) + " --> " + durationString(endTime) + "\n" + text + "\n";
    }
}
class Subtitles{
    private List<Subtitle> subtitles;

    public Subtitles() {
        this.subtitles = new ArrayList<>();
    }

    private static void accept(Subtitle subtitle) {
        System.out.println(subtitle.toString());
    }

    public int loadSubtitles(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while(true){
            int order;
            try{
                order = Integer.parseInt(bufferedReader.readLine());
            }catch (Exception e){
                break;
            }

            String time = bufferedReader.readLine();
            String[] parts = time.split(" --> ");
            int startTime = (Integer.parseInt(parts[0].substring(0, 2)) * 3600000) +
                    (Integer.parseInt(parts[0].substring(3, 5)) * 60000) +
                    (Integer.parseInt(parts[0].substring(6, 8)) * 1000) +
                            Integer.parseInt(parts[0].substring(9));
            int endTime = (Integer.parseInt(parts[1].substring(0, 2)) * 3600000) +
                    (Integer.parseInt(parts[1].substring(3, 5)) * 60000) +
                    (Integer.parseInt(parts[1].substring(6, 8)) * 1000) +
                    Integer.parseInt(parts[1].substring(9));
            StringBuilder stringBuilder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null && line.length() > 0){
                stringBuilder.append(String.format("%s\n", line));
            }
            Subtitle subtitle = new Subtitle(stringBuilder.toString(), startTime, endTime, order);
            subtitles.add(subtitle);
        }
        return subtitles.size();
    }
    public void print(){
        subtitles.forEach(Subtitles::accept);
    }
    public void shift(int ms){
        subtitles.forEach(subtitle -> subtitle.shift(ms));
    }

}