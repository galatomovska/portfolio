import java.util.*;

public class WebBrowserTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int limit = scanner.nextInt();
        scanner.nextLine();
        WebBrowser webBrowser = new WebBrowser(limit);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String tab = scanner.nextLine();
            int memory = scanner.nextInt();
            scanner.nextLine();
            webBrowser.addTab(tab, memory);
        }
        System.out.println(webBrowser);
        scanner.close();
    }
}
class Tab implements Comparable<Tab>{
    private String title;
    private int memory;

    public Tab(String title, int memory) {
        this.title = title;
        this.memory = memory;
    }

    public String getTitle() {
        return title;
    }

    public int getMemory() {
        return memory;
    }

    @Override
    public int compareTo(Tab o) {
        return Comparator.comparing(Tab::getMemory).reversed().thenComparing(Tab::getTitle)
                .compare(this, o);
    }

    @Override
    public String toString() {
        return String.format("(%dMB) %s\n", memory, title);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Tab tab = (Tab) o;
//        return memory == tab.memory &&
//                Objects.equals(title, tab.title);
//    }
}

class WebBrowser{
    private int limit;
    private TreeSet<Tab> tabs;
    TreeSet<Tab> buffer;
    public WebBrowser(int limit) {
        this.limit = limit;
        tabs = new TreeSet<>();
        buffer = new TreeSet<>();
    }
    public int getTotalMemory(){
        return tabs.stream().mapToInt(Tab::getMemory).sum();
    }
    public void addTab(String title, int memory){
        Tab tempTab = new Tab(title, memory);
        if(memory < limit){
            if(memory <= limit-getTotalMemory()){
                if(!tabs.contains(tempTab))
                    tabs.add(tempTab);
            }
            else{
                buffer.add(tabs.first());
                tabs.remove(tabs.first());
                this.addTab(title, memory);
            }
        }
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        tabs.forEach(tab -> sb.append(tab.toString()));
        sb.append("Total memory: " + getTotalMemory() + "MB");
        return sb.toString();
    }
}