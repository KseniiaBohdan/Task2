package task2.part2;

import java.util.*;

public class ListWrapper implements List {

    private ArrayList modifList = new ArrayList();
    private ArrayList fixedList = new ArrayList();

    public ListWrapper() {
    }

    public ListWrapper(ArrayList modif, ArrayList fixed) {
        this.fixedList.addAll(modif);
        this.modifList.addAll(fixed);
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return modifList.size() + fixedList.size();
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this list contains the specified element.
     *
     * @param element element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    @Override
    public boolean contains(Object element) {
        return fixedList.contains(element) || modifList.contains(element);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator iterator() {
        return new WrapperIterator();
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence.
     *
     * @return an array containing all of the elements in this list in proper
     * sequence
     */
    @Override
    public Object[] toArray() {
        if (size() > 0) {
            ArrayList array = new ArrayList();
            array.addAll(fixedList);
            array.addAll(modifList);
            return array.toArray();
        } else {
            return new Object[0];
        }
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element element to be appended to this list
     * @return true if list was changed
     */
    @Override
    public boolean add(Object element) {
        int oldSize = size();
        modifList.add(element);
        return oldSize != size();
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present and situated in modifiable part of list.  If this list does not contain
     * the element, it is unchanged.
     *
     * @param element element to be removed from this list, if present
     * @return true if this list contained the specified element
     */
    @Override
    public boolean remove(Object element) {
        int oldSize = size();
        checkIndexOfChange(indexOf(element));
        modifList.remove(element);
        return oldSize != size();

    }

    private void checkIndexOfChange(int index) {
        if (index < fixedList.size()) {
            throw new UnmodifiableElementsException("Try to change unmodifiable part of list.");
        }
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list.
     *
     * @param c collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     */
    @Override
    public boolean addAll(Collection c) {
        return addAll(size(), c);
    }

    /**
     * Inserts all of the elements in the specified collection into this
     * list at the specified position if this position in modifiable part of list.
     *
     * @param index   index at which to insert the first element
     * @param collect collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public boolean addAll(int index, Collection collect) {
        indexValid(index);
        int oldSize = size();
        checkIndexOfChange(index);
        modifList.addAll(index, collect);
        return (oldSize != size());
    }

    private void indexValid(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Removes all of the elements from this list if the fixed part of list is empty.
     */
    @Override
    public void clear() {
        if (fixedList.size() == 0) {
            modifList.clear();
        } else {
            throw new UnmodifiableElementsException("Try to change unmodifiable part of list.");
        }
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public Object get(int index) {
        indexValid(index);
        return toArray()[index];
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element if position is situated in modifiable part of list.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public Object set(int index, Object element) {
        indexValid(index);
        checkIndexOfChange(index);
        Object oldElement = modifList.set(index - fixedList.size(), element);
        return oldElement;
    }

    /**
     * Inserts the specified element at the specified position in this
     * list if position is situated in modifiable part of list.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public void add(int index, Object element) {
        indexValid(index);
        checkIndexOfChange(index);
        modifList.add(index - fixedList.size(), element);
    }

    /**
     * Removes the element at the specified position in this list
     * if position is situated in modifiable part of list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public Object remove(int index) {
        indexValid(index);
        checkIndexOfChange(index);
        Object element = get(index);
        modifList.remove(index - fixedList.size());
        return element;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     *
     * @param element element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     */
    @Override
    public int indexOf(Object element) {
        Object[] array = toArray();
        for (int i = 0; i < array.length; i++) {
            if (element == null ? element == array[i] : element.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     *
     * @param element element to search for
     * @return the index of the last occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     */
    @Override
    public int lastIndexOf(Object element) {
        Object[] array = toArray();
        int lastIndex = -1;
        for (int i = 0; i < size(); i++) {
            if (element == null ? element == array[i] : element.equals(array[i])) {
                lastIndex = i;
            }
        }
        return lastIndex;
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection if it not change fixed part of list.
     *
     * @param collect collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection collect) {
        int oldSize = size();
        Object[] array = toArray();
        for (int i = 0; i < size(); i++) {
            if (!collect.contains(array[i])) {
                remove(i);
            }
        }
        return (oldSize != size());
    }

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection if it not change fixed part of list.
     *
     * @param collect collection containing elements to be removed from this list
     * @return true if this list changed as a result of the call
     */
    @Override
    public boolean removeAll(Collection collect) {
        int oldSize = size();
        for (Object ob : collect) {
            if (contains(ob)) {
                remove(ob);
            }
        }
        return (size() != oldSize);
    }

    /**
     * Returns true if this list contains all of the elements of the
     * specified collection.
     *
     * @param collect collection to be checked for containment in this list
     * @return true if this list contains all of the elements of the
     * specified collection
     */
    @Override
    public boolean containsAll(Collection collect) {
        for (Object ob : collect) {
            if (!contains(ob)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns an array containing all of the elements in this list in
     * proper sequence.
     *
     * @param array the array into which the elements of this list are to
     *              be stored.
     * @return an array containing the elements of this list
     */
    @Override
    public Object[] toArray(Object[] array) {
        if (array.length >= size()) {
            System.arraycopy(array, 0, toArray(), 0, size());
        } else {
            array = new Object[size()];
            System.arraycopy(array, 0, toArray(), 0, size());
        }
        return array;
    }

    class WrapperIterator implements Iterator {

        private boolean flag = false;
        private int index = -1;

        /**
         * Returns true if the iteration has more elements.
         *
         * @return true if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return (index < size() - 1);
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Object next() {
            if (hasNext()) {
                ++index;
                flag = true;
                return toArray()[index];
            } else {
                throw new NoSuchElementException();
            }

        }
    }
}
