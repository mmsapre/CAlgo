import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class BinaryHeap<E> extends AbstractCollection implements PriorityQueue<E> {

	private Array<E> bh;
	
	public BinaryHeap(int initialCapacity,Comparator<? super E> comp){
		super(comp);
		bh=new DynamicArray<E>(initialCapacity, comp); 
	}
	
	public BinaryHeap(){
		this(DEFAULT_CAPACITY, DEFAULT_COMPARATOR);
	}
	
	public BinaryHeap(Comparator<? super E> comp){
		this(DEFAULT_CAPACITY, comp);
	}
	
	public BinaryHeap(int initialCapacity){
		this(initialCapacity,DEFAULT_COMPARATOR);
	}
	
	@Override
	public E extractMax() {
		bh.swap(0, bh.getSize()-1);
		E data=bh.removeLast();
		fixDownward(0);
		return data;
	}

	@Override
	public E max() {
		if(isEmpty())
			throw new NoSuchElementException();
		return bh.get(0);
	}
	
	protected int find(E element){
		for(int i=0;i<bh.getSize();i++){
			if(equivalent(element, bh.get(i)))
				return i;
		}
		return NOT_FOUND;
	}
	
	public boolean contains(E element){
		return bh.contains(element);
	}
	
	public E getEquivalentElement(E target){
		int index=find(target);
		if(index==NOT_FOUND)
			throw new NoSuchElementException();
		else
			return (E)bh.get(index);
			
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(Object item) {
		bh.addLast((E) item);
		fixUpward(bh.getSize()-1);
	}
	

	public int getSize(){
		return bh.getSize();
	}
	
	public boolean isEmpty(){
		return bh.isEmpty();
	}

	private int parent(int i){
		return((int)(i-1)/2);
	}
	
	private int left(int i){
		return 2*i+1;
	}
	
	private int right(int i){
		return 2*i+2;
	}
	
	private void fixUpward(int i){
		while((i>0) && comp.compare(bh.get(i), bh.get(parent(i)))>0){
			bh.swap(i, parent(i));
			i=parent(i);
		}
	}
	
	private void fixDownward(int i){
		int max=i;
		while(true){
			int left=left(i);
			int right=left+1;
			if(right>bh.getSize())
				return;
			else if(right==bh.getSize() || comp.compare(bh.get(left),bh.get(right))>-1){
				max=left;
			}else
				max=right;
			if(comp.compare(bh.get(i), bh.get(max))<0){
				bh.swap(i, max);
				i=max;
			}else
				return;
		}
	}
	
	protected void update(int p,E element){
		bh.set(p,element);
		if(comp.compare(element, bh.get(parent(p)))>0)
			fixUpward(p);
		else
			fixDownward(p);
	}
	
	protected void remove(int p){
		E removed=bh.get(p);
		int lastPos=getSize()-1;
		if(p==lastPos)
			bh.removeLast();
		else{
			bh.swap(lastPos, p);
			bh.removeLast();
			if(comp.compare(bh.get(p),removed)>=0)
				fixUpward(p);
			else
				fixDownward(p);

		}
	}
	
	public boolean remove(E target){
		int p=find(target);
		if(p==NOT_FOUND)
			return false;
		else{
			remove(p);
			return true;
		}
	}
	
	
}
