import org.junit.Before;
import org.junit.Test;
import part1.Beer;
import part2.ModifiableContainer;
import part2.exception.UnmodifiablePartException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MixedListsTest { 
    private ModifiableContainer<Beer> mixed;
    private Beer product;
    private Beer product1;
    private Beer product2;
    private Beer product3;
    List<Beer> unmodif;
    List<Beer> modif;
    List<Beer> collection;


    @Before
    public void initialize() {
        product = new Beer();
        product1 = new Beer();
        product2 = new Beer();
        product3 = new Beer();
        product.setName("beer");
        product1.setName("beer1");
        product2.setName("beer2");
        product3.setName("beer3");
        unmodif = new ArrayList<>();
        unmodif.add(product);
        unmodif.add(product1);
        modif = new ArrayList<>();
        modif.add(product2);
        modif.add(product3);
        collection = new ArrayList<>();
        collection.add(product);
        collection.add(product3);
        mixed = new ModifiableContainer<>(unmodif, modif);
    }

    @Test
    public void shouldReturnSize() {
        assertEquals(4, mixed.size());
    }

    @Test
    public void shouldReturnFalseIfCollectionNotIsEmpty() {
        assertFalse(mixed.isEmpty());
    }

    @Test
    public void shouldReturnTrueIfCollectionContainsObject() {
        assertTrue(mixed.contains(product));
    }

    @Test
    public void shouldRemoveObjectFromCollection() {
        mixed.remove(product2);
        assertEquals(3, mixed.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionIfYouTryRemoveObjectFromUnmodCollection() {
        mixed.remove(product3);
    }

    @Test
    public void shouldAddObjectAtTheEndOfTheList() {
        mixed.add(product3);
        assertTrue(mixed.contains(product3));
    }

    @Test
    public void shouldAddObjectByIndex() {
        mixed.add(2, product3);
        assertTrue(mixed.contains(product3));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void throwExceptionIfYouTryAddObjectInUnmodCollection() {
        mixed.add(3, product3);
    }

    @Test
    public void shouldIterator() {
        Iterator iter = mixed.iterator();
        assertTrue(iter.hasNext());
        assertEquals(product, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(product1, iter.next());
    }

    @Test
    public void shouldRemoveByIndex() {
        mixed.remove(2);
        assertEquals(3, mixed.size());
    }

    @Test
    public void shouldGetObjectByIndex() {
        assertEquals(product1, mixed.get(1));
    }

    @Test
    public void shouldRemoveByObject() {
        mixed.remove(product2);
        assertEquals(3, mixed.size());
    }

    @Test(expected = UnmodifiablePartException.class)
    public void shouldThrowExceptionIfYouTryRemoveByObjectFromUnmodCollection() {
        mixed.remove(product);
    }

    @Test
    public void shouldSetTheObjectByIndex() {
        mixed.set(2, product3);
        assertTrue(mixed.contains(product3));
        assertEquals(2, mixed.indexOf(product3));
    }

    @Test
    public void shouldReturnIndexObject() {
        assertEquals(2, mixed.indexOf(product2));
        assertEquals(0, mixed.indexOf(product));
    }

    @Test
    public void shouldReturnLastIndexObject() {
        assertEquals(2, mixed.lastIndexOf(product2));
        assertEquals(0, mixed.lastIndexOf(product));
    }

    @Test
    public void shouldReturnNegativeNumberIfObjectNotContainsInCollection() {
        Beer productNotExist = new Beer();
        productNotExist.setName("beer56");
        assertEquals(-1, mixed.indexOf(productNotExist));
    }

    @Test
    public void shouldAddCollectionAtTheEndOfTheList() {
        mixed.addAll(collection);
        assertEquals(6, mixed.size());
    }

    @Test
    public void shouldRemoveAllWhatHaveIncomingCollectionFromCollection() {
        collection.remove(product);
        mixed.removeAll(collection);
        assertEquals(3, mixed.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotRemoveAllWhatHaveIncomingCollectionFromCollection() {
        collection.remove(product);
        mixed.removeAll(collection);
    }
   
}
