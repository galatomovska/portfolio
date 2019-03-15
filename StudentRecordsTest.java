import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
    public static void main(String[] args) throws IOException {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}

// your code here

class Student implements Comparable<Student>{
    private String code;
    private String major;
    private ArrayList<Integer> grades;

    public Student(String line) {
        grades = new ArrayList<>();
        List<String> parts = new ArrayList<>();
        parts = Arrays.asList(line.split(" "));
        code = parts.get(0);
        major = parts.get(1);
        parts.subList(2, parts.size()).stream().map(Integer::parseInt).forEach(integer -> grades.add(integer));
    }
    public double getGPA(){
        return grades.stream().mapToInt(Integer::intValue).average().getAsDouble();
    }
    @Override
    public String toString() {
        return String.format("%s %.2f\n", code, getGPA());
    }

    public String getCode() {
        return code;
    }

    public String getMajor() {
        return major;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }

    @Override
    public int compareTo(Student o) {
        return Comparator.comparing(Student::getGPA).reversed().thenComparing(Student::getCode)
                .compare(this, o);
    }
}
class StudentRecords{
    private TreeSet<Student> students;
    public int readRecords(InputStream inputStream) throws IOException {
        students = new TreeSet<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = br.readLine()) != null && line.length() != 0){
            students.add(new Student(line));
        }
        //System.out.println(students);
        return students.size();
    }
    void writeTable(OutputStream outputStream){
        StringBuilder stringBuilder = new StringBuilder();
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
        Map<String, Set<Student>> var = students.stream().collect(groupingBy((Student::getMajor), toSet()));
        var.keySet().stream().sorted().forEach(s -> {
            stringBuilder.append(s + "\n");
            var.get(s).stream().sorted().forEach(stu->stringBuilder.append(stu.toString()));
        });
        printWriter.print(stringBuilder.toString());
        printWriter.flush();
    }
    public void writeDistribution(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
        Map<String, Set<Student>> mapStudentsByMajor = students.stream().collect(groupingBy((Student::getMajor),
                 toSet()));
        Map<String, List<Integer>> oceni = new TreeMap<>();
        mapStudentsByMajor.keySet().stream().forEach(k->{
            List<Integer> temp = mapStudentsByMajor.get(k).stream().flatMap(student ->
                    student.getGrades().stream()).collect(Collectors.toList());
            oceni.put(k, temp);
                });
        //System.out.println(oceni.toString());
        oceni.keySet().stream().sorted(Comparator.comparing(k->oceni.get(k).stream().filter(integer ->
                integer == 10).count()).reversed()).forEach(s ->
        {
            printWriter.println(s);
            IntStream.range(6, 11).forEach(i->{
                long count = oceni.get(s).stream().filter(integer -> integer == i).count();
                StringBuilder stringBuilder = new StringBuilder();
                long limit;
                if(count % 10 == 0){
                    limit = count / 10;
                }
                else {
                    limit = count / 10 + 1;
                }
                for(int j = 0; j < limit; j++){
                    stringBuilder.append("*");
                }
                printWriter.printf("%2d | %s(%d)%n", i, stringBuilder.toString(), count);
            });
        });
        printWriter.flush();
    }
}