package io.github.nabil.ptolemy.rcp.ui;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ExitHandlerTest {

	private SWTBot bot;

	@BeforeEach
	public void beforeClass() {
		bot = new SWTBot();		
	}

	@Test
	public void executeExit() {
		SWTBotMenu fileMenu = bot.menu("File");
		assertNotNull(fileMenu); //NOSONAR
		SWTBotMenu exitMenu = fileMenu.menu("Quit");
		assertNotNull(exitMenu); //NOSONAR
		exitMenu.click();

		SWTBotShell shell = bot.shell("Confirmation");
		SWTBot childBot = new SWTBot(shell.widget);
		SWTBotButton button = childBot.button("Cancel");
		assertTrue(button.isEnabled()); //NOSONAR
		button.click();
	}
}