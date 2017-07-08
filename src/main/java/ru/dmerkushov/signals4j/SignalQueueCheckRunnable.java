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

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dmerkushov
 */
class SignalQueueCheckRunnable implements Runnable {

	@Override
	public void run () {

		SignalQueue q = SignalQueue.getInstance ();
		SignalSlotMap m = SignalSlotMap.getInstance ();

		while (true) {
			while (q.isEmpty ()) {

				try {
					Thread.sleep (10L);
				} catch (InterruptedException ex) {
					Logger.getLogger (SignalQueueCheckRunnable.class.getName ()).log (Level.SEVERE, null, ex);
					return;
				}
			}

			Signal signal = q.poll ();
			if (signal != null) {

				Set<Class<? extends Signal>> signalClasses = m.getRegisteredSignalClasses ();

				Class signalClass = signal.getClass ();
				while (!signalClasses.contains (signalClass) && !signalClass.equals (Signal.class)) {
					signalClass = signalClass.getSuperclass ();
				}

				Set<Slot> slots = m.getSlots (signalClass);
				if (slots != null) {

					Iterator<Slot> slotIter = slots.iterator ();
					while (slotIter.hasNext ()) {
						Slot slot = slotIter.next ();
						if (slot != null) {
							SlotExecutor.getInstance ().submit (slot, signal);
						}
					}
				}
			}

			try {
				Thread.sleep (1L);
			} catch (InterruptedException ex) {
				Logger.getLogger (SignalQueueCheckRunnable.class.getName ()).log (Level.SEVERE, null, ex);
				return;
			}
		}
	}

}
