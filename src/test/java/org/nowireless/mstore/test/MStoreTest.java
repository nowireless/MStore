package org.nowireless.mstore.test;

import org.nowireless.mstore.MStore;

public class MStoreTest {
	
	public static void main(String[] args) {
		MStore.get().init();
		MStore.collTick();
	}
}
