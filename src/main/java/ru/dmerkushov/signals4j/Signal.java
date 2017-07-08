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

import java.util.Set;
import ru.dmerkushov.signals4j.util.CreationTraceable;

/**
 *
 * @author dmerkushov
 */
public abstract class Signal extends CreationTraceable {

	/**
	 * Emit this signal, so the slots connected to this signal's class will
	 * execute
	 */
	public final void emit () {
		SignalQueue.getInstance ().add (this);
	}

	/**
	 * Disconnect all the slots connected to this signal's class
	 */
	public final void disconnectAll () {
		Signals4j.getInstance ().disconnectAll (this);
	}

	/**
	 * Get the slots connected to this signal's class
	 *
	 * @return
	 */
	public final Set<Slot> getSlots () {
		return Signals4j.getInstance ().getSlots (this);
	}

	/**
	 * Get a count of the slots connected to this signal's class
	 *
	 * @return
	 */
	public final int getSlotCount () {
		return Signals4j.getInstance ().getSlotCount (this);
	}

}
