import java.util.ArrayList;
import java.util.Iterator;

public class Test01 {
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(11);
        arrayList.add(22);
        Iterator iterator = arrayList.iterator();
        if (iterator.hasNext()) {
            //ConcurrentModificationException
            //arrayList.remove(1);
            System.out.println(iterator.next());
        }
    }
}
