package datastructures.dictionaries;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see IDictionary
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.

    Note: The field below intentionally omits the "private" keyword. By leaving off a specific
    access modifier like "public" or "private" it becomes package-private, which means anything in
    the same package can access it. Since our tests are in the same package, they will be able
    to test this property directly.
     */
    Pair<K, V>[] pairs;
    int size;

    // You may add extra fields or helper methods though!

    public ArrayDictionary(int initialCapacity) {
        pairs = makeArrayOfPairs(initialCapacity);
        // ... initialize any extra fields you made as necessary
    }
    private static final int DEFAULT_INITIAL_CAPACITY = 10;

    public ArrayDictionary() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * This method will return a new, empty array of the given size that can contain `Pair<K, V>`
     * objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
         arrays and generics interact. Do not modify this method in any way.
        */
        return (Pair<K, V>[]) (new Pair[arraySize]);
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        int i = indexOfKey(key);
        if (i == -1) {
            return defaultValue;
        }
        return pairs[i].value;
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            throw new NoSuchKeyException();
        }
        return pairs[indexOfKey(key)].value;
    }

    @Override
    public V put(K key, V value) {
        if (size == pairs.length) {  // if we need to resize the array
            Pair<K, V>[] newPairs = makeArrayOfPairs(2 * pairs.length);
            for (int i = 0; i < pairs.length; i++) {
                newPairs[i] = pairs[i];
            }
            pairs = newPairs;
        }
        int index = indexOfKey(key);
        if (index == -1) {
            pairs[size] = new Pair<K, V>(key, value);
            size++;
            return null;
        } else {
            V result = pairs[index].value;
            pairs[index].value = value;
            return result;
        }
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int i = indexOfKey(key);
        V result = pairs[i].value;
        pairs[i] = pairs[size - 1];
        size--;
        return result;
    }

    @Override
    public boolean containsKey(K key) {
        return indexOfKey(key) != -1;
    }
    // return the index of the key. return -1 if key not found.
    private int indexOfKey(K key) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(key, pairs[i].key)) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new ArrayDictionaryIterator<K, V>(pairs, size);
    }

    @Override
    public String toString() {
        return super.toString();

        /*
        After you've implemented the iterator, comment out the line above and uncomment the line
        below to get a better string representation for objects in assertion errors and in the
        debugger.
        */

        // return IDictionary.toString(this);
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("%s=%s", this.key, this.value);
        }
    }

    private static class ArrayDictionaryIterator<K, V> implements Iterator<KVPair<K, V>> {
        // You'll need to add some fields
        int position;
        Pair<K, V>[] pairs;
        int size;

        public ArrayDictionaryIterator(Pair<K, V>[] pairs, int size) {
            this.pairs = pairs;
            this.size = size;
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public KVPair<K, V> next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            position++;
            return new KVPair<>(pairs[position - 1].key, pairs[position - 1].value);
        }
    }
}
