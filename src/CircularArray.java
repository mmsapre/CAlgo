import java.util.Arrays;
import java.util.Comparator;


public class CircularArray<E> extends Array{

	int start;
	public CircularArray(){
		this(DEFAULT_CAPACITY,DEFAULT_EQUIVALENCE_TESTER);
	}
	
	public CircularArray(int capacity,Comparator<? super E> equivalenceTester){
		super(capacity,equivalenceTester);
	}
	
	public CircularArray(int capacity){
		this(capacity,DEFAULT_EQUIVALENCE_TESTER);
	}
	
	protected final int getIndex(int p){
		int loc=start+p;
		if(loc<a.length)
			return loc;
		else
			return loc-a.length;		
	}
	
	protected final int getPosition(int index){
		int pos=index-start;
		if(pos>=0)
			return pos;
		else
			return pos+a.length;
	}
	
	protected final int prevIndex(int index){
		if(index>0)
			return index-1;
		else
			return a.length-1;
	}
	
	protected Object[] moveElementsTo(Object[] newArray){
		if(a!=null){
			if(start+size<=a.length)
				System.arraycopy(a, start, newArray, 0, size);
			else{
				System.arraycopy(a, start, newArray, 0, a.length-start);
				System.arraycopy(a, 0, newArray, a.length-start,size- a.length+start);
			}
		}
		start=0;
		return newArray;
	}
	
	protected void shiftLeft(int p1,int p2,int num){
		int numToMove=p2-p1+1;
		int i=getIndex(p1);
		int j=getIndex(p2);
		int cap=a.length;
		if(i<=j){
			if(i>=num)
				System.arraycopy(a, i, a,i-num, numToMove);
			else if(num>=j+1)
				System.arraycopy(a, i, a,i-num+cap, numToMove);
			else{
				System.arraycopy(a, i, a,i-num+cap, num-i);
				System.arraycopy(a, num, a,0, numToMove-(num-i));
			}
		}else{
			System.arraycopy(a, i, a,i-num, cap-i);
			if(num>j)
				System.arraycopy(a, 0, a,cap-num, j+1);
			else{
				System.arraycopy(a, 0, a,cap-num, num);
				System.arraycopy(a, num, a,0, j+1-num);
			}
		}
	}
	
	protected void shiftRight(int p1,int p2,int num){
		int numToMove=p2-p1+1;
		int i=getIndex(p1);
		int j=getIndex(p2);
		int cap=a.length;
		if(i<=j){
			if(num<=cap-j-1)
				System.arraycopy(a, i, a,i+num, numToMove);
			else if(num>=cap-i)
				System.arraycopy(a, i, a,i+num-cap, numToMove);
			else{
				System.arraycopy(a, cap-num, a,0, numToMove-cap+num+i);
				System.arraycopy(a, i, a,num+i, cap-(num+i));
			}
		}else{
			System.arraycopy(a, 0, a,num, j+1);
			if(num>=cap-i)
				System.arraycopy(a, i, a,0, num);
			else{
				System.arraycopy(a,cap-num+1, a,0, num);
				System.arraycopy(a, i, a,i+num, cap-i-num);
			}
		}
	}
	
	protected void createGap(int p){
		if(p+1<=size-p){
			if(p!=0)
				shiftLeft(0, p-1, 1);
			start=prevIndex(start);
		}else if(p!=size)
			shiftRight(p, size-1, 1);
	}
	
	protected void setToNull(int pos1,int pos2){
		int i=getIndex(pos1);
		int j=getIndex(pos2);
		if(i<=j)
			Arrays.fill(a,i,j+1,null);
		else{
			Arrays.fill(a,i,a.length,null);
			Arrays.fill(a, 0,j+1,null);
		}
	}
	
	protected void closeGap(int fromPos,int toPos){
		int sizeOfFrontPosition=fromPos;
		int sizeOfBackPosition=size-toPos-1;
		int numRemoving=toPos-fromPos+1;
		
		if(sizeOfBackPosition<=sizeOfFrontPosition){
			if(toPos!=size-1)
				shiftLeft(toPos+1,size-1, numRemoving);
			start=getIndex(numRemoving);
		}
	}
}

