
public interface Command<E> {

	public String execute(long aTimeStamp,String quantStrng,String aKey,E aVal);
}
