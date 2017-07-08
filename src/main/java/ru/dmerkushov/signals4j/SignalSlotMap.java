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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author dmerkushov
 */
class SignalSlotMap {

	private static SignalSlotMap instance = null;

	synchronized static SignalSlotMap getInstance () {
		if (instance == null) {
			instance = new SignalSlotMap ();
		}
		return instance;
	}

	private final Object lock;
	private Map<Class<? extends Signal>, Set<Slot>> map;

	private SignalSlotMap () {
		this.lock = new Object ();
		this.map = new HashMap<> ();
	}

	void connect (Class<? extends Signal> signalClass, Slot slot) {
		Objects.requireNonNull (signalClass, "signalClass");
		Objects.requireNonNull (slot, "slot");

		synchronized (lock) {
			if (!map.containsKey (signalClass)) {
				map.put (signalClass, new LinkedHashSet<> ());
			}

			// To realize adding of this slot to the end of the LinkedHashSet
			if (map.get (signalClass).contains (slot)) {
				map.get (signalClass).remove (slot);
			}

			map.get (signalClass).add (slot);
		}
	}

	void connect (Signal signal, Slot slot) {
		Objects.requireNonNull (signal, "signal");
		Objects.requireNonNull (slot, "slot");

		connect (signal.getClass (), slot);
	}

	void disconnect (Class<? extends Signal> signalClass, Slot slot) {
		Objects.requireNonNull (signalClass, "signalClass");
		Objects.requireNonNull (slot, "slot");

		synchronized (lock) {
			if (map.containsKey (signalClass)) {
				map.get (signalClass).remove (slot);
			}
		}
	}

	void disconnect (Signal signal, Slot slot) {
		Objects.requireNonNull (signal, "signal");
		Objects.requireNonNull (slot, "slot");

		disconnect (signal.getClass (), slot);
	}

	void disconnectAll (Class<? extends Signal> signalClass) {
		Objects.requireNonNull (signalClass, "signalClass");

		synchronized (lock) {
			if (map.containsKey (signalClass)) {
				map.put (signalClass, new LinkedHashSet<> ());
			}
		}
	}

	void disconnectAll (Signal signal) {
		Objects.requireNonNull (signal, "signal");

		disconnectAll (signal.getClass ());
	}

	Set<Class<? extends Signal>> getRegisteredSignalClasses () {
		synchronized (lock) {
			return new LinkedHashSet<> (map.keySet ());
		}
	}

	Set<Slot> getSlots (Class<? extends Signal> signalClass) {
		Objects.requireNonNull (signalClass, "signalClass");

		synchronized (lock) {
			return map.get (signalClass);
		}
	}

	Set<Slot> getSlots (Signal signal) {
		Objects.requireNonNull (signal, "signal");

		return getSlots (signal.getClass ());
	}

	int getSlotCount (Class<? extends Signal> signalClass) {
		Objects.requireNonNull (signalClass, "signalClass");

		synchronized (lock) {
			if (!map.containsKey (signalClass)) {
				return 0;
			}
			return map.get (signalClass).size ();
		}
	}

	int getSlotCount (Signal signal) {
		Objects.requireNonNull (signal, "signal");

		return getSlotCount (signal.getClass ());
	}

}
