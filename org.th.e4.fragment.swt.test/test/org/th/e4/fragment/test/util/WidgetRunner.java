package org.th.e4.fragment.test.util;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.th.e4.fragment.views.swt.ViewSWT;

public class WidgetRunner {

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		Composite parent = shell;
		new ViewSWT().createControls(parent);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
