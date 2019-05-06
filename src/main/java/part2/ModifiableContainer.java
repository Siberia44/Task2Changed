package part2;

import part2.exception.UnmodifiablePartException;

import java.util.*;

public class ModifiableContainer<A> implements List<A> {
    private List<A> modifiableList;
    private List<A> unmodifiableList;

    public ModifiableContainer(List<A> unmodifiableList, List<A> modifiableList) {
        if (modifiableList == null || unmodifiableList == null) {
            throw new NullPointerException();
        }
        this.modifiableList = modifiableList;
        this.unmodifiableList = unmodifiableList;
    }

    @Override
    public int size() {
        return modifiableList.size() + unmodifiableList.size();
    }

    @Override
    public boolean isEmpty() {
        return (unmodifiableList.size() + modifiableList.size()) == 0;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) != -1;
    }

    @Override
    public Iterator<A> iterator() {
        return new IteratorImpl<A>();
    }

    @Override
    public Object[] toArray() {
        List<A> list = additionLists();
        return list.toArray();
    }

    public <T> T[] toArray(T[] collection) {
        List<A> list = additionLists();
        return (T[]) Arrays.copyOf(list.toArray(), list.size(), collection.getClass());
    }

    @Override
    public boolean add(A object) {
        modifiableList.add(object);
        return true;
    }

    @Override
    public boolean remove(Object object) {
        int index = indexOf(object);
        if (index < unmodifiableList.size()) {
            throw new UnmodifiablePartException("Can't change unmodifiable part. " +
                    "Unmodifiable size: " + unmodifiableList.size() + ", index: " + index);
        }
        return modifiableList.remove(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        Objects.requireNonNull(collection);
        List<A> list = additionLists();
        return list.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends A> collection) {
        Objects.requireNonNull(collection);
        return modifiableList.addAll(collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends A> collection) {
        Objects.requireNonNull(collection);
        if (index >= unmodifiableList.size()) {
            return modifiableList.addAll(index - unmodifiableList.size(), collection);
        }
        throw new UnmodifiablePartException("Can't change unmodifiable part. " +
                "Unmodifiable size: " + unmodifiableList.size() + ", index: " + index);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Objects.requireNonNull(collection);
        for (Object object : collection) {
            if (unmodifiableList.contains(object)) {
                throw new UnmodifiablePartException("Unmodifiable part have object from input collection");
            }
        }
        return modifiableList.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Objects.requireNonNull(collection);
        for (Object object : collection) {
            if (unmodifiableList.contains(object)) {
                throw new UnmodifiablePartException("Unmodifiable part have object from input collection");
            }
        }
        return modifiableList.retainAll(collection);
    }

    @Override
    public void clear() {
        if (unmodifiableList.size() == 0) {
            modifiableList.clear();
            return;
        }
        throw new UnmodifiablePartException("Unmodifiable part have objects");
    }

    @Override
    public A get(int index) {
        if (index < unmodifiableList.size()) {
            return unmodifiableList.get(index);
        }
        return modifiableList.get(index - unmodifiableList.size());
    }

    @Override
    public A set(int index, A element) {
        if (index >= unmodifiableList.size()) {
            return modifiableList.set(index - unmodifiableList.size(), element);
        }
        throw new UnmodifiablePartException("Can't change unmodifiable part." +
                " Unmodifiable size: " + unmodifiableList.size() + ", index: " + index);
    }

    @Override
    public void add(int index, A element) {
        if (index >= unmodifiableList.size()) {
            modifiableList.add(index - unmodifiableList.size(), element);
        } else {
            throw new UnsupportedOperationException("Can't change unmodifiable part." +
                    "Unmodifiable size: " + unmodifiableList.size() + ", index: " + index);
        }
    }

    @Override
    public A remove(int index) {
        if (index >= unmodifiableList.size()) {
            return modifiableList.remove(index - unmodifiableList.size());
        } throw new UnsupportedOperationException("Can't change unmodifiable part." +
                    "Unmodifiable size: " + unmodifiableList.size() + ", index: " + index);
    }

    @Override
    public int indexOf(Object object) {
        int index = unmodifiableList.indexOf(object);
        if (index != -1) {
            return index;
        }
        index = modifiableList.indexOf(object);
        return index != -1 ? index + unmodifiableList.size() : -1;
    }

    @Override
    public int lastIndexOf(Object inputObject) {
        int index = modifiableList.lastIndexOf(inputObject);
        if (index != -1) {
            return index + unmodifiableList.size();
        }
        return unmodifiableList.lastIndexOf(inputObject);
    }

    @Override
    public ListIterator<A> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<A> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<A> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class IteratorImpl<A> implements Iterator<A> {

        private int indexForIterator;
        private boolean lastRet = false;

        @Override
        public boolean hasNext() {
            return indexForIterator < size();
        }

        @Override
        public A next() {
            if (hasNext()) {
                lastRet = true;
                return (A) get(indexForIterator++);
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            if (!lastRet) {
                throw new IllegalStateException();
            }
            if (indexForIterator < unmodifiableList.size()) {
                throw new UnmodifiablePartException("Can't change unmodifiable part.");
            }
            modifiableList.remove(indexForIterator);
            lastRet = false;
        }

    }

    private List<A> additionLists() {
        List<A> list = new ArrayList<A>(unmodifiableList);
        list.addAll(modifiableList);
        return list;
    }
}
