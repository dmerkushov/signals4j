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

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author dmerkushov
 */
public class SlotExceptionTest {

	public SlotExceptionTest () {
	}

	@Test
	public void testConstructorDefault () {
		SlotException ex = new SlotException ();
		Assert.assertNotNull (ex);
		Assert.assertNull (ex.getSignal ());
		Assert.assertNull (ex.getSlot ());
		Assert.assertTrue (ex.getMessage ().contains ("Signal") && ex.getMessage ().contains ("Slot"));
		Assert.assertNull (ex.getCause ());

	}

	@Test
	public void testConstructorMessage () {
		String message = "message";
		SlotException ex = new SlotException (message);
		Assert.assertNotNull (ex);
		Assert.assertNull (ex.getSignal ());
		Assert.assertNull (ex.getSlot ());
		Assert.assertTrue (ex.getMessage ().contains (message) && ex.getMessage ().contains ("Signal") && ex.getMessage ().contains ("Slot"));
		Assert.assertNull (ex.getCause ());
	}

//	public void smth () {
//		System.out.println ("SlotException by new SlotException (\"message\"):");
//		ex.printStackTrace (System.out);
//
//		ex = new SlotException ("message", new Exception ());
//		System.out.println ("SlotException by new SlotException (\"message\", new Exception ()):");
//		ex.printStackTrace (System.out);
//
//		ex = new SlotException (new Exception ());
//		System.out.println ("SlotException by new SlotException (new Exception ()):");
//		ex.printStackTrace (System.out);
//
//		Signal signal = new Signal () {
//		};
//
//		Slot<Signal> slot = new Slot () {
//
//			@Override
//			public void threadsafeHandle (Signal signal) throws SlotException {
//			}
//
//		};
//
//		ex = new SlotException (signal, slot);
//		System.out.println ("SlotException by new SlotException (signal, slot):");
//		ex.printStackTrace (System.out);
//
//		ex = new SlotException (signal, slot, "message");
//		System.out.println ("SlotException by new SlotException (signal, slot, \"message\"):");
//		ex.printStackTrace (System.out);
//
//		ex = new SlotException (signal, slot, new Exception ());
//		System.out.println ("SlotException by new SlotException (signal, slot, new Exception ()):");
//		ex.printStackTrace (System.out);
//
//		ex = new SlotException (signal, slot, "message", new Exception ());
//		System.out.println ("SlotException by new SlotException (signal, slot, \"message\", new Exception ()):");
//		ex.printStackTrace (System.out);
//	}
}
