package org.th.e4.fragment.views.swt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.th.e4.fragment.views.swt.ViewSWT;

@RunWith(MockitoJUnitRunner.class)
public class ViewTest {

	private static final int SWTBOT_TIMEOUT_FOR_DISABLED_WIDGETS = 100;
	private static final String FIRST_TEXT = "Hello World!";
	private static final String SECOND_TEXT = "Second message...";
	private static final String CHECKBOX_TEXT = "Up-to-date";
	private static final String UPDATE_BUTTON_TEXT = "Update";
	private static final String REVERT_BUTTON_TEXT = "Revert";

	private Display display;
	private SWTBotText textBox;
	private SWTBotCheckBox checkBox;
	private SWTBotButton updateButton;
	private SWTBotButton revertButton;

	@Before
	public void setUp() {
		display = new Display();
	}

	private ViewSWT createView(boolean modified) {
		final Shell shell = new Shell(display);
		ViewSWT view = new ViewSWT();
		view.createControls(shell);
		view.setText(FIRST_TEXT);
		view.setModified(modified);
		shell.pack();
		shell.open();
		findControls();
		return view;
	}

	@After
	public void tearDown() {
		display.dispose();
	}

	private void findControls() {
		SWTBot bot = new SWTBot();
		textBox = bot.text();
		checkBox = bot.checkBox();
		updateButton = bot.button(UPDATE_BUTTON_TEXT);
		revertButton = bot.button(REVERT_BUTTON_TEXT);
	}

	private void verifyTextBox(String text) {
		assertEquals(text, textBox.getText());
	}

	private void verifyVariants(boolean modified) {
		assertEquals(!modified, checkBox.isChecked());
		assertEquals(modified, updateButton.isEnabled());
		assertEquals(modified, revertButton.isEnabled());
	}

	private void verifyInvariants() {
		assertEquals(true, textBox.isEnabled());
		assertEquals(CHECKBOX_TEXT, checkBox.getText());
		assertEquals(false, checkBox.isEnabled());
	}

	private void verifyNotModifiedStateWithTextValue(String text) {
		verifyTextBox(text);
		verifyInvariants();
		verifyVariants(false);
	}

	private void verifyModifiedStateWithTextValue(String text) {
		verifyTextBox(text);
		verifyInvariants();
		verifyVariants(true);
	}

	@Test
	public void defaultValues() {

		// when
		createView(false);

		// then
		verifyNotModifiedStateWithTextValue(FIRST_TEXT);
	}

	@Test
	public void setModified_True_NotModifiesText() {
		// given
		ViewSWT part = createView(false);

		// when
		part.setModified(true);

		// then
		verifyModifiedStateWithTextValue(FIRST_TEXT);
	}

	@Test
	public void setModified_False_NotModifiesText() {
		// given
		ViewSWT part = createView(true);

		// when
		part.setModified(false);

		// then
		verifyNotModifiedStateWithTextValue(FIRST_TEXT);
	}

	@Test
	public void textEdit_NotifiesModifyListener() {
		// given
		ViewSWT view = createView(false);
		ModifyListener modifyListener = mock(ModifyListener.class);
		view.setModifyListener(modifyListener);

		// when
		textBox.setText(SECOND_TEXT);

		// then
		verify(modifyListener).modifyText(any(ModifyEvent.class));
		verifyNotModifiedStateWithTextValue(SECOND_TEXT);
	}

	@Test(expected = TimeoutException.class)
	public void updateClick_NotModified_NotPossible() throws Exception {
		// given
		createView(false);
		long savedTimeout = SWTBotPreferences.TIMEOUT;
		SWTBotPreferences.TIMEOUT = SWTBOT_TIMEOUT_FOR_DISABLED_WIDGETS;

		// when
		updateButton.click();

		// cleanup
		SWTBotPreferences.TIMEOUT = savedTimeout;
	}

	@Test(expected = TimeoutException.class)
	public void revertClick_NotModified_NotPossible() throws Exception {
		// given
		createView(false);
		long savedTimeout = SWTBotPreferences.TIMEOUT;
		SWTBotPreferences.TIMEOUT = SWTBOT_TIMEOUT_FOR_DISABLED_WIDGETS;

		// when
		revertButton.click();

		// cleanup
		SWTBotPreferences.TIMEOUT = savedTimeout;
	}

	@Test
	public void revertClick_Modified_NotifiesOnlyRevertListener() {
		// given
		ViewSWT part = createView(true);
		SelectionListener updateListener = mock(SelectionListener.class);
		part.setUpdateListener(updateListener);
		SelectionListener revertListener = mock(SelectionListener.class);
		part.setRevertListener(revertListener);

		// when
		revertButton.click();

		// then
		verify(updateListener, never()).widgetSelected(any(SelectionEvent.class));
		verify(revertListener).widgetSelected(any(SelectionEvent.class));
		verifyModifiedStateWithTextValue(FIRST_TEXT);
	}

	@Test
	public void updateClick_Modified_NotifiesOnlyUpdateListener() {
		// given
		ViewSWT part = createView(true);
		SelectionListener updateListener = mock(SelectionListener.class);
		part.setUpdateListener(updateListener);
		SelectionListener revertListener = mock(SelectionListener.class);
		part.setRevertListener(revertListener);

		// when
		updateButton.click();

		// then
		verify(updateListener).widgetSelected(any(SelectionEvent.class));
		verify(revertListener, never()).widgetSelected(any(SelectionEvent.class));
		verifyModifiedStateWithTextValue(FIRST_TEXT);
	}

	@Test
	public void getText_ReturnTextInputValue() throws Exception {
		// given
		ViewSWT part = createView(true);

		// when
		String result = part.getText();

		// then
		assertEquals(result, FIRST_TEXT);
	}
}
