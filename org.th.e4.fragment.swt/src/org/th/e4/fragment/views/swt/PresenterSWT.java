package org.th.e4.fragment.views.swt;

import javax.annotation.PostConstruct;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.th.e4.fragment.data.ModelService;
import org.th.e4.fragment.views.Presenter;

public class PresenterSWT extends Presenter {

	@PostConstruct
	public void initialize(ViewSWT view, ModelService modelService) {
		this.view = view;
		setData(modelService.getModel());
		view.setModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				processModified();
			}
		});
		view.setUpdateListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				processUpdate();
			}
	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		view.setRevertListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				processRevert();
			}
	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

}
