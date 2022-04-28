package io.github.nabil.ptolemy.rcp.application.startup;

import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;

public abstract class SequenceStep {

	private SequenceStep next;

	public SequenceStep chainWith(SequenceStep step) {
		next = step;
		return next;
	}

	public abstract boolean execute(PtolemyApplicationContext context);

	protected boolean nextStep(PtolemyApplicationContext context) {
		if (next == null) {
			return true;
		}

		return next.execute(context);
	}

}
