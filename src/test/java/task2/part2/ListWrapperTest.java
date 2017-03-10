package task2.part2;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class ListWrapperTest {

    ListWrapper listW;

    @Before
    public void init() {
        ArrayList fList = new ArrayList();
        fList.add(1);
        fList.add(2);
        ArrayList mList = new ArrayList();
        mList.add("a");
        mList.add("b");
        listW = new ListWrapper(fList, mList);
    }

    @Test
    public void whenAddElementThenTrue() {
        int s = listW.size();
        listW.add("Test");
        assertTrue(s != listW.size());
    }

    @Test(expected = UnmodifiableElementsException.class)
    public void whenRemoveElementFromModifiablePartThenException() {
        listW.remove(1);
    }

    @Test
    public void whenRemoveElementFromModifiablePartThenFalse() {
        listW.remove("a");
        assertFalse(listW.contains("a"));
    }

    @Test
    public void whenAddAllOnIndexInModifiablePartThenTrue() {
        ArrayList arr = new ArrayList();
        arr.add("Test");
        assertTrue(listW.addAll(2, arr));
    }

    @Test(expected = UnmodifiableElementsException.class)
    public void whenAddAllOnIndexInFixedPartThenException() {
        ArrayList arr = new ArrayList();
        arr.add("Test");
        listW.addAll(0, arr);
    }

    @Test(expected = UnmodifiableElementsException.class)
    public void whenClearAndFixedPartNotEmptyThenException() {
        listW.clear();
    }

    @Test(expected = UnmodifiableElementsException.class)
    public void whenSetInUnmodifablePartThenException() {
        listW.set(0, "test");
    }

    @Test(expected = UnmodifiableElementsException.class)
    public void whenAddInUnmodifablePartThenException() {
        listW.add(0, "test");
    }

    @Test
    public void whenAddInModifablePartThenTrue() {
        listW.add(2, "test");
        assertTrue(listW.contains("test"));
    }

    @Test(expected = UnmodifiableElementsException.class)
    public void whenRemoveInUnmodifablePartThenException() {
        listW.remove(0);
    }

    @Test
    public void whenRemoveInModifablePartThenTrue() {
        assertTrue(listW.remove(2).equals("a"));
    }

    @Test(expected = UnmodifiableElementsException.class)
    public void whenRetainAllInUnmodifablePartThenException() {
        ArrayList test = new ArrayList();
        test.add(1);
        test.add("b");
        listW.retainAll(test);
    }

    @Test(expected = UnmodifiableElementsException.class)
    public void whenRemoveAllInUnmodifablePartThenException() {
        ArrayList test = new ArrayList();
        test.add(1);
        test.add("b");
        listW.retainAll(test);
    }

}
