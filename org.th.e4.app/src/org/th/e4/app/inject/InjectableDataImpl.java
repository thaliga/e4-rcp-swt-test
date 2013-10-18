package org.th.e4.app.inject;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;

public class InjectableDataImpl implements InjectableData {

	@Inject
	public InjectableDataImpl(MApplication application) {

	}
}
