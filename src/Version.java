import java.util.ConcurrentModificationException;


public class Version {

	private int modificationCount=0;
	
	public int increment(){
		return ++modificationCount;
	}
	
	public int getCount(){
		return modificationCount;
	}
	
	public void restoreCount(int count){
		modificationCount=count;
	}
	
	public void check(int v){
		if(v<modificationCount)
			throw new ConcurrentModificationException("Exception occur..");
	}
}
