import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}
class InvalidExtraTypeException extends Exception{
    public InvalidExtraTypeException(){
        super();
    }
}
class InvalidPizzaTypeException extends Exception {
    public InvalidPizzaTypeException(){
        super();
    }
}
class ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(Item item) {
        super();
    }
}


class EmptyOrder extends Exception{
    public EmptyOrder() {
        super("EmptyOrder");
    }
}

class OrderLockedException extends Exception{
    public OrderLockedException() {
        super();
    }
}

interface Item {
    public int getPrice();
    public String getType();
}


class ExtraItem implements Item{
    private String type;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if(type.equals("Coke") || type.equals("Ketchup"))
            this.type = type;
        else
            throw new InvalidExtraTypeException();

    }
    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getPrice() {
        if (this.type.equals("Coke"))
            return 5;
        else
            return 3;
    }
}

class PizzaItem implements Item{
    private String type;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if(type.equals("Standard") || type.equals("Pepperoni") || type.equals("Vegetarian"))
            this.type = type;
        else
            throw new InvalidPizzaTypeException();

    }

    @Override
    public int getPrice() {
        if(this.type.equals("Standard"))
            return 10;
        else if(this.type.equals("Pepperoni"))
            return 12;
        else
            return 8;
    }

    @Override
    public String getType() {
        return type;
    }

}

class OrderItem{
    private int quantity;
    private Item item;

    public OrderItem(Item item, int quantity) {
        this.quantity = quantity;
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item getItem() {
        return item;
    }
    public int getPrice(){
        return item.getPrice() * quantity;
    }
    @Override
    public String toString() {
        return String.format("%-15sx%2d%5d$", item.getType(), quantity, getPrice());
    }
}

class Order{
    private List<OrderItem> items;
    private boolean locked;

    public Order() {
        items = new ArrayList<>();
        this.locked = false;
    }
    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if(locked == true)
            throw new OrderLockedException();
        if(count > 10)
            throw new ItemOutOfStockException(item);
        OrderItem orderItem = new OrderItem(item, count);
        int flag = 1;
        for (int i = 0; i < items.size(); i ++){
            if(items.get(i).getItem().getType().equals(item.getType())){
                items.set(i, orderItem);
                flag = 0;
                break;
            }

        }
        if(flag == 1)
            items.add(orderItem);
    }
    public int getPrice(){
        return items.stream().mapToInt(oItem ->
                oItem.getPrice()).sum();
    }
    public void displayOrder(){
        for(int i = 0; i < items.size(); i++){
            System.out.println(String.format("%3d.%s", i + 1, items.get(i).toString()));
        }
        System.out.println(String.format("%-25s%d$", "Total:", this.getPrice()));
    }
    public void removeItem(int idx) throws OrderLockedException {
        if(locked == true)
            throw new OrderLockedException();
        if(items.isEmpty())
            throw  new ArrayIndexOutOfBoundsException(idx);
        items.remove(idx);
    }
    public void lock() throws EmptyOrder {
        if(items.isEmpty())
            throw new EmptyOrder();
        if(this.locked == true)
            this.locked = false;
        else{
            this.locked = true;
        }




    }

}











