package cn.fund123.shumi.exceptions;

/**
 * 
 * @author iron.yin@gmail.com
 */
public class VerifySignException extends RuntimeException {
	private static final long serialVersionUID = 8565418541013678078L;

	public VerifySignException(String resource) {
		this("an error occurred while trying to verify sign "+resource, null);
	}

	public VerifySignException(String resource, Throwable cause) {
		super(resource, cause);
	}
}
