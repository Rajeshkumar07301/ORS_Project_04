package in.co.pro4.exception;
/**
 * DatabaseException is propogated by DAO classes when an unhandled Database
 * exception occurred.
 * 
 * @author Rajesh Kumar
 *
 */
public class DataBaseException extends Exception{
	public DataBaseException(String msg) {
		super(msg);
	}

}
