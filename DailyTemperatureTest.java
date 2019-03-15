import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class DailyTemperatureTest {
    public static void main(String[] args) throws IOException {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

class DailyMeasure{
    private int dayOfYear;
    List<String> measuredTemps;

    public DailyMeasure(List<String> lineInParts) {
        dayOfYear = Integer.parseInt(lineInParts.get(0));
        measuredTemps = lineInParts.subList(1, lineInParts.size());
    }
    public int getCount(){
        return measuredTemps.size();
    }
    public String getCelsius(String s){
        if(s.charAt(s.length() - 1) == 'F'){
            s = String.valueOf((Double.parseDouble(s.substring(0, s.length() - 1)) - 32) * (double) 5/9) + "C";
        }
        return s;
    }
    public String getFarenheit(String s){
        if(s.charAt(s.length() - 1) == 'C'){
            s = String.valueOf((Double.parseDouble(s.substring(0, s.length() - 1)) * (double) 9/5) + 32) + "F";
        }
        return s;
    }
    public double getMin(List<String> temps){
        return temps.stream().mapToDouble(d ->
                Double.valueOf(d.substring(0, d.length() - 1))).min().getAsDouble();
    }
    public double getMax(List<String> temps){
        return temps.stream().mapToDouble(d ->
                Double.valueOf(d.substring(0, d.length() - 1))).max().getAsDouble();
    }
    public double getAverage(List<String> temps){
        return temps.stream().mapToDouble(d ->
                Double.valueOf(d.substring(0, d.length() - 1))).average().getAsDouble();
    }

    public String toString(char scale){
        if(scale == 'C'){
            List<String> temp = new ArrayList<>();
            measuredTemps.forEach(s -> temp.add(getCelsius(s)));
            return String.format("%d: Count: %d Min: %.2fC Max: %.2fC Avg: %.2fC",
                    dayOfYear, getCount(), getMin(temp),
                    getMax(temp), getAverage(temp));
        }
        else{
            List<String> temp1 = new ArrayList<>();
            measuredTemps.forEach(s-> temp1.add(getFarenheit(s)));
            return String.format("%d: Count: %-3d Min: %.2fF Max: %.2fF Avg: %.2fF",
                    dayOfYear, getCount(), getMin(temp1),
                    getMax(temp1), getAverage(temp1));
        }
    }
}


class DailyTemperatures{
    private Map<Integer, DailyMeasure> temperaturesOnDay;
    public DailyTemperatures() {
        this.temperaturesOnDay = new TreeMap<>();
    }
    void readTemperatures(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<List<String>> lines = new ArrayList<>();
        String line;
        while((line = br.readLine()) != null && line.length() != 0){
            lines.add(Arrays.asList(line.split("\\s+")));
        }
        lines.forEach(strings ->
                temperaturesOnDay.put(Integer.parseInt(strings.get(0)), new DailyMeasure(strings)));
    }
    void writeDailyStats(OutputStream outputStream, char scale){
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
        temperaturesOnDay.values().stream().forEach(dailyMeasure ->
                printWriter.println(dailyMeasure.toString(scale)));
        printWriter.flush();
    }
}