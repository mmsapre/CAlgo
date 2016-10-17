
public class SCrossCorrelation {

	private double[] xData,yData,Sxy;
	private double sumX,sumY;
	int i,j,n;
	double mx,my,sx,sy,sxy,denom,r;

	SCrossCorrelation(double[] aDataX,double[] aDataY) {
		this.xData = aDataX;
		this.yData=aDataY;
		this.n=xData.length;
		Sxy=new double[n];
	}
	
	public void mean(){
		mx=0;
		my=0;
		for(i=0;i<n;i++){
			mx+=xData[i];
			my+=yData[i];
		}
		mx/=n;
		my/=n;
	}
	
	public double[] crossCorrelation(){
		int r=0;
		mean();
		sx = 0;
		   sy = 0;
		   for (i=0;i<n;i++) {
		      sx += (xData[i] - mx) * (xData[i] - mx);
		      sy += (yData[i] - my) * (yData[i] - my);
		   }
		return r;
	}

}
