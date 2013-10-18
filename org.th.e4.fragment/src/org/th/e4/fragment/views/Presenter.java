package org.th.e4.fragment.views;

import org.th.e4.fragment.data.Model;
import org.th.e4.fragment.data.ModelService;

abstract public class Presenter {

	protected View view;

	private Model model;

	protected void initialize(View view, ModelService modelService) {
		this.view = view;
		setData(modelService.getModel());
	}

	public void setData(Model model) {
		this.model = model;
		view.setModified(false);
		view.setText(model.getText());
	}

	protected void processUpdate() {
		model.setText(view.getText());
		view.setModified(false);
	}

	protected void processRevert() {
		view.setText(model.getText());
		view.setModified(false);
	}

	protected void processModified() {
		view.setModified(true);
	}
}
