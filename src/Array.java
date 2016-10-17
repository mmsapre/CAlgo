import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Array<E> extends AbstractCollection<E> {

	protected Object[] a;
	private Version version;

	public Array(int capacity, Comparator<? super E> equivalenceTester) {
		super(equivalenceTester);
		resizeArray(capacity);
		size = 0;
		version = new Version();
	}

	public Array(int capacity) {
		this(capacity, DEFAULT_EQUIVALENCE_TESTER);
	}

	public Array() {
		this(DEFAULT_CAPACITY, DEFAULT_EQUIVALENCE_TESTER);
	}

	public int getCapacity() {
		return a.length;
	}

	protected final E read(int p) {
		return (E) a[getIndex(p)];
	}

	public E get(int p) {
		if (p < 0 || p >= size)
			System.out.println("Position not correct....");
		return read(p);
	}

	protected int getIndex(int p) {
		return p;
	}

	protected int getPosition(int index) {
		return index;
	}

	public int nextIndex(int index) {
		return index + 1;
	}

	protected int findPosition(E value) {
		for (int pos = 0; pos < size; pos++) {
			if (equivalent(value, read(pos)))
				return pos;
		}
		return -1;
	}

	public void trimToSize() {
		if (size != a.length) {
			resizeArray(size);
		}
	}

	public boolean contains(E value) {
		return (findPosition(value) != -1);
	}

	public int positionOf(E value) {
		int loc = findPosition(value);
		if (loc == -1)
			throw new NoSuchElementException();
		return loc;
	}

	protected Object[] moveElementsTo(Object[] newArray) {
		if (a != null)
			System.arraycopy(a, 0, newArray, 0, size);
		return newArray;
	}

	protected void resizeArray(int capacity) {
		if (capacity < size)
			throw new IllegalArgumentException("Capacity < size");
		a = moveElementsTo(new Object[capacity]);
	}

	protected void createGap(int p) {
		System.arraycopy(a, p, a, p + 1, size - p);
	}

	protected void add(int p, E value) {
		if (p < 0 || p > size)
			throw new IllegalArgumentException();
		if (p != size)
			createGap(p);
		a[getIndex(p)] = value;
		size++;
	}

	protected void closeGap(int fromPos, int toPos) {
		int numElements = size - toPos - 1;
		if (numElements != 0)
			System.arraycopy(a, toPos + 1, a, fromPos, numElements);
		Arrays.fill(a, fromPos + numElements, size, null);
	}

	public void removeRange(int fromPos, int toPos) {
		if (fromPos < 0 || toPos >= size)
			throw new IllegalArgumentException();
		if (fromPos > toPos)
			throw new IllegalArgumentException();
		closeGap(fromPos, toPos);
		size = size - (toPos - fromPos + 1);
		if (toPos != size - 1)
			version.increment();
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(E item) {
		add(size, item);

	}
	
	
	public void addLast(E item) {
		add(size, item);

	}

	public E remove(int p) {
		if (p < 0 || p >= size)
			throw new IllegalArgumentException();
		E removeElement = get(p);
		removeRange(p, p);
		return removeElement;
	}

	public final E removeFirst() {
		if (!isEmpty())
			throw new NoSuchElementException();
		return remove(0);
	}

	public final E removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		return remove(size - 1);
	}

	public boolean remove(E value) {
		int position = findPosition(value);
		if (position == NOT_FOUND)
			return false;
		removeRange(position, position);
		return true;
	}

	public void ensureCapacity(int capacity) {
		resizeArray(capacity);
	}

	public void swap(int pos1,int pos2){
		if(pos1<0 || pos2>=size)
			throw new NoSuchElementException();
		if(pos2<0 || pos2>=size)
			throw new NoSuchElementException();
		
	}
	protected void swapImpl(int pos1,int pos2){
		if(pos1==pos2) return;
		int indx1=getIndex(pos1);
		int indx2=getIndex(pos2);
		Object tmp=a[indx1];
		a[indx1]=a[indx2];
		a[indx2]=tmp;
	}
	
	public E set(int p,E value){
		if(p<0 || p>=size)
			throw new NoSuchElementException();
		int indx=getIndex(p);
		E oldVal=(E)a[indx];
		a[indx]=value;
		return oldVal;
	}
}
