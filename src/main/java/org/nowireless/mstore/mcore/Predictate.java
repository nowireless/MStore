package org.nowireless.mstore.mcore;

public interface Predictate<T>
{
	public boolean apply(T type);
}
