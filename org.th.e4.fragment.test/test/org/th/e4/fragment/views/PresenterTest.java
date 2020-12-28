package org.th.e4.fragment.views;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.th.e4.fragment.data.Model;
import org.th.e4.fragment.data.ModelService;

@RunWith(MockitoJUnitRunner.class)
public class PresenterTest {

	@Mock
	protected View view;

	@Mock
	protected ModelService modelService;

	@Mock
	protected Model model;

	@Test
	public void initialize_UpdatesView() {
		// given
		Presenter presenter = createPresenter();

		String expectedText = "expected";
		doReturn(expectedText).when(model).getText();

		// when
		presenter.initialize(view, modelService);

		// then
		verify(view).setModified(false);
		verify(view).setText(expectedText);
	}

	@Test
	public void initialize_SetsModel() {
		// given
		Presenter presenter = createPresenter();

		String expectedText = "expected";
		doReturn(expectedText).when(model).getText();

		// when
		presenter.initialize(view, modelService);

		// then
		verify(presenter).setData(model);
	}

	@Test
	public void processUpdate_ModelSet_SetsToNotModifiedWithoutTextUpdate() {
		// given
		Presenter presenter = createPresenter();
		presenter.initialize(view, modelService);

		reset(view);
		String expectedText = "expected";
		doReturn(expectedText).when(view).getText();

		// when
		presenter.processUpdate();

		// then
		verify(view).setModified(false);
		verify(view, never()).setText(anyString());
		verify(model).setText(expectedText);
	}

	@Test(expected = NullPointerException.class)
	public void processUpdate_ModelNotSet_ThrowsException() {
		// given
		Presenter presenter = createPresenter();
		doAnswer(null).when(modelService).getModel();
		presenter.initialize(view, modelService);

		reset(view);
		String expectedText = "expected";
		doReturn(expectedText).when(view).getText();

		// when
		presenter.processUpdate();
	}

	@Test
	public void processRevert_ModelSet_SetsToNotModifiedWithTextUpdate() throws Exception {
		// given
		Presenter presenter = createPresenter();
		presenter.initialize(view, modelService);

		reset(view);
		String expectedText = "expected";
		doReturn(expectedText).when(model).getText();

		// when
		presenter.processRevert();

		// then
		verify(view).setModified(false);
		verify(view).setText(expectedText);
	}

	@Test(expected = NullPointerException.class)
	public void processRevert_ModelNotSet_ThrowsException() throws Exception {
		// given
		Presenter presenter = createPresenter();
		doAnswer(null).when(modelService).getModel();
		presenter.initialize(view, modelService);

		// when
		presenter.processRevert();
	}

	@Test
	public void processModify_SetsToModifiedWithoutTextUpdate() throws Exception {
		// given
		Presenter presenter = createPresenter();
		presenter.initialize(view, modelService);
		reset(view);

		// when
		presenter.processModified();

		// then
		verify(view).setModified(true);
		verify(view, never()).setText(anyString());
	}

	protected Presenter createPresenter() {
		Presenter presenter = spy(new Presenter() {
		});
		doReturn(model).when(modelService).getModel();
		return presenter;
	}
}
