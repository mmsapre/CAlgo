import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


public abstract class AbstractCollection<E> extends VisitingIterator implements Iterable<E>{

	protected static final int FORE=-1;
	protected static final int NOT_FOUND=-2;
	public static final int DEFAULT_CAPACITY=8;
	protected int size=0;
	protected Comparator<? super E> comp;
	public static final Comparator<Object> DEFAULT_EQUIVALENCE_TESTER=new DefaultEquivalenceTester<Object>();
	public static final Comparator<Object> DEFAULT_COMPARATOR=new DefaultComparator<Object>();
	
	public AbstractCollection(Comparator<? super E> comparator){
		comp=comparator;
	}
	
	public boolean isEmpty(){
		return size==0;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getCapacity(){
		return Integer.MAX_VALUE;
	}
	
	public Comparator<? super E> getComparator(){
		return comp;
	}
	
	public int compare(E e1,E e2){
		return comp.compare(e1, e2);
	}
	
	public boolean equivalent(E e1,E e2){
		return comp.compare(e1, e2)==0;
	}
	
	public Object[] toArray(){
		Object[] result=new Object[getSize()];
		int i=0;
		for(E e: this)
			result[i++]=e;
		return result;
	}
	
	public final void accept(VisitingIterator visitIter){
		try{
			traverseForVisitor(visitIter);
		}catch(Throwable cause){
			
		}
	}
	
	protected void traverseForVisitor(VisitingIterator v){
		for(E e:this)
			try {
				v.visit(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	
	public void addAll(Collection<? extends E> c){
		for(E e:c)
			add(e);
	}
	
	public abstract void add(E item);
	
	public class VisitingIterator<E> implements Iterator<E>,Runnable {

		private boolean hasItem=false;
		E nextItem=null;
		boolean finished=false;
		boolean canceled=false;
		
		public VisitingIterator(){
			(new Thread(this)).start();
		}
		@Override
		public boolean hasNext() {
			if(canceled)
				return false;
			while(!hasItem && !finished)
				try{
					wait();
				}catch(InterruptedException ie){
					ie.printStackTrace();
				}
			return false;
		}

		@Override
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			hasItem=false;
			notify();
			return nextItem;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
			
		}
		@Override
		public void run() {
			try{
				AbstractCollection.this.accept(this);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			finished=true;
			notify();
			
		}
		
		public synchronized void cancel(){
			canceled=true;
			notify();
		}
		
		public synchronized void visit(E item)throws Exception{
			if(canceled)
				throw new Exception("Further iteration cancelled");
			
			nextItem=item;
			hasItem=true;
			notify();
			try{
				wait();
			}catch(InterruptedException iex){}
		}
	}
	
	public static class DefaultComparator<E> implements Comparator<E>{

		@Override
		public int compare(E o1, E o2) {
			try{
				return ((Comparable<E>)o1).compareTo(o2);
			}catch(Exception cce){
				
			}
			return 0;
			
		}
		
	}
	
	public static class DefaultEquivalenceTester<E> implements Comparator<E>{

		@Override
		public int compare(E o1, E o2) {
			if(Objects.equals(o1, o2))
				return 0;
			else
				return -1;
			
		}
		
	}

	

}
