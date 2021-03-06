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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import ru.dmerkushov.signals4j.util.Signals4jExecutors;

/**
 *
 * @author dmerkushov
 */
class SlotExecutor {

	Set<Thread> slotExecutorThreads = Collections.synchronizedSet (new HashSet<Thread> ());
	private ExecutorService service;
	public static final int THREAD_COUNT_DEFAULT = 1;
	public static final long THREAD_SHUTDOWN_TIMEOUT_SECONDS = 10L;

	private static SlotExecutor instance;

	synchronized static SlotExecutor getInstance () {
		if (instance == null) {
			instance = new SlotExecutor ();
		}

		return instance;
	}

	private SlotExecutor () {
		int threadCount = Preferences.userNodeForPackage (SlotExecutor.class).getInt ("THREAD_COUNT", THREAD_COUNT_DEFAULT);

		long shutdownTimeout = Preferences.userNodeForPackage (SlotExecutor.class).getLong ("THREAD_SHUTDOWN_TIMEOUT_SECONDS", THREAD_SHUTDOWN_TIMEOUT_SECONDS);

		service = Signals4jExecutors.createFixedTimeoutExecutorService ("SlotExecutorService-", threadCount, shutdownTimeout, TimeUnit.SECONDS);
	}

	Future submit (final Slot slot, final Signal signal) {
		Future f = service.submit (() -> {
			slotExecutorThreads.add (Thread.currentThread ());
			try {
				slot.threadsafeHandle (signal);
			} catch (SlotException ex) {
				Logger.getLogger (SignalQueueCheckRunnable.class.getName ()).log (Level.SEVERE, "Error while handling signal with slot.\nSignal: " + signal + "\nSlot: " + slot, ex);
			}
			slotExecutorThreads.remove (Thread.currentThread ());
		});
		Logger.getLogger (SignalQueueCheckRunnable.class.getName ()).log (Level.FINEST, "Future object for the submitted slot execution: {0}", f);

		return f;
	}
}
