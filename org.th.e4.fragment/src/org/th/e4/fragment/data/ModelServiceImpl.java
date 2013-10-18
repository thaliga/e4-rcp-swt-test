package org.th.e4.fragment.data;

public class ModelServiceImpl implements ModelService {

	private final Model model = new ModelImpl();

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public boolean validate(Model model) {
		return model != null;
	}
}
