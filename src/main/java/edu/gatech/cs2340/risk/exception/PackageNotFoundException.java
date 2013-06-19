package main.java.edu.gatech.cs2340.risk.exception;

public class PackageNotFoundException extends Exception {
	
	public static final String DEFAULT_MSG = "PackageNotFoundException detected. ";
	
	public PackageNotFoundException(String message) {
		super(message);
	}
	
	public PackageNotFoundException() {
		this(DEFAULT_MSG);
	}
	
	
}
