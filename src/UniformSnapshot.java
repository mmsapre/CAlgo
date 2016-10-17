import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.math3.stat.StatUtils;

public class UniformSnapshot extends Snapshot {

	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private final long[] values;

	public UniformSnapshot(Collection<Long> values) {
		final Object[] copy = values.toArray();
		this.values = new long[copy.length];
		for (int i = 0; i < copy.length; i++) {
			this.values[i] = (Long) copy[i];
		}
		Arrays.sort(this.values);
	}

	public UniformSnapshot(long[] values) {
		this.values = Arrays.copyOf(values, values.length);
		Arrays.sort(this.values);
	}

	@Override
	public double getValue(double percentile) {
		if (percentile < 0.0 || percentile > 1.0 || Double.isNaN(percentile)) {
			throw new IllegalArgumentException(percentile + "value is not valid...]");
		}
		if (values.length == 0) {
			return 0.0;
		}
		double pos = percentile * (values.length + 1);
		int index = (int) pos;
		if (index < 1) {
			return values[0];
		}
		if (index >= values.length) {
			return values[values.length - 1];
		}
		double lower = values[index - 1];
		double upper = values[index];
		return lower + (pos - Math.floor(pos)) * (upper - lower);
	}

	@Override
	public long[] getValues() {
		return Arrays.copyOf(values, values.length);
	}

	@Override
	public int size() {
		return values.length;
	}

	@Override
	public long getMax() {
		if (values.length == 0) {
			return 0;
		}
		return values[values.length - 1];
	}

	@Override
	public double getMean() {
		double sum = 0;
		if (values.length == 0) {
			return 0;
		}
		for (int i = 0; i < values.length; i++) {
			sum += values[i];
		}
		return sum / values.length;
	}

	@Override
	public double getStdDev() {
		if (values.length <= 1) {
			return 0;
		}
		final double mean = getMean();
		double sum = 0;
		for (int i = 0; i < values.length; i++) {
			final double diff = values[i] - mean;
			sum += diff * diff;
		}
		final double variance = sum / (values.length - 1);
		return Math.sqrt(variance);
	}

	@Override
	public void dump(OutputStream output) {
		final PrintWriter out = new PrintWriter(new OutputStreamWriter(output,
				UTF_8));
		try {
			for (int i = 0; i < values.length; i++) {
				out.printf("Received values", values[i]);
			}
		} finally {
			out.close();
		}

	}

}
