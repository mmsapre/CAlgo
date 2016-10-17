import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class SPearsonsCorrelation {

	private double[] xData;
	private double[] yData;
	private PearsonsCorrelation psCorrelate;
	
	
	SPearsonsCorrelation(double[] aDataX, double[] aDataY) {

		this.xData=aDataX;
		this.yData=aDataY;
		psCorrelate=new PearsonsCorrelation();
	}
	
	
	public double computeCorrelationXY() throws Exception{
		double corr;
		try{
			corr=psCorrelate.correlation(xData, yData);
		}catch(IllegalArgumentException illEx){
			throw new FAEIllegalArgumentException(illEx.getMessage());
		}
		return corr;
	}
	
	
}
