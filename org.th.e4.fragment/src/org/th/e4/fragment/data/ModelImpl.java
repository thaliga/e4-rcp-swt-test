package org.th.e4.fragment.data;

public class ModelImpl implements Model {

	private String text = "";

	@Override
	public final String getText() {
		return text;
	}

	@Override
	public final void setText(String text) {
		this.text = text;
	}
}
