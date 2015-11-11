package org.nowireless.mstore.mcore;

public class PredictateIsRegistered implements Predictate<Registerable>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static PredictateIsRegistered i = new PredictateIsRegistered();
	public static PredictateIsRegistered get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public boolean apply(Registerable registerable)
	{
		return registerable.isRegistered();
	}
	
}
