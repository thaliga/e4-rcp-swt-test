package org.th.e4.fragment.views.swt;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.th.e4.fragment.data.Model;
import org.th.e4.fragment.data.ModelService;

@RunWith(MockitoJUnitRunner.class)
public class PresenterSWTTest {

	class UpdateListenerCatcher implements Answer<Void> {

		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			updateListener = (SelectionListener) invocation.getArguments()[0];
			return null;
		}
	}

	class RevertListenerCatcher implements Answer<Void> {

		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			revertListener = (SelectionListener) invocation.getArguments()[0];
			return null;
		}
	}

	class ModifyListenerCatcher implements Answer<Void> {

		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			modifyListener = (ModifyListener) invocation.getArguments()[0];
			return null;
		}
	}

	@Mock
	protected ViewSWT view;
	@Mock
	protected Composite parent;
	@Mock
	protected ModelService modelService;
	@Mock
	protected Model model;
	SelectionListener updateListener;
	private final UpdateListenerCatcher updateListenerCatcher = new UpdateListenerCatcher();
	SelectionListener revertListener;
	private final RevertListenerCatcher revertListenerCatcher = new RevertListenerCatcher();
	ModifyListener modifyListener;
	private final ModifyListenerCatcher modifyListenerCatcher = new ModifyListenerCatcher();

	protected PresenterSWT createPresenter() {
		PresenterSWT presenter = spy(new PresenterSWT());
		doReturn(model).when(modelService).getModel();
		return presenter;
	}

	@Test
	public void initialize_SetsUpListeners() {
		// given
		PresenterSWT presenter = createPresenter();

		// when
		presenter.initialize(view, modelService);

		// then
		verify(view).setUpdateListener(any(SelectionListener.class));
		verify(view).setRevertListener(any(SelectionListener.class));
	}

	@Test
	public void initialize_UpdatesView() {
		// given
		PresenterSWT presenter = createPresenter();

		String expectedText = "expected";
		doReturn(expectedText).when(model).getText();

		// when
		presenter.initialize(view, modelService);

		// then
		verify(view).setModified(false);
		verify(view).setText(expectedText);
	}

	@Test
	public void initialize_SetsUpUpdateListener() throws Exception {
		// given
		PresenterSWT presenter = createPresenter();
		doAnswer(updateListenerCatcher).when(view).setUpdateListener(any(SelectionListener.class));

		// when
		presenter.initialize(view, modelService);

		// then
		assertNotEquals(null, updateListener);
	}

	@Test
	public void initialize_SetsUpRevertListener() throws Exception {
		// given
		PresenterSWT presenter = createPresenter();
		doAnswer(revertListenerCatcher).when(view).setRevertListener(any(SelectionListener.class));

		// when
		presenter.initialize(view, modelService);

		// then
		verify(view).setRevertListener(any(SelectionListener.class));
		assertNotEquals(null, revertListener);
	}

	@Test
	public void initialize_SetsUpModifyListener() throws Exception {
		// given
		PresenterSWT presenter = createPresenter();
		doAnswer(modifyListenerCatcher).when(view).setModifyListener(any(ModifyListener.class));

		// when
		presenter.initialize(view, modelService);

		// then
		verify(view).setModifyListener(any(ModifyListener.class));
		assertNotEquals(null, modifyListener);
	}
}
