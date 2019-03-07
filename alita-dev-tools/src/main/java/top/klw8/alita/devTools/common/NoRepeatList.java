package top.klw8.alita.devTools.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @ClassName: SetList
 * @Description: 不可重复的list
 * @author klw
 * @date 2019-03-04 19:25:44
 */
public class NoRepeatList<E> implements List<E> {

    private Map<Integer, Object> map;

    private List<E> list;

    private static final Object PRESENT = new Object();

    public NoRepeatList() {
	map = new HashMap<Integer, Object>();
	list = new ArrayList<E>();
    }

    public NoRepeatList(int initSize) {
	map = new HashMap<Integer, Object>(initSize);
	list = new ArrayList<E>(initSize);
    }

    /*
     * <p>Title: size</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see java.util.List#size()
     */
    @Override
    public int size() {
	return list.size();
    }

    /*
     * <p>Title: isEmpty</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see java.util.List#isEmpty()
     */
    @Override
    public boolean isEmpty() {
	return list.isEmpty();
    }

    /*
     * <p>Title: contains</p> <p>Description: </p>
     * 
     * @param o
     * 
     * @return
     * 
     * @see java.util.List#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object o) {
	return list.contains(o);
    }

    /*
     * <p>Title: iterator</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see java.util.List#iterator()
     */
    @Override
    public Iterator<E> iterator() {
	return list.iterator();
    }

    /*
     * <p>Title: toArray</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see java.util.List#toArray()
     */
    @Override
    public Object[] toArray() {
	return list.toArray();
    }

    /*
     * <p>Title: toArray</p> <p>Description: </p>
     * 
     * @param a
     * 
     * @return
     * 
     * @see java.util.List#toArray(java.lang.Object[])
     */
    @Override
    public <T> T[] toArray(T[] a) {
	return list.toArray(a);
    }

    /*
     * <p>Title: add</p> <p>Description: </p>
     * 
     * @param e
     * 
     * @return
     * 
     * @see java.util.List#add(java.lang.Object)
     */
    @Override
    public boolean add(E e) {
	if (!map.containsKey(e.hashCode())) {
	    map.put(e.hashCode(), PRESENT);
	    list.add(e);
	    return true;
	}
	return false;
    }

    /*
     * <p>Title: remove</p> <p>Description: </p>
     * 
     * @param o
     * 
     * @return
     * 
     * @see java.util.List#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object o) {
	if (map.containsKey(o.hashCode())) {
	    map.remove(o.hashCode());
	    list.remove(o);
	    return true;
	}
	return false;
    }

    /*
     * <p>Title: containsAll</p> <p>Description: </p>
     * 
     * @param c
     * 
     * @return
     * 
     * @see java.util.List#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
	return list.containsAll(c);
    }

    /*
     * <p>Title: addAll</p> <p>Description: </p>
     * 
     * @param c
     * 
     * @return
     * 
     * @see java.util.List#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
	boolean tagReturn = false;
	Iterator<? extends E> it = c.iterator();
	while (it.hasNext()) {
	    E item = it.next();
	    if (!map.containsKey(item.hashCode())) {
		list.add(item);
		map.put(item.hashCode(), PRESENT);
		tagReturn = true;
	    }
	}
	return tagReturn;
    }

    /*
     * <p>Title: addAll</p> <p>Description: </p>
     * 
     * @param index
     * 
     * @param c
     * 
     * @return
     * 
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
	boolean tagReturn = false;
	Iterator<? extends E> it = c.iterator();
	while (it.hasNext()) {
	    E item = it.next();
	    if (!map.containsKey(item.hashCode())) {
		list.add(index, item);
		map.put(item.hashCode(), PRESENT);
		index++;
		tagReturn = true;
	    }
	}
	return tagReturn;
    }

    /*
     * <p>Title: removeAll</p> <p>Description: </p>
     * 
     * @param c
     * 
     * @return
     * 
     * @see java.util.List#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
	boolean tagReturn = false;
	Iterator<?> it = c.iterator();
	while (it.hasNext()) {
	    Object item = it.next();
	    if (map.containsKey(item.hashCode())) {
		list.remove(item);
		map.remove(item.hashCode());
		tagReturn = true;
	    }
	}
	return tagReturn;
    }

    /*
     * <p>Title: retainAll</p> <p>Description: </p>
     * 
     * @param c
     * 
     * @return
     * 
     * @see java.util.List#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
	Iterator<?> it = c.iterator();
	map.clear();
	while (it.hasNext()) {
	    Object item = it.next();
	    map.remove(item.hashCode());
	}
	return list.retainAll(c);
    }

    /*
     * <p>Title: clear</p> <p>Description: </p>
     * 
     * @see java.util.List#clear()
     */
    @Override
    public void clear() {
	map.clear();
	list.clear();
    }

    /*
     * <p>Title: get</p> <p>Description: </p>
     * 
     * @param index
     * 
     * @return
     * 
     * @see java.util.List#get(int)
     */
    @Override
    public E get(int index) {
	return list.get(index);
    }

    /*
     * <p>Title: set</p> <p>Description: </p>
     * 
     * @param index
     * 
     * @param element
     * 
     * @return
     * 
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public E set(int index, E element) {
	E temp = list.get(index);
	map.remove(temp.hashCode());
	map.put(element.hashCode(), PRESENT);
	return list.set(index, element);
    }

    /*
     * <p>Title: add</p> <p>Description: </p>
     * 
     * @param index
     * 
     * @param element
     * 
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public void add(int index, E element) {
	map.put(element.hashCode(), PRESENT);
	list.add(index, element);

    }

    /*
     * <p>Title: remove</p> <p>Description: </p>
     * 
     * @param index
     * 
     * @return
     * 
     * @see java.util.List#remove(int)
     */
    @Override
    public E remove(int index) {
	E temp = list.get(index);
	map.remove(temp.hashCode());
	return list.remove(index);
    }

    /*
     * <p>Title: indexOf</p> <p>Description: </p>
     * 
     * @param o
     * 
     * @return
     * 
     * @see java.util.List#indexOf(java.lang.Object)
     */
    @Override
    public int indexOf(Object o) {
	return list.indexOf(o);
    }

    /*
     * <p>Title: lastIndexOf</p> <p>Description: </p>
     * 
     * @param o
     * 
     * @return
     * 
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */
    @Override
    public int lastIndexOf(Object o) {
	return list.lastIndexOf(o);
    }

    /*
     * <p>Title: listIterator</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see java.util.List#listIterator()
     */
    @Override
    public ListIterator<E> listIterator() {
	return list.listIterator();
    }

    /*
     * <p>Title: listIterator</p> <p>Description: </p>
     * 
     * @param index
     * 
     * @return
     * 
     * @see java.util.List#listIterator(int)
     */
    @Override
    public ListIterator<E> listIterator(int index) {
	return list.listIterator(index);
    }

    /*
     * <p>Title: subList</p> <p>Description: </p>
     * 
     * @param fromIndex
     * 
     * @param toIndex
     * 
     * @return
     * 
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
	return list.subList(fromIndex, toIndex);
    }

}
