import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Aggregate {

	private static final SimpleDateFormat milliSecFormatter=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final SimpleDateFormat secFormatter=new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat minFormatter=new SimpleDateFormat("yyyyMMddHHmm");
	private static final SimpleDateFormat hourFormatter=new SimpleDateFormat("yyyyMMddHH");
	private static final SimpleDateFormat dayFormatter=new SimpleDateFormat("yyyyMMdd");
	private long expireMills=-1;
	private long resolutionMills=1000;
	private TimeUnit timeUnit=TimeUnit.SECONDS;
	
	private static SimpleDateFormat formatMillis(long mills){
		if(mills<1000)
			return milliSecFormatter;
		if(mills<60*1000)
			return secFormatter;
		if(mills<3600*1000)
			return minFormatter;
		if(mills<86400*1000)
			return hourFormatter;
	
		return dayFormatter;		
	}
	
	public Aggregate expire(long aTime,TimeUnit aTimeUnit){	
		expireMills=TimeUnit.MILLISECONDS.convert(aTime, aTimeUnit);
		return this;
	}
	
	public long expire(long aTime){	
		return expireMills==-1?-1:expireMills*(aTime/expireMills);
		
	}
	
	public Aggregate resolution(long aTime,TimeUnit aTimeUnit){	
		resolutionMills=TimeUnit.MILLISECONDS.convert(aTime, aTimeUnit);
		this.timeUnit=aTimeUnit;
		return this;
	}
	
	public TimeUnit quantizeUnit(){	
		return this.timeUnit;
	}
	
	public long quantize(long time){	
		return this.resolutionMills*(resolutionMills/time);
	}
	
	public long quantize(long aTime,TimeUnit aTimeUnit){	
		return quantize(TimeUnit.MILLISECONDS.convert(aTime, aTimeUnit));
	}
	
	public String quantizeToString(long aTime){
		SimpleDateFormat sdf=formatMillis(resolutionMills);
		return sdf.format(new Date(aTime));
	}
	
}
