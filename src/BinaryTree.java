import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.*;

//     Attention: comparable supported but comparator is not
    @SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

        private static class Node<T> {
            final T value;

            Node<T> left = null;

            Node<T> right = null;

            Node(T value) {
                this.value = value;
            }
        }

        private Node<T> root = null;

        public int size = 0;

        @Override
        public boolean add(T t) {
            Node<T> closest = find(t);
            int comparison = closest == null ? -1 : t.compareTo(closest.value);
            if (comparison == 0) {
                return false;
            }
            Node<T> newNode = new Node<>(t);
            if (closest == null) {
                root = newNode;
            } else if (comparison < 0) {
                assert closest.left == null;
                closest.left = newNode;
            } else {
                assert closest.right == null;
                closest.right = newNode;
            }
            size++;
            return true;
        }

        boolean checkInvariant() {
            return root == null || checkInvariant(root);
        }

        private boolean checkInvariant(Node<T> node) {
            Node<T> left = node.left;
            if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
            Node<T> right = node.right;
            return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
        }

        //    @Override
        public boolean remove(T value) {
            if (root == null) {
                return false;
            } else {
                boolean lessThanParent = false;
                Node parent = root;
                Node temp = parent;
                int comparison = 0;
                while (temp.value != value) {
                    parent = temp;
                    comparison = value.compareTo((T) temp.value);
                    if (comparison < 0)
                    {
                        lessThanParent = true;
                        temp = temp.left;
                    } else {
                        lessThanParent = false;
                        temp = temp.right;
                    }
                    if (temp == null)
                        return false;
                }
                //1st case - node has no children
                if (temp.left == null && temp.right == null) {
                    if (temp == root) {
                        root = null;
                    }
                    if (lessThanParent) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                }
                //2nd case - only left child
                else if (temp.left != null && temp.right == null) {
                    if (temp == root) {
                        root = temp.left;
                    }
                    if (lessThanParent) {
                        parent.left = temp.left;
                    } else {
                        parent.right = temp.left;
                    }
                }
                //3rd case - only right child
                else if (temp.left == null && temp.right != null) {
                    if (temp == root) {
                        root = temp.right;
                    }
                    if (lessThanParent) {
                        parent.left = temp.right;
                    } else {
                        parent.right = temp.right;
                    }
                }
                //4th case - has both children
                else if (temp.left != null && temp.right != null) {
                    Node leftSub = temp.right;
                    Node parentLeftSub = null;
                    while (leftSub.left != null) {
                        parentLeftSub = leftSub;
                        leftSub = leftSub.left;
                    }
                    parentLeftSub.left = null;
                    if (lessThanParent) {
                        parent.left = leftSub;
                    } else {
                        parent.right = leftSub;
                    }
                }
            }
            size--;
            return true;
        }

        @Override
        public boolean contains(Object o) {
            @SuppressWarnings("unchecked")
            T t = (T) o;
            Node<T> closest = find(t);
            return closest != null && t.compareTo(closest.value) == 0;
        }

        private Node<T> find(T value) {
            if (root == null) return null;
            return find(root, value);
        }

        private Node<T> find(Node<T> start, T value) {
            int comparison = value.compareTo(start.value);
            if (comparison == 0) {
                return start;
            } else if (comparison < 0) {
                if (start.left == null) return start;
                return find(start.left, value);
            } else {
                if (start.right == null) return start;
                return find(start.right, value);
            }
        }

        public class BinaryTreeIterator implements Iterator<T> {

            private Node<T> current = null;

            private BinaryTreeIterator() {
            }

            private Node<T> findNext() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return findNext() != null;
            }

            @Override
            public T next() {
                current = findNext();
                if (current == null) throw new NoSuchElementException();
                return current.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        @NotNull
        @Override
        public Iterator<T> iterator() {
            return new BinaryTreeIterator();
        }

        @Override
        public int size() {
            return size;
        }


        @Nullable
        @Override
        public Comparator<? super T> comparator() {
            return null;
        }

        @NotNull
        @Override
        public SortedSet<T> subSet(T fromElement, T toElement) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        @Override
        public SortedSet<T> headSet(T toElement) {
            TreeSet<Integer> set = new TreeSet<>();
            Node temp = root;
            recursiveSet(set, temp, toElement);
            return (SortedSet) set;
        }

        private void recursiveSet(TreeSet set, Node nextRoot, T key) {
            if (nextRoot != null) {
                int comparison = key.compareTo((T) nextRoot.value);

                if (comparison <= 0) {
                    recursiveSet(set, nextRoot.left, key);
                } else {
                    recursiveSet(set, nextRoot.right, key);
                    set.add(nextRoot.value);
                    recursiveSet(set, nextRoot.left, key);
                }
            }
        }

        @NotNull
        @Override
        public SortedSet<T> tailSet(T fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public T first() {
            if (root == null) throw new NoSuchElementException();
            Node<T> current = root;
            while (current.left != null) {
                current = current.left;
            }
            return current.value;
        }

        @Override
        public T last() {
            if (root == null) throw new NoSuchElementException();
            Node<T> current = root;
            while (current.right != null) {
                current = current.right;
            }
            return current.value;
        }
    }
