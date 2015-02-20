package mw.client.controller;

public abstract class Controller {
	
	public abstract static Controller singleton();
	public abstract static void initialize();
	public abstract static void clear();

}
