import java.util.ArrayList;


public class Aggregator implements Notifier{
	
	private ArrayList<Aggregate> aggregates=new ArrayList<Aggregate>();
	Expirer expirer=null;
	Notifier notifier;
	public Aggregator aggregate(Aggregate aggrt){
		aggregates.add(aggrt);
		return this;
	}
	
	public <E> void each(long aTimestamp,String aKey,E aValue,Command<E> aCmd){
		for(Aggregate a:aggregates){
			long quanta=a.expire(aTimestamp);
			String quantaString=a.quantizeToString(aTimestamp);
			String expKey=aCmd.execute(aTimestamp, quantaString, aKey, aValue);
			if(expKey!=null && expirer!=null){
				expirer.expire(expKey, a.expire(aTimestamp));
			}
			if(notifier!=null){
				notifier.notify(aKey,"Test", aTimestamp, quanta, quantaString);
			}
		}
		
	}
	
	public Expirer expirer(Expirer aExpirer){
		this.expirer=aExpirer;
		return expirer;
	}
	
	public <E> void each(String aKey,E value,Command<E> aCmd){
		each(System.currentTimeMillis(),aKey,value,aCmd);
	}

	@Override
	public void notify(String key, String resolution, long timestamp,
			long quant, String quantString) {
		System.out.println("Publish");
		System.out.println(key+":"+resolution+":"+quantString);
		
	}

}
