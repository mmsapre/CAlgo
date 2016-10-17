import redis.clients.jedis.Jedis;


public class RedisJedisClient {

	public static void main(String[] args){
		 Jedis j = new Jedis("si-vm-095", 6379);
		 j.connect(); 
		 RedisClient rd=new RedisClient();
		 rd.jedis(j);
		 Aggregator aggr=new Aggregator();
		 Aggregate agg=new Aggregate();
		 rd.aggregator(aggr);
	}
	
	 public static void behaveAsMap(Jedis j) {
		 System.out.println("\n[Map<String, String>]");
		  
		 //Store integer as string.
		 j.set("burgers_sold", "0");
		  
		 for (int i = 0; i < 224; i++) {
		 j.incrBy("burgers_sold", 1);
		 }
		  
		 
		 } 
}
