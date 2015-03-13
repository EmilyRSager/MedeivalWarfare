package mw.util;

public interface CacheValueComputer<InputType, OutputType> {

	public OutputType computeValue(InputType arg);

}