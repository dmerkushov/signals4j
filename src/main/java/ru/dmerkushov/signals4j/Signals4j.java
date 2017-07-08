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

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author dmerkushov
 */
public final class Signals4j {

	private SignalQueueCheckRunnable slotR;
	private Thread slotT;
	private static Signals4j instance = null;

	/**
	 * Get the singleton instance of the framework general class
	 *
	 * @return
	 */
	public synchronized static Signals4j getInstance () {
		if (instance == null) {
			instance = new Signals4j ();
			instance.slotT.start ();
		}

		return instance;
	}

	private Signals4j () {
		slotR = new SignalQueueCheckRunnable ();
		slotT = new Thread (slotR);
		slotT.setName ("SlotThread");
		slotT.setDaemon (true);
	}

	/**
	 * Connect a signal class to a slot. When a signal of this class is emitted,
	 * the slot will be executed
	 *
	 * @param <S>
	 * @param signalClass
	 * @param slot
	 */
	public <S extends Signal> void connect (Class<S> signalClass, Slot<S> slot) {
		Objects.requireNonNull (signalClass, "signalClass");
		Objects.requireNonNull (slot, "slot");

		SignalSlotMap.getInstance ().connect (signalClass, slot);
	}

	/**
	 * Connect a signal to a slot based on the signal's class. Thus, all signals
	 * belonging to the same class as this one, when emitted, will make this
	 * slot execute
	 *
	 * @param <S>
	 * @param signal
	 * @param slot
	 */
	public <S extends Signal> void connect (S signal, Slot<S> slot) {
		Objects.requireNonNull (signal, "signal");
		Objects.requireNonNull (slot, "slot");

		SignalSlotMap.getInstance ().connect (signal, slot);
	}

	/**
	 * Disconnect a signal class from a slot
	 *
	 * @param <S>
	 * @param signalClass
	 * @param slot
	 */
	public <S extends Signal> void disconnect (Class<S> signalClass, Slot<S> slot) {
		Objects.requireNonNull (signalClass, "signalClass");
		Objects.requireNonNull (slot, "slot");

		SignalSlotMap.getInstance ().disconnect (signalClass, slot);
	}

	/**
	 * Disconnect the signal's class from a slot
	 *
	 * @param <S>
	 * @param signal
	 * @param slot
	 */
	public <S extends Signal> void disconnect (S signal, Slot<S> slot) {
		Objects.requireNonNull (signal, "signal");
		Objects.requireNonNull (slot, "slot");

		SignalSlotMap.getInstance ().disconnect (signal, slot);
	}

	/**
	 * Disconnect all slots from the signal's class
	 *
	 * @param <S>
	 * @param signal
	 */
	public <S extends Signal> void disconnectAll (S signal) {
		Objects.requireNonNull (signal, "signal");

		SignalSlotMap.getInstance ().disconnectAll (signal);
	}

	/**
	 * Disconnect all slots from the signal class
	 *
	 * @param <S>
	 * @param signalClass
	 */
	public <S extends Signal> void disconnectAll (Class<S> signalClass) {
		Objects.requireNonNull (signalClass, "signalClass");

		SignalSlotMap.getInstance ().disconnectAll (signalClass);
	}

	/**
	 * Get slots connected to a specified signal class
	 *
	 * @param signalClass
	 * @return
	 */
	public Set<Slot> getSlots (Class<? extends Signal> signalClass) {
		Objects.requireNonNull (signalClass, "signalClass");

		return SignalSlotMap.getInstance ().getSlots (signalClass);
	}

	/**
	 * Get slots connected to the signal's class
	 *
	 * @param signal
	 * @return
	 */
	public Set<Slot> getSlots (Signal signal) {
		Objects.requireNonNull (signal, "signal");

		return SignalSlotMap.getInstance ().getSlots (signal);
	}

	/**
	 * Get the number of slots currently connected to the given signal class
	 *
	 * @param signalClass
	 * @return
	 */
	public int getSlotCount (Class<? extends Signal> signalClass) {
		Objects.requireNonNull (signalClass, "signalClass");

		return SignalSlotMap.getInstance ().getSlotCount (signalClass);
	}

	/**
	 * Get the number of slots currently connected to the given signal's class
	 *
	 * @param signal
	 * @return
	 */
	public int getSlotCount (Signal signal) {
		Objects.requireNonNull (signal, "signal");

		return SignalSlotMap.getInstance ().getSlotCount (signal);
	}

}
