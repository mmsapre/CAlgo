
public class AggregatorClient implements Expirer{

	Aggregator aggr;
	
	public AggregatorClient(Aggregator aAggr){
		this.aggr=aAggr;
		aggr.expirer=this;
	}
	
	public void doSomething(long timestamp,String key,String value){
	
		aggr.each(timestamp, key, value, new Command<String>(){
			public String execute(long timestamp,String quantString,String key,String value){
				String k=key+":"+quantString;
				System.out.println(k+"="+value);
				System.out.println("Print...");
				return k;
			}
		});
		
	}
	
	public void doSomethingMore(long timestamp,String key,String value,final String another){
		aggr.each(timestamp, key, value, new Command<String>(){
			public String execute(long timestamp,String quantString,String key,String value){
				String k=key+":"+quantString;
				System.out.println(k+"="+value);
				return k;
			}
		});
	}
	
	
	public static void main(String args[]){
		Aggregator agrt=new Aggregator();
		Aggregate agregat=new Aggregate();
		agregat.expire(100);
		agregat.quantize(1000);
		
		agrt.aggregate(agregat);
		
		AggregatorClient agrClnt=new AggregatorClient(agrt);
		agrClnt.doSomething(10, "msg1", "Msg 1...");
		
		
	}

	@Override
	public void expire(String key, long retainMills) {
		// TODO Auto-generated method stub
		
	}
	
}
