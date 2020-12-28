package org.th.e4.fragment.data;

import org.eclipse.e4.core.di.InjectorFactory;
import org.eclipse.e4.core.di.annotations.Execute;
import org.th.e4.fragment.views.swt.ViewSWT;

public class Injector {

	@Execute
	public void doInjection() {
		InjectorFactory.getDefault().addBinding(ViewSWT.class).implementedBy(ViewSWT.class);
		InjectorFactory.getDefault().addBinding(ModelService.class).implementedBy(ModelServiceImpl.class);
	}
}
