import redis.clients.jedis.Jedis;


public class RedisClient implements Expirer{

	Jedis jedis;
	Aggregator aggregator;
	
	public RedisClient jedis(Jedis aJedis){
		this.jedis=aJedis;
		return this;
	}
	
	public RedisClient aggregator(Aggregator aAggregator){
		this.aggregator=aAggregator;
		aggregator.expirer(this);
		return this;
	}
	
	@Override
	public void expire(String key, long retainMills) {
		jedis.expire(key, (int)(retainMills/1000));
		
	}
	
	
	public void incrBy(long timestamp,String key,long value){
		aggregator.each(timestamp, key, value, new Command<Long>(){

			@Override
			public String execute(long aTimeStamp, String quantStrng,
					String aKey, Long aVal) {
				String k=aKey+":"+quantStrng;
				jedis.incrBy(aKey, aVal);
				return k;			
			}
			
		});
	}
	
	public void hincrby(long timestamp,String key,String field,final long by){
		aggregator.each(timestamp, key, field, new Command<String>(){

			@Override
			public String execute(long aTimeStamp, String quantStrng,
					String aKey, String aVal) {
				String k=aKey+":"+quantStrng;
				jedis.hincrBy(aKey, aVal, by);
				return k;
			}
			
		});
	}

}
