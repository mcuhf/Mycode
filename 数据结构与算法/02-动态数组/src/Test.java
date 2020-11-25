import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        MyArrayList<Person> personMyArrayList = new MyArrayList<>();
        //默认是支持存储空元素
        personMyArrayList.add(null);
        personMyArrayList.add(new Person(12));
        personMyArrayList.add(new Person(22));
        System.out.println(personMyArrayList.indexOf(null));
        System.out.println(personMyArrayList);
        //提醒垃圾回收
        System.gc();
    }
}


