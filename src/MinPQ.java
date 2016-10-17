import java.util.Comparator;
import java.util.NoSuchElementException;


public class MinPQ<Key> {

	private Key[] priorMinQueue;
	private int N;
	private Comparator<Key> compare;
	
	public MinPQ(int initialCapacity){
		priorMinQueue=(Key[])new Object[initialCapacity+1];
	}
	
	public MinPQ(){
		this(1);
	}
	
	public MinPQ(int initialCapacity,Comparator<Key> aCompare){
		this.compare=aCompare;
		priorMinQueue=(Key[])new Object[initialCapacity+1];
		N=0;
	}
	
	public MinPQ(Comparator<Key> aCompare){
		this(1,aCompare);
	}
	
	public boolean isEmpty(){
		return N==0;
	}
	
	public int size(){
		return N;
	}
	
	public Key min(){
		if(isEmpty()){
			throw new NoSuchElementException("Queue is empty.....");
		}
		return priorMinQueue[1];
	}
	
	public void insert(Key aKey){
		priorMinQueue[++N]=aKey;
	}
	
	
	public void swim(int size){
		while(size>1 && compareKey(size,size/2)){
			Key temp=priorMinQueue[size/2];
			priorMinQueue[size/2]=priorMinQueue[size];
			priorMinQueue[size]=temp;
			size=size/2;
		}
	}
	
	public boolean compareKey(int i,int j){
		if(compare!=null){
			return ((Comparable<Key>) priorMinQueue[i]).compareTo(priorMinQueue[j])<0;
		}else{
			return compare.compare(priorMinQueue[i],priorMinQueue[j])<0;
		}
	}
	
	
	
}
