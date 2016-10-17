
public class FAEIllegalArgumentException extends Exception{
	
	FAEIllegalArgumentException(String message){
		super(message);
	}
	
	FAEIllegalArgumentException(String message,Throwable tr){
		super(message,tr);
	}
	
	
	
	public String getMessage(){
		return super.getMessage();		
	}
	
	

}
