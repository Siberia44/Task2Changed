package part1;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class COWList<T> implements List<Object> {
    private Object[] array;
    private int arrayIncrease = 1;

    public COWList() {
        array = new Object[0];
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator iterator() {
        return new IteratorImpl(getArray());
    }

    @Override
    public Object[] toArray() {
        Object[] tmpArray = getArray();
        return Arrays.copyOf(tmpArray, tmpArray.length);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        Object[] tmpArray = getArray();
        if (array.length < tmpArray.length)
            return (T[]) Arrays.copyOf(tmpArray, tmpArray.length, array.getClass());
        System.arraycopy(tmpArray, 0, array, 0, tmpArray.length);
        if (array.length > tmpArray.length)
            array[tmpArray.length] = null;
        return array;
    }

    @Override
    public boolean add(Object element) {
        Object[] tmpArray = getArray();
        tmpArray = Arrays.copyOf(tmpArray, tmpArray.length + arrayIncrease);
        tmpArray[tmpArray.length - 1] = element;
        setArray(tmpArray);
        return true;
    }

    @Override
    public boolean remove(Object inputObject) {
        Object[] tmpArray = getArray();
        int foundIndex = indexOf(inputObject);
        if (foundIndex != -1) {
            Object[] newArray = new Object[array.length-1];
            System.arraycopy(tmpArray, foundIndex + 1, newArray, foundIndex, tmpArray.length - 1 - foundIndex);
            setArray(newArray);
            return true;
        }
        return false;
    }

    private Object[] remove(Object[] elements, int i) {
        System.arraycopy(elements, i + 1, elements, i, elements.length - i - 1);
        elements = Arrays.copyOf(elements, elements.length - 1);
        return elements;
    }

    @Override
    public boolean containsAll(Collection inputCollection) {
        for (Object object : inputCollection) {
            if (!contains(object)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection collection) {
        Object[] tmpArray = getArray();
        Object[] newArray = Arrays.copyOf(tmpArray, tmpArray.length + collection.size());
        System.arraycopy(collection.toArray(), 0, newArray, tmpArray.length, collection.size());
        setArray(newArray);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection collection) {
        checkIndex(index);
        Object[] tmpArray = getArray();
        int newLength = tmpArray.length + collection.size();
        Object[] newArray = Arrays.copyOf(tmpArray, newLength);
        System.arraycopy(newArray, index, newArray, index + collection.size(), tmpArray.length - index);
        System.arraycopy(collection.toArray(), 0, newArray, index, collection.size());
        setArray(newArray);
        return true;
    }

    @Override
    public boolean removeAll(Collection c) {
        Object[] tmpArray = getArray();
        Object[] newArray = new Object[tmpArray.length];
        int length = 0;
        for (int i = 0; i < newArray.length; i++) {
            if (!c.contains(tmpArray[i]))
                newArray[length++] = tmpArray[i];
        }
        setArray(Arrays.copyOf(newArray, length));
        return true;
    }

    @Override
    public boolean retainAll(Collection c) {
        Object[] tmpArray = getArray();
        Object[] newArray = new Object[tmpArray.length];
        int length = 0;
        for (int i = 0; i < newArray.length; i++) {
            if (c.contains(tmpArray[i])) {
                newArray[length++] = tmpArray[i];
            }
        }
        setArray(Arrays.copyOf(newArray, length));
        return true;
    }

    @Override
    public void clear() {
        setArray(new Object[0]);
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        Object[] tmpArray = getArray();
        return tmpArray[index];
    }

    @Override
    public Object set(int index, Object element) {
        checkIndex(index);
        Object[] tmpArray = getArray();
        Object previousElement = tmpArray[index];
        tmpArray[index] = element;
        setArray(tmpArray);
        return previousElement;
    }

    @Override
    public void add(int index, Object element) {
        checkIndex(index);
        Object[] tmpArray = getArray();
        Object[] newArray = new Object[tmpArray.length + 1];
        System.arraycopy(tmpArray, index, newArray, index + 1, tmpArray.length - index);
        newArray[index] = element;
        setArray(newArray);
    }

    @Override
    public Object remove(int index) {
        checkIndex(index);
        Object[] tmpArray = getArray();
        Object deletedElement = tmpArray[index];
        System.arraycopy(tmpArray, index + 1, tmpArray, index, tmpArray.length - index);
        setArray(tmpArray);
        return deletedElement;
    }

    @Override
    public int indexOf(Object element) {
        Object[] tmpArray = getArray();
        if (isNull(element)) {
            for (int i = 0; i < tmpArray.length; i++)
                if (isNull(tmpArray[i]))
                    return i;
        } else {
            for (int i = 0; i < tmpArray.length; i++)
                if (element.equals(tmpArray[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object element) {
        Object[] tmpArray = getArray();
        if (!nonNull(element)) {
            for (int i = tmpArray.length - 1; i >= 0; i--)
                if (!nonNull(tmpArray[i]))
                    return i;
        } else {
            for (int i = tmpArray.length - 1; i >= 0; i--)
                if (element.equals(tmpArray[i]))
                    return i;
        }
        return -1;
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

    private void setArray(Object[] changeArray) {
        array = changeArray;
    }

    private Object[] getArray() {
        return array;
    }

    private void checkIndex(int index) {
        if (index > array.length || index < 0) {
            throw new IndexOutOfBoundsException("Index is " + index + ", but size is " + array.length);
        }
    }

    private boolean changeArrayIfElementIsNull(Object element) {
        Object[] tmpArray = getArray();
        for (int i = 0; i < tmpArray.length; i++) {
            if (element == tmpArray[i]) {
                tmpArray = remove(tmpArray, i);
                setArray(tmpArray);
                return true;
            }
        }
        return false;
    }

    private boolean changeArrayIfElementIsNotNull(Object element) {
        Object[] tmpArray = getArray();
        for (int i = 0; i < tmpArray.length; i++) {
            if (element.equals(tmpArray[i])) {
                tmpArray = remove(tmpArray, i);
                setArray(tmpArray);
                return true;
            }
        }
        return false;
    }

    class IteratorImpl implements Iterator {
        private int currentIndex = 0;
        private boolean callFlag;
        private Object[] snapshot;

        public IteratorImpl(Object[] snapshot) {
            this.snapshot = snapshot;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < snapshot.length;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return snapshot[currentIndex++];
        }

        @Override
        public void remove() {
            if (callFlag) {
                throw new IllegalStateException();
            } else {
                callFlag = true;
                COWList.this.remove(currentIndex--);
            }
        }
    }
}