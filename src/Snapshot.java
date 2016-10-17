import java.io.OutputStream;

public abstract class Snapshot {

	public abstract double getValue(double percentile);

	public abstract long[] getValues();

	public abstract int size();

	public double getMedian() {
		return getValue(0.5);
	}

	public double get75thPercentile() {
		return getValue(0.75);
	}

	public double get95thPercentile() {
		return getValue(0.95);
	}

	public double get98thPercentile() {
		return getValue(0.98);
	}

	public double get99thPercentile() {
		return getValue(0.99);
	}

	public abstract long getMax();
	
	public abstract double getMean();
	
	public abstract double getStdDev();

	public abstract void dump(OutputStream output);

	
}
