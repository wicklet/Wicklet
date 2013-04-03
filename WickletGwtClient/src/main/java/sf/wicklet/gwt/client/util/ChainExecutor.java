/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.util;

import java.util.LinkedList;

/**
 * A list of tasks that are executed when ChainExecutor.start() is called.
 * Each task must explicitly start the next task when it is done.
 */
public class ChainExecutor {
	public interface IStartable {
		void start();
	}
	public interface IChainable {
		void run(IStartable next);
	}
	private final LinkedList<Command> tasks = new LinkedList<Command>();
	public static class Command implements IStartable {
		final IChainable task;
		Command next;
		public Command(final IChainable task) {
			this.task = task;
		}
		@Override
		public void start() {
			task.run(next);
		}
	}
	public void queue(final IChainable task) {
		tasks.add(new Command(task));
	}
	public void start() {
		final Command head = tasks.peek();
		if (head != null) {
			for (Command task; (task = tasks.poll()) != null;) {
				task.next = tasks.peek();
		}}
		head.start();
	}
}
