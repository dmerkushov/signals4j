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
import ru.dmerkushov.signals4j.util.CreationTraceable;

/**
 *
 * @author dmerkushov
 * @param <S>
 */
public abstract class Slot<S extends Signal> extends CreationTraceable {

	/**
	 * Connect this slot to a signal class
	 *
	 * @param signalClass
	 */
	public final void connect (Class<S> signalClass) {
		Objects.requireNonNull (signalClass, "signalClass");

		Signals4j.getInstance ().connect (signalClass, this);
	}

	/**
	 * Connect this slot to the given signal's class
	 *
	 * @param signal
	 */
	public final void connect (S signal) {
		Objects.requireNonNull (signal, "signal");

		Signals4j.getInstance ().connect (signal, this);
	}

	/**
	 * Disconnect this slot from a signal class
	 *
	 * @param signalClass
	 */
	public final void disconnect (Class<S> signalClass) {
		Objects.requireNonNull (signalClass, "signalClass");

		Signals4j.getInstance ().disconnect (signalClass, this);
	}

	/**
	 * Disconnect this slot from the given signal's class
	 *
	 * @param signal
	 */
	public final void disconnect (S signal) {
		Objects.requireNonNull (signal, "signal");

		Signals4j.getInstance ().disconnect (signal, this);
	}

	/**
	 * What must be executed in response to a signal. The code must be
	 * thread-safe, as it is run in a separate thread, probably at the same time
	 * with other slots executing
	 *
	 * @param signal
	 * @throws SlotException
	 */
	public abstract void threadsafeHandle (S signal) throws SlotException;
}
