package org.th.e4.fragment.views.swt;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.th.e4.fragment.views.View;

public class ViewSWT implements View {
	private Text textInput;
	private Button checkButton;
	private Button updateButton;
	private Button revertButton;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FormLayout());

		textInput = new Text(parent, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.bottom = new FormAttachment(0, 93);
		fd_text.top = new FormAttachment(0, 31);
		fd_text.left = new FormAttachment(0, 10);
		fd_text.right = new FormAttachment(0, 214);
		textInput.setLayoutData(fd_text);
		textInput.setToolTipText("Write your message");

		checkButton = new Button(parent, SWT.CHECK);
		checkButton.setSelection(true);
		checkButton.setEnabled(false);
		FormData fd_checkButton = new FormData();
		fd_checkButton.top = new FormAttachment(textInput, 16);
		fd_checkButton.left = new FormAttachment(0, 38);
		checkButton.setLayoutData(fd_checkButton);
		checkButton.setText("Up-to-date");

		updateButton = new Button(parent, SWT.CENTER);
		updateButton.setEnabled(false);
		FormData fd_okButton = new FormData();
		fd_okButton.top = new FormAttachment(checkButton, 18);
		fd_okButton.left = new FormAttachment(checkButton, 0, SWT.LEFT);
		updateButton.setLayoutData(fd_okButton);
		updateButton.setText("Update");

		revertButton = new Button(parent, SWT.CENTER);
		revertButton.setEnabled(false);
		fd_checkButton.right = new FormAttachment(100, -54);
		FormData fd_cancelButton = new FormData();
		fd_cancelButton.top = new FormAttachment(updateButton, 0, SWT.TOP);
		fd_cancelButton.right = new FormAttachment(checkButton, 0, SWT.RIGHT);
		revertButton.setLayoutData(fd_cancelButton);
		revertButton.setText("Revert");
	}

	private void updateControls(boolean modified) {
		checkButton.setSelection(!modified);
		updateButton.setEnabled(modified);
		revertButton.setEnabled(modified);
	}

	@Override
	public void setModified(boolean modified) {
		updateControls(modified);
	}

	@Override
	public String getText() {
		return textInput.getText();
	}

	@Override
	public void setText(String text) {
		textInput.setText(text);
	}

	public void setUpdateListener(SelectionListener updateListener) {
		updateButton.addSelectionListener(updateListener);
	}

	public void setRevertListener(SelectionListener revertListener) {
		revertButton.addSelectionListener(revertListener);
	}

	public void setModifyListener(ModifyListener modifyListener) {
		textInput.addModifyListener(modifyListener);
	}
}
