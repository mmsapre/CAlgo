import java.util.Comparator;


public class DynamicArray<E> extends Array<E> {

	int minCapacity;
	int lowWaterMark;
	int highWaterMark;
	
	public DynamicArray(int capacity,Comparator<? super E> equivalenceTester){
		super(capacity);
		minCapacity=capacity;
	}
	
	public DynamicArray(){
		this(DEFAULT_CAPACITY);
	}
	
	public DynamicArray(int capacity){
		this(capacity, DEFAULT_EQUIVALENCE_TESTER);
	}
	
	protected  void resizeArray(int desiredCapacity){
		super.resizeArray(desiredCapacity);
		lowWaterMark=(int)Math.ceil(a.length/4);
		highWaterMark=a.length;
	}
	
	public void ensureCapacity(int capacity){
		minCapacity=capacity;
		if(a.length<capacity)
			super.ensureCapacity(capacity);
	}
	
	protected void add(int p, E value) {
		if(size==highWaterMark)
			resizeArray(2*size);
		super.add(p, value);
	}
	
	public void removeRange(int fromPos,int toPos){
		super.removeRange(fromPos, toPos);
		if(size<=lowWaterMark)
			resizeArray(Math.max(2*size, minCapacity));
	}
}
