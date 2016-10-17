import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;

public class SAutoCorrelation {

	private double[] xData;

	SAutoCorrelation(double[] aDataX) {
		this.xData = aDataX;
	}

	public void fftAutoCorrelation() {
		FastFourierTransformer fft = new FastFourierTransformer(
				DftNormalization.STANDARD);

	}

	public double[] statsAutoCorrelation() {
		double mean = StatUtils.mean(xData);
		double var = StatUtils.variance(xData);
		double[] temp = new double[xData.length];
		double[] sum = new double[xData.length];
		double[] result = new double[xData.length];

		for (int i = 0; i < xData.length; i++) {
			for (int j = 1; j < xData.length - i; j++) {
				temp[i] = temp[i] + (xData[i + j] - mean)
						* (xData[j - 1] - mean);
				sum[i] = (1.0 / xData.length) * (temp[i]);
			}
			result[i] = sum[i] / var;

		}
		return result;
	}
}
