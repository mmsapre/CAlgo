
public interface Reservoir {

	int size();
	
	void update(long value);

	Snapshot getSnapshot();
}
