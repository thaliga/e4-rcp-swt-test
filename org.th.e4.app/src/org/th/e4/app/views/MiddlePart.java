package org.th.e4.app.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.widgets.Composite;
import org.th.e4.app.inject.InjectableData;

public class MiddlePart {

	@Inject
	private InjectableData data;

	@PostConstruct
	public void createControls(Composite parent, InjectableData data) {
		if (data == null || parent == null) {
			throw new NullPointerException();
		}

		if (this.data == data) {

		}
	}
}