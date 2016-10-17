
public class TestAutoCorrelation {
	private double[] xData,yData;
	int i,j,n;
	double mx,my,sx,sy,sxy,denom,r;

	TestAutoCorrelation(double[] aDataX,double[] aDataY) {
		this.xData = aDataX;
		this.yData=aDataY;
		this.n=xData.length;
				
	}
	
	private void mean(){
		mx=0;
		my=0;
		for(i=0;i<n;i++){
			mx+=xData[i];
			my+=yData[i];
		}
		mx/=n;
		my/=n;
	}

	public double crossCorrelation(){
		int delay,maxDelay=23;
		mean();
		sx = 0;
		   sy = 0;
		   for (i=0;i<n;i++) {
		      sx += (xData[i] - mx) * (xData[i] - mx);
		      sy += (yData[i] - my) * (yData[i] - my);
		   }
		   denom=Math.sqrt(sx*sy);
		   for(delay=-maxDelay;delay<maxDelay;delay++){
			   sxy=0;
			   for(i=0;i<n;i++){
				   j=i+delay;
				   if(j<0 || j>n)
					   continue;
				   else{
					   System.out.println("i ::"+i+": j ::"+j);
					   sxy+=(xData[i]-mx) *(yData[j]-my);
				   }
			   }
			   
		   }
		   r=sxy/denom;
		return r;
	}
	
	public static void main(String args[]){
		 double[] inputX =new double[]{12,23,1,25,1,2,0,13,12,45,34,56,1,0,9,10,16,16,35,19,11,3,4,7};
		 double[] inputY =new double[]{1,3,11,20,21,2,20,31,2,5,3,6,14,40,9,19,11,10,30,1,1,3,2,7};
		 TestAutoCorrelation tr=new TestAutoCorrelation(inputX,inputY);
		 double outR=tr.crossCorrelation();
		 System.out.println("Correlation ::"+outR);
	}

}
