import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode<K, V> {
        private K key;
        private V val;

        public BSTNode<K, V> left;
        public BSTNode<K, V> right;

        public BSTNode() {
            left = right = null;
        }

        public BSTNode(K key, V val) {
            this.key = key;
            this.val = val;
            left = right = null;
        }

        public BSTNode(K key, V val, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private BSTNode<K, V> root;
    private int size;

    public BSTMap() {
        size = 0;
        root = null;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        root = put(key, value, root);
    }

    private BSTNode<K, V> put(K key, V value, BSTNode<K, V> node) {
        if (node == null) {
            size += 1;
            return new BSTNode<>(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0)        node.left = put(key, value, node.left);
        else if (cmp > 0)   node.right = put(key, value, node.right);
        else                node.val = value;
        return node;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        if (containsKey(key)) {
            return find(key).val;
        } else return null;
    }

    private BSTNode<K, V> find(K key) {
        BSTNode<K, V> p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp == 0) return p;
            else if (cmp > 0) p = p.right;
            else p = p.left;
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        if (find(key) == null) return false;
        else return true;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        BSTNode<K, V> target = find(key);

        if (target == null) return null;

        V value = target.val;

        root = remove(root, key);

        size -= 1;

        return value;
    }

    private BSTNode<K, V> remove(BSTNode<K, V> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0)        node.left = remove(node.left, key);
        else if (cmp > 0)   node.right = remove(node.right, key);
        else {
            if (node.left == null)  return node.right;
            if (node.right == null) return node.left;

            BSTNode<K, V> successor = node.right;

            while (successor.left != null) {
                successor = successor.left;
            }

            node.key = successor.key;
            node.val = successor.val;

            node.right = remove(node.right, successor.key);
        }

        return node;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
