package datastructures.dictionaries;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @see IDictionary and the assignment page for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You'll need to define reasonable default values for each of the following three fields
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1.0;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 1;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 1;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.

    Note: The field below intentionally omits the "private" keyword. By leaving off a specific
    access modifier like "public" or "private" it becomes package-private, which means anything in
    the same package can access it. Since our tests are in the same package, they will be able
    to test this property directly.
     */
    IDictionary<K, V>[] chains;
    private double resizingLoadFactorThreshold;
    private int chainInitialCapacity;
    private int size;

    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashDictionary() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    public ChainedHashDictionary(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;
        this.chainInitialCapacity = chainInitialCapacity;
        chains = makeArrayOfChains(initialChainCount);
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * `IDictionary<K, V>` objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int arraySize) {
        /*
        Note: You do not need to modify this method. See `ArrayDictionary`'s `makeArrayOfPairs`
        method for more background on why we need this method.
        */
        return (IDictionary<K, V>[]) new IDictionary[arraySize];
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            throw new NoSuchKeyException();
        }
            return chains[keyHash(key)].get(key);
    }

    private int keyHash(K key){
       if (key == null) {
           return 0;
       }
       return Math.abs(key.hashCode()) % chains.length;
    }

    @Override
    public V put(K key, V value) {
        if (!containsKey(key)) {
            if (1.0 * size / chains.length >= resizingLoadFactorThreshold) {
                resize();
            }
            if (chains[keyHash(key)] == null) {
                chains[keyHash(key)] = new ArrayDictionary<>(chainInitialCapacity);
            }
            size++;
        }
        return chains[keyHash(key)].put(key, value);
    }

    private void resize() {
        IDictionary<K, V>[] temp = chains;
        chains = makeArrayOfChains(chains.length * 2);
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                for (KVPair<K, V> pair : temp[i]) {
                    int keyHash = keyHash(pair.getKey());
                    if (chains[keyHash] == null) {
                        chains[keyHash] = new ArrayDictionary<>(chainInitialCapacity);
                    }
                    chains[keyHash].put(pair.getKey(), pair.getValue());
                }
            }
        }

    }
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        size--;
        return chains[keyHash(key)].remove(key);
    }

    @Override
    public boolean containsKey(K key) {
        return chains[keyHash(key)]!= null && (chains[keyHash(key)].containsKey(key));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        //return new ChainedIterator<>(this.chains);
        return new ChainedIterator<>(this.chains, size);
    }

    @Override
    public String toString() {
        //return super.toString();

        /*
        After you've implemented the iterator, comment out the line above and uncomment the line
        below to get a better string representation for objects in assertion errors and in the
        debugger.
        */

        return IDictionary.toString(this);
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        int position;
        int itemsLeft;
        Iterator<KVPair<K, V>> bucket;

        // You may add more fields and constructor parameters
        public ChainedIterator(IDictionary<K, V>[] chains, int size) {
            this.chains = chains;
            this.itemsLeft = size;
            position = 0;
            bucket = null;
        }


        @Override
        public boolean hasNext() {
            return itemsLeft > 0;
        }


        @Override
        public KVPair<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (bucket == null || !bucket.hasNext()) {
                while (chains[position] == null) {
                    position++;
                }
                bucket = chains[position].iterator();
                position++;
            }
            itemsLeft--;
            return bucket.next();
        }
    }
}

