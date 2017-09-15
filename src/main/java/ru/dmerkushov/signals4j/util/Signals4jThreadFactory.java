/*
 * Copyright 2017 Dmitriy Merkushov <d.merkushov@gmail.com>.
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
package ru.dmerkushov.signals4j.util;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * After Pshemo in
 * https://stackoverflow.com/questions/13883293/turning-an-executorservice-to-daemon-in-java
 *
 * @author Dmitriy Merkushov <d.merkushov@gmail.com>
 */
public class Signals4jThreadFactory implements ThreadFactory {

	public final String prefix;
	public final boolean daemon;
	ThreadFactory inner = Executors.defaultThreadFactory ();

	public Signals4jThreadFactory (String prefix, boolean daemon) {
		Objects.requireNonNull (prefix, "prefix");

		this.prefix = prefix;
		this.daemon = daemon;
	}

	public Signals4jThreadFactory (String prefix) {
		this (prefix, true);
	}

	@Override
	public Thread newThread (Runnable r) {
		Thread t = inner.newThread (r);

		t.setName (prefix + t.getName ());
		t.setDaemon (daemon);

		return t;
	}

}
