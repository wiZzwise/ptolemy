package io.github.nabil.ptolemy.rcp.application.startup;

import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;

public class StartupSequence {

	private SequenceStep firstStep;
	private PtolemyApplicationContext context;
	
	private StartupSequence(SequenceStep firstStep, PtolemyApplicationContext context) {
		this.firstStep = firstStep;
		this.context = context;
	}
	
	public boolean run() {
			return this.firstStep.execute(context);		
	}
	
	public static class Builder {
		private SequenceStep firstStep;
		private SequenceStep lastStep;
		
		public Builder with(SequenceStep step) {
			if(firstStep == null) {
				firstStep = step;
				return this;
			}
			
			if(lastStep == null) {
				lastStep = step;
				firstStep.chainWith(lastStep);
				return this;
			}
			
			lastStep.chainWith(step);
			lastStep = step;
			return this;			
		}
		
		public StartupSequence build(PtolemyApplicationContext context) {
			return new StartupSequence(firstStep, context);
		}		
	}
 	
}
