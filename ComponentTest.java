import java.util.*;

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

//vasiot kod ovde
class Component{
    private String color;
    private int weight;
    private Set<Component> components;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        this.components = new TreeSet<>(Comparator.comparing(Component::getWeight)
        .thenComparing(Component::getColor));
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public Set<Component> getComponents() {
        return components;
    }
    public void addComponent(Component component){
        components.add(component);
    }

    public void setColor(String color) {
        this.color = color;
    }
    public void changeColor(int weight, String color){
        if(this.getWeight() < weight)
            this.color = color;
        components
                .forEach(component ->
                    component.changeColor(weight, color));
    }
    public String toString(int lvl){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(weight + ":" + color + "\n");
        for (Component component : components) {
            for(int i = 0; i <= lvl; i++){
                stringBuilder.append("---");
            }
            stringBuilder.append(component.toString(lvl + 1));
        }
        return stringBuilder.toString();
    }
}
class Window{
    private String name;
    private Map<Integer, Component> componentMap;

    public Window(String name) {
        this.name = name;
        this.componentMap = new TreeMap<>();
    }
    void addComponent(int position, Component component) throws InvalidPositionException {
        if(componentMap.containsKey(position))
            throw new InvalidPositionException(position);
        else
            componentMap.put(position, component);

    }
    public void changeColor(int weight, String color){
        componentMap.values()
                .forEach(component -> component.changeColor(weight, color));
    }
    public void swichComponents(int pos1, int pos2){
        Component temp = componentMap.get(pos1);
        componentMap.replace(pos1, componentMap.get(pos2));
        componentMap.put(pos2, temp);
    }
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WINDOW " + name + "\n");
        componentMap.keySet().forEach(position ->{
            stringBuilder.append(position + ":" + componentMap.get(position).toString(0));
        });
        return stringBuilder.toString();
    }
}
class InvalidPositionException extends Exception{
    public InvalidPositionException(int pos) {
        super("Invalid position " + pos +", alredy taken!");
    }
}

