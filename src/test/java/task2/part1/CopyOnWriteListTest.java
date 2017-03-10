package task2.part1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CopyOnWriteListTest {

    CopyOnWriteList cowList;

    @Before
    public void init() {
        cowList = new CopyOnWriteList();
        cowList.add("Test 1");
        cowList.add("Test 2");
        cowList.add("Test 3");
    }

    @Test
    public void whenClearThenHasNext() {
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        cowList.clear();
        assertTrue(cowIterator.hasNext());
    }

    @Test
    public void whenRemoveThenHasNext() {
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        cowList.removeAll(cowList);
        assertTrue(cowIterator.hasNext());
    }

    @Test
    public void whenAddThenHasNotNext() {
        CopyOnWriteList cowList = new CopyOnWriteList();
        cowList.add("Test 1");
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        assertFalse(cowIterator.hasNext());
    }

    @Test
    public void whenAddToIndexThenHasNotNext() {
        CopyOnWriteList cowList = new CopyOnWriteList();
        cowList.add(0, "Test 1");
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        assertFalse(cowIterator.hasNext());
    }

    @Test
    public void whenRemoveElementThenHasNext() {
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        cowList.remove("Test 1");
        cowList.remove("Test 2");
        cowList.remove("Test 3");
        assertTrue(cowIterator.hasNext());
    }

    @Test
    public void whenRemoveElementByIndexThenHasNext() {
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        cowList.remove(0);
        cowList.remove(0);
        cowList.remove(0);
        assertTrue(cowIterator.hasNext());
    }

    @Test
    public void whenAddAllThenSizeIsUnchanged() {
        int oldSize = cowList.size();
        cowList.addAll(cowList);
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        int newSize = 0;
        for (Object o : cowList) {
            ++newSize;
        }
        assertEquals(oldSize, newSize);
    }

    @Test
    public void whenAddAllToIndexThenSizeIsUnchanged() {
        int oldSize = cowList.size();
        cowList.addAll(2, cowList);
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        int newSize = 0;
        for (Object o : cowList) {
            ++newSize;
        }
        assertEquals(oldSize, newSize);
    }

    @Test
    public void whenRetainAllThenSizeIsUnchanged() {
        int oldSize = cowList.size();
        cowList.retainAll(cowList);
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        int newSize = 0;
        for (Object o : cowList) {
            ++newSize;
        }
        assertEquals(oldSize, newSize);
    }

    @Test
    public void whenSetThenElementIsUnchanged() {
        cowList.set(2, "TEST");
        Object element = "";
        CopyOnWriteList.CopyOnWriteIterator cowIterator = cowList.iterator();
        for (Object o : cowList) {
            element = o;
        }
        assertEquals(element.toString(), "Test 3");
    }

}