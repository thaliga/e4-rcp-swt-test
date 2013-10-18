package org.th.e4.app.views;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.widgets.Composite;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.th.e4.app.inject.InjectableDataImpl;
import org.th.e4.app.views.MiddlePart;

public class MiddlePartTest {

	@Mock
	MApplication app;

	@Mock
	Composite parent;

	@Mock
	InjectableDataImpl data;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createControls_WithoutException() {
		// given
		MiddlePart part = new MiddlePart();

		// when
		part.createControls(parent, data);

		// then
	}

	@Test(expected = NullPointerException.class)
	public void createControls_NullData_ThrowsNPException() {
		// given
		MiddlePart part = new MiddlePart();
		data = null;

		// when
		part.createControls(parent, data);

		// then
	}

	@Test(expected = NullPointerException.class)
	public void createControls_NullParent_ThrowsNPException() {
		// given
		MiddlePart part = new MiddlePart();
		parent = null;

		// when
		part.createControls(parent, data);

		// then
	}
}
