import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        list.remove(jin.nextInt());
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if ( num == 1 ) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }


}


class IntegerList{
    private List<Integer> list;

    public IntegerList(Integer... numbers) {
        this.list = new ArrayList<>();
        for (Integer i : numbers) {
            list.add(i);
        }
    }

    public IntegerList() {
        this.list = new ArrayList<>();
    }
    public void add(int el, int idx){
        if(idx < list.size()){
            list.add(idx, el);
        }
        else {
            IntStream.range(list.size(), idx).forEach(i -> list.add(0));
            list.add(el);
        }
    }
    public int remove(int idx){
        return list.remove(idx);
    }
    public void set(int el, int idx){
        list.set(idx, el);
    }
    public int get(int idx){
        return list.get(idx);
    }
    public int size(){
        return list.size();
    }
    public int count(int el){
        return (int) list.stream().filter(e -> e == el).count();
    }
    public void removeDuplicates(){
        Collections.reverse(list);
        list = list.stream().distinct().collect(Collectors.toList());
        Collections.reverse(list);
    }
    public int sumFirst(int k){
        if(k > size())
            k = list.size();
        return list.stream().limit(k).reduce(Integer::sum).orElse(0);
    }
    public int sumLast(int k){
        //index out of bounds;
        if(k > size())
            k = size();
        return list.subList(size() - k, list.size()).stream().reduce(Integer::sum).orElse(0);
    }
    public void shiftRight(int idx, int k){
        int tempSize = list.size();
        int tempNode = list.remove(idx);
        list.add(((idx + k) % tempSize), tempNode);
    }
    public void shiftLeft(int idx, int k){
        int tempSize = list.size();
        int tempNode = list.remove(idx);
//        if(Math.abs(idx - k) > tempSize){
//            list.add(Math.abs(idx - k) % 4, tempNode);
//        }
//        else{
//            list.add(tempSize - Math.abs(idx - k), tempNode);
//        }
        if((idx-k)<0)
        {
            list.add((idx-k)%tempSize + tempSize, tempNode);
        }
        else
            list.add(idx-k, tempNode);
    }
    public IntegerList addValue(int value){
        IntegerList integerList = new IntegerList();
        for(int i = 0; i < this.size(); i++ ){
            integerList.add(this.get(i) + value, i);
        }
        return integerList;

    }
}