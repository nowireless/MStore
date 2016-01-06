package org.nowireless.common.massive.predicate;

import org.nowireless.common.massive.Registerable;

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
