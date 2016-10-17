import org.apache.commons.math3.stat.StatUtils;


public class AutoCorrelation {

	private double[] xData,outData;
	private int noOfLags;
	private int noOfOutPut;
	private int N;
	private double mx,varX;
	
	public AutoCorrelation(double[] aDataX,int aLags){
		this.xData=aDataX;
		this.noOfLags=aLags;
		this.noOfOutPut=aLags;
		this.N=xData.length;
		this.outData=new double[noOfOutPut];
	}
	
	public double mean(){
		mx = StatUtils.mean(xData);
		return mx;
	}
	
	public double variance(){
		varX = StatUtils.variance(xData);
		return varX;
	}
	
	public double[] autocorrelation(){
		double[] temp = new double[xData.length];
		double[] sum = new double[xData.length];
		mean();
		variance();
		for (int i = 0; i < N-noOfLags; i++) {
			for (int j = N-noOfLags; j < N-i; j++) {
			
				temp[i] = temp[i] + (xData[i + j] - mx)
						* (xData[j - 1] - mx);
				sum[i] = (1.0 / xData.length) * (temp[i]);
			}
			outData[i] = sum[i] / varX;

		}
		return outData;
	}
	
	
	public static void main(String args[]){
		double[] inputX =new double[]{12,23,1,25,1,2,0,13,12,45,34,56,1,0,9,10,16,16,35,19,11,3,4,7,1,3,11,20,21,2,20,31,2,5,3,6,14,40,9,19,11,10,30,1,1,3,2,7};
		AutoCorrelation ac=new AutoCorrelation(inputX, 24);
		double[] out=ac.autocorrelation();
		for(int i=0;i<out.length;i++)
			System.out.println("Autocorr "+i+"::"+out[i]);
	}
	
	
	
}
