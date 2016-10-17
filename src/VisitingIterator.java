import java.util.Iterator;
import java.util.NoSuchElementException;


public class VisitingIterator<E> implements Iterator<E> {

	private boolean hasItem=false;
	E nextItem=null;
	boolean finished=false;
	boolean canceled=false;
	
	public VisitingIterator(){
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
