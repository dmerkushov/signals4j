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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * After Marco13 in
 * https://stackoverflow.com/questions/13883293/turning-an-executorservice-to-daemon-in-java
 *
 * @author Dmitriy Merkushov <d.merkushov@gmail.com>
 */
public class Signals4jExecutors {

	public static ExecutorService createFixedTimeoutExecutorService (String threadNamePrefix, int threadPoolCount, long keepAlive, TimeUnit timeUnit) {
		ThreadPoolExecutor e = new ThreadPoolExecutor (threadPoolCount, threadPoolCount, keepAlive, timeUnit, new LinkedBlockingQueue<> (), new Signals4jThreadFactory (threadNamePrefix, false));
		e.allowCoreThreadTimeOut (true);
		return e;
	}

}
