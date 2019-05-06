import org.junit.Before;
import org.junit.Test;
import part2.ModifiableContainer;
import part2.exception.UnmodifiablePartException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MixedListsTest {
    private List<Object> tmp = new ArrayList<>();
    private ModifiableContainer<Object> mixedLists = new ModifiableContainer<>(tmp, new ArrayList<>());

    @Test
    public void checkAddMethod() {
        assertTrue(mixedLists.add("hello"));
        assertTrue(mixedLists.add("hello2"));
        assertTrue(mixedLists.add("hello3"));
        assertFalse(mixedLists.isEmpty());
        assertEquals(12, mixedLists.size());
    }

    @Test
    public void checkAddByIndexMethod() {
        mixedLists.add(3, "add by index");
        assertEquals("add by index", mixedLists.get(3));
        assertFalse(mixedLists.isEmpty());
        assertEquals(10, mixedLists.size());
    }

    @Test
    public void checkIndexOffMethod() {
        assertEquals(-1, mixedLists.indexOf("skfdns"));
        assertEquals(-1, mixedLists.indexOf("sk"));
        assertEquals(6, mixedLists.indexOf("hello4"));
    }

    @Test
    public void checkLastIndexOffMethod() {
        assertEquals(8, mixedLists.lastIndexOf("hello"));
    }

    @Test
    public void checkRemoveByIndexMethod() {
        assertEquals("hello2", mixedLists.remove(4));
    }

    @Test
    public void checkRemoveObjectMethod() {
        assertTrue(mixedLists.remove("hello"));
    }

    @Test
    public void checkRemoveAllMethod() {
        List<Object> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("hello2");
        arrayList.add("hello3");
        assertTrue(mixedLists.removeAll(arrayList));
        assertEquals(-1, mixedLists.indexOf("hello3"));
    }

    @Test(expected = UnmodifiablePartException.class)
    public void checkRemoveObjectMethod2() {
        assertTrue(mixedLists.remove("test"));
    }

    @Test
    public void checkRetainAllMethod() {
        List<Object> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("hello2");
        arrayList.add("hello3");
        assertTrue(mixedLists.retainAll(arrayList));
        assertEquals(-1, mixedLists.indexOf("hello4"));
    }

    @Test
    public void checkSetMethod() {
        assertEquals("hello2", mixedLists.set(4, "set Test"));
        assertEquals("set Test", mixedLists.get(4));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkSetMethod2() {
        assertEquals("hello2", mixedLists.set(50, "set Test"));
    }

    @Test
    public void checkClearMethod() {
        mixedLists.clear();
        assertEquals(tmp.size(), mixedLists.size());
    }

    @Test
    public void checkContainsMethod() {
        assertTrue(mixedLists.contains("test3"));
        assertTrue(mixedLists.contains("hello"));
        assertTrue(mixedLists.contains("hello2"));
        assertFalse(mixedLists.contains("hello22"));
    }

    @Test
    public void checkContainsAllMethod() {
        List<Object> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("hello2");
        arrayList.add("hello3");
        arrayList.add("hello4");
        arrayList.add("hello5");
        arrayList.add("hello");
        assertTrue(mixedLists.containsAll(arrayList));
        assertTrue(mixedLists.containsAll(tmp));
    }

    @Test
    public void checkToArrayMethod() {
        assertArrayEquals(new Object[]{"test", "test2", "test3", "hello", "hello2",
                "hello3", "hello4", "hello5", "hello"}, mixedLists.toArray());
    }

    @Test
    public void checkAddAllMethod() {
        List<Object> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("hello2");
        arrayList.add("hello3");
        assertTrue(mixedLists.addAll(arrayList));
    }

    @Test
    public void checkAddAllByIndexMethod() {
        List<Object> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("hello2");
        arrayList.add("hello3");
        assertTrue(mixedLists.addAll(5, arrayList));
        assertEquals("hello3", mixedLists.get(8));
    }

    @Test
    public void checkIterator() {
        Iterator iterator = mixedLists.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("test", iterator.next());
        assertEquals("test2", iterator.next());
        assertEquals("test3", iterator.next());
        assertEquals("hello", iterator.next());

    }

    @Before
    public void add() {
        tmp.add("test");
        tmp.add("test2");
        tmp.add("test3");
        mixedLists.add("hello");
        mixedLists.add("hello2");
        mixedLists.add("hello3");
        mixedLists.add("hello4");
        mixedLists.add("hello5");
        mixedLists.add("hello");
    }
}
