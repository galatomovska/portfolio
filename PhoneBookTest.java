import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

class Contact implements Comparable<Contact>{
    private String name;
    private String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int compareTo(Contact o) {
        return Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber)
                .compare(this, o);
    }

    @Override
    public String toString() {
        return name + " " + number;
    }
    //    public String checknumber(String numberpart){
//        number.contains(numberpart)
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) &&
                Objects.equals(number, contact.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }
}

class PhoneBook{
    private Map<String, ArrayList<Contact>> NameContact_SetMap;
    Set<String> numbers;
    private Map<String, String> contactByNum;
    public PhoneBook() {
        NameContact_SetMap = new TreeMap<>();
        numbers = new TreeSet<>();
        contactByNum = new TreeMap<>();
    }
    void addContact(String name, String number) throws DuplicateNumberException {
        if(numbers.contains(number))
            throw new DuplicateNumberException(number);
        else{
            numbers.add(number);
            contactByNum.put(number, name);
            NameContact_SetMap.computeIfAbsent(name, v-> new ArrayList<>());
            NameContact_SetMap.get(name).add(new Contact(name, number));
        }
//        System.out.println("Set of numbers \n\n" + numbers);
//        System.out.println("Name-contacts Map\n\n" + NameContact_SetMap);
    }
    public void contactsByNumber(String number){
        List<String> list = contactByNum.keySet().stream().filter(s ->
                s.contains(number)).sorted(Comparator.comparing(s ->
                contactByNum.get(s))).collect(Collectors.toList());
        if(list.isEmpty())
            System.out.println("NOT FOUND");
        else
            list.forEach(s -> System.out.println(contactByNum.get(s) + " " + s));
    }
    public void contactsByName(String name){
        if(!NameContact_SetMap.containsKey(name)){
            System.out.println("NOT FOUND");
        }
        else {
            NameContact_SetMap.get(name).stream().sorted(Comparator.comparing(Contact::getNumber))
                    .forEach(System.out::println);
            //System.out.println(list);
        }
    }
}

class DuplicateNumberException extends Exception{
    public DuplicateNumberException(String number) {
        super("Duplicate Number: " + number);
    }
}