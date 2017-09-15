/*
 * Copyright 2017 dmerkushov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.dmerkushov.signals4j;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dmerkushov
 */
public class Signals4jTest {

	public Signals4jTest () {
	}

	static boolean[] phases = new boolean[2];

	@BeforeClass
	public static void setUpClass () {
		Arrays.fill (phases, false);
	}

	@AfterClass
	public static void tearDownClass () {
	}

	/**
	 * Check that an emitted signal starts a slot
	 */
	@Test
	public void simpleSignal () {
		Signal signal = new Signal () {
		};

		final boolean[] result = new boolean[1];
		result[0] = false;

		Slot slot0 = new Slot () {

			@Override
			public void threadsafeHandle (Signal signal) throws SlotException {
				result[0] = true;
			}
		};
		Signals4j.getInstance ().connect (signal, slot0);

		signal.emit ();

		try {
			Thread.sleep (300L);
		} catch (InterruptedException ex) {
			Logger.getLogger (Signals4jTest.class.getName ()).log (Level.SEVERE, null, ex);
		}

		boolean[] expected = {true};

		Assert.assertArrayEquals (expected, result);
	}

	/**
	 * Check that a single signal starts several connected slots
	 */
	@Test
	public void multipleSlotSignal () {
		Signal signal = new Signal () {
		};

		final boolean[] result = {false, false};

		Slot slot0 = new Slot () {
			@Override
			public void threadsafeHandle (Signal signal) throws SlotException {
				result[0] = true;
			}
		};
		Signals4j.getInstance ().connect (signal, slot0);

		Slot slot1 = new Slot () {
			@Override
			public void threadsafeHandle (Signal signal) throws SlotException {
				result[1] = true;
			}
		};
		Signals4j.getInstance ().connect (signal, slot1);

		signal.emit ();

		try {
			Thread.sleep (3000L);
		} catch (InterruptedException ex) {
			Logger.getLogger (Signals4jTest.class.getName ()).log (Level.SEVERE, null, ex);
		}

		boolean[] expected = {true, true};

		Assert.assertArrayEquals (expected, result);
	}

	/**
	 * Check that the connect(signal,slot) method works
	 */
	@Test
	public void testConnectBySignal () {
		Signal signal = new Signal () {
		};

		final boolean[] result = new boolean[1];
		result[0] = false;

		Slot slot0 = new Slot () {

			@Override
			public void threadsafeHandle (Signal signal) throws SlotException {
				result[0] = true;
			}
		};
		Signals4j.getInstance ().connect (signal, slot0);

		signal.emit ();

		try {
			Thread.sleep (3000L);
		} catch (InterruptedException ex) {
			Logger.getLogger (Signals4jTest.class.getName ()).log (Level.SEVERE, null, ex);
		}

		boolean[] expected = {true};

		Assert.assertArrayEquals (expected, result);
	}

	/**
	 * Check that the connect(signalClass,slot) method works
	 */
	@Test
	public void testConnectBySignalClass () {
		Signal signal = new Signal () {
		};

		final boolean[] result = new boolean[1];
		result[0] = false;

		Slot slot0 = new Slot () {

			@Override
			public void threadsafeHandle (Signal signal) throws SlotException {
				result[0] = true;
			}
		};
		Signals4j.getInstance ().connect (signal.getClass (), slot0);

		signal.emit ();

		try {
			Thread.sleep (300L);
		} catch (InterruptedException ex) {
			Logger.getLogger (Signals4jTest.class.getName ()).log (Level.SEVERE, null, ex);
		}

		boolean[] expected = {true};

		Assert.assertArrayEquals (expected, result);
	}

	/**
	 * Check that the disconnect(signal,slot) method works
	 */
	@Test
	public void testDisconnectBySignal () {
		Signal signal = new Signal () {
		};

		final boolean[] result = {true};

		Slot slot = new Slot () {
			@Override
			public void threadsafeHandle (Signal signal) throws SlotException {
				result[0] = false;
			}
		};

		Signals4j.getInstance ().connect (signal, slot);

		Signals4j.getInstance ().disconnect (signal, slot);

		signal.emit ();

		boolean[] expected = {true};

		Assert.assertArrayEquals (expected, result);
	}

	/**
	 * Check that the disconnect(signalClass,slot) method works
	 */
	@Test
	public void testDisconnectBySignalClass () {
		Signal signal = new Signal () {
		};

		final boolean[] result = {true};

		Slot slot = new Slot () {
			@Override
			public void threadsafeHandle (Signal signal) throws SlotException {
				result[0] = false;
			}
		};

		Signals4j.getInstance ().connect (signal, slot);

		Signals4j.getInstance ().disconnect (signal.getClass (), slot);

		signal.emit ();

		boolean[] expected = {true};

		Assert.assertArrayEquals (expected, result);
	}
}
