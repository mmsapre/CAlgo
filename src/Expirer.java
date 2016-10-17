
public interface Expirer {

	public void expire(String key,long retainMills);
}
