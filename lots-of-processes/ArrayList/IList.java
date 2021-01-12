package ArrayList;

import java.util.Iterator;
import java.util.ListIterator;

public interface IList<E> extends Iterable<E> {

    void add(E e);
    boolean add(int index, E value);
    void clear();
    boolean contains(E element);
    E get(int index);
    E set(int index, E element);
    int indexOf(E element);
    boolean isEmpty();
    Iterator<E> iterator();
    ListIterator<E> listIterator();
    void remove(int index);
    boolean remove(E element);
    int size();
}

