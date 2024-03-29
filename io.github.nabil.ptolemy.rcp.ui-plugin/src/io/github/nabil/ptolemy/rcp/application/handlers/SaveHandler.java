package io.github.nabil.ptolemy.rcp.application.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;

public class SaveHandler {

	@Inject
	private PtolemyApplicationContext ptolemyApplicationContext;

	@CanExecute
	public boolean canExecute(EPartService partService) {
		return true;
	}

	@Execute
	public void execute(EPartService partService) {
		if (!ptolemyApplicationContext.storageManager().saveDbFile()) {
			throw new RuntimeException("Unable to save DB file");
		}

		partService.saveAll(false);
		System.out.println("Save handler called!");
	}
}