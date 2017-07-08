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
package ru.dmerkushov.signals4j.util;

import java.util.Arrays;

/**
 *
 * @author dmerkushov
 */
public abstract class CreationTraceable {

	private StackTraceElement[] stackTraceCreated;

	public CreationTraceable () {
		InnerException ie = new InnerException ();
		StackTraceElement[] st = ie.getStackTrace ();
		stackTraceCreated = Arrays.copyOfRange (st, 1, st.length);
	}

	public final StackTraceElement[] getStackTraceCreated () {
		return Arrays.copyOf (stackTraceCreated, stackTraceCreated.length);
	}

	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder ();

		sb.append (this.getClass ().getCanonicalName ()).append (" created:");
		for (StackTraceElement ste : stackTraceCreated) {
			sb.append ("\n\tin ").append (ste.getClassName ())
					.append (":").append (ste.getMethodName ()).append ("()")
					.append (", line ").append (ste.getLineNumber ());
		}

		return sb.toString ();
	}

	private static class InnerException extends Exception {
	}

}
