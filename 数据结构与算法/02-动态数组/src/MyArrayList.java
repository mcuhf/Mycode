import java.util.Arrays;

/**
 * 线性结构
 * 线性表 是具有n个相同数据类型的有限序列
 * 常见的线性表有：
 * 数组 顺序存储的线性表，所有元素的内存地址是连续的 无法动态修改数组容量
 * new 一个对象数组 数组中存放的是对象的地址值
 * 链表 栈 队列 哈希表
 */
public class MyArrayList<E> {
    /**
     * 元素的数量
     */
    private int size;
    /**
     * 所有的元素 底层数组实现
     */
    private E[] elements;


    private static final int DEFAULT_CAPACITY=10;
    private static final int ELEMENT_NOT_FOUND=-1;

    public MyArrayList(int capacity) {
        capacity = (capacity<DEFAULT_CAPACITY)? DEFAULT_CAPACITY:capacity;
        elements = (E[])new Object[capacity];
    }

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 清除所有元素
     */
    public void clear() {
        //能循环利用的留下，不能循环利用的丢掉 垃圾回收机制
        for (int i=0;i<size;i++){
            elements[i]=null;
        }
        size=0;
    }

    /**
     * 元素的数量
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * 是否包含某个元素
     * @param element
     * @return
     */
    public boolean contains(E element) {
        if (indexOf(element)!=ELEMENT_NOT_FOUND){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 添加元素到尾部
     * @param element
     */
    public void add(E element) {
        ensureCapacity(size+1);
         //直接在下标size的位置插入数据
//        elements[size] = element;
//        size++;
        //上面的两句可以合成一句代码
        //elements[size++] = element; 这一句也可以调用add方法
        add(size,element);
    }

    /**
     * 获取index位置的元素
     * @param index
     * @return
     */
    public E get(int index) {
        //size 是元素的总个数 index 对应的是0到size-1
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 设置index位置的元素
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    public E set(int index, E element){
        rangeCheck(index);
        E old = elements[index];
        elements[index]=element;
        return old;
    }
    /**
     * 在index位置插入一个元素
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        //移动的范围是index~size-1
        // i是4 index 是5 时 不走这个for
        ensureCapacity(size+1);
         for (int i=size-1;i>=index;i--){
             elements[i+1] = elements[i];
         }
         //直接在数组新位置加入元素

         elements[index] = element;
         //增加一个新元素 size 不要忘记加一
         size++;
    }


    /**
     * 删除index位置的元素
     * 返回被删除的元素
     * @param index
     * @return
     */
    public E remove(int index) {
        //对index 加入条件进行判断
        rangeCheck(index);
        E old = elements[index];
        for (int i=index+1;i<=size-1;i++){
            elements[i-1] = elements[i];
        }
        //remove 从右边向左移动 最右边的地址会存在非空值 置为null
//        size--;
//        elements[size]=null;
        //先size-- 再清空
        //上面两句可以合成为一句
        elements[--size]=null;
        return old;
    }

    /**
     * 查看元素的索引
     * @param element
     * @return
     */
    public int indexOf(E element) {
        if (element==null){
            for (int i=0;i<size;i++){
                if (elements[i]==null){
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
            //对象内型的数据判断是否相等 不能单纯的使用==
            if (elements[i].equals(element)) {
                return i;
            }
        }
    }
        return ELEMENT_NOT_FOUND;
    }

//	public int indexOf2(E element) {
//		for (int i = 0; i < size; i++) {
//			if (valEquals(element, elements[i])) return i; // 2n
//		}
//		return ELEMENT_NOT_FOUND;
//	}
//
//	private boolean valEquals(Object v1, Object v2) {
//		return v1 == null ? v2 == null : v1.equals(v2);
//	}

    /**
     * 保证要有capacity的容量
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        //如何扩容 new 返回内存是随机的 申请更大的数组 进行数组的拷贝
        int oldCapacity = elements.length;
        if (oldCapacity>capacity){
            return;
        }
        //新容量是旧容量的1.5倍
        int newCapacity=oldCapacity+(oldCapacity>>1);
        E[] newElements = (E[])new Object[capacity];
        for (int i=0;i<size;i++){
            newElements[i] = elements[i];
        }
        elements=newElements;
        System.out.println("旧容量为："+oldCapacity+" 新容量为："+newCapacity);
    }

    private void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index:"+index+"Size:"+size);
    }

    private void rangeCheck(int index) {
        if (index<0||index>=size){
            outOfBounds(index);
        }
    }

    private void rangeCheckForAdd(int index) {
        //index要被插入元素的位置，可以在数组元素的最后一个位置插入元素，所以索引是可以等于size的
        //像其他操作如indexOf get remove 是获取数组内部的数据 最大下标必须是size-1；
        if (index<0||index>size){
            outOfBounds(index);
        }
    }


    @Override
    public String toString() {
        return "MyArrayList{" +
                "size=" + size +
                ", elements=" + Arrays.toString(elements) +
                '}';
    }
}
