package org.th.e4.fragment.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class ModelServiceImplTest {

	@Test
	public void getModel_ReturnsNotNullValues() {
		// given
		ModelService service = new ModelServiceImpl();

		// when
		Model model = service.getModel();

		// then
		assertNotNull(model);
		assertNotNull(model.getText());
	}

	@Test
	public void validate_Null_ReturnsFalse() throws Exception {
		// given
		ModelService service = new ModelServiceImpl();
		Model model = null;

		// when
		boolean result = service.validate(model);

		// then
		assertFalse(result);
	}

	@Test
	public void validate_NotNull_ReturnsTrue() throws Exception {
		// given
		ModelService service = new ModelServiceImpl();
		Model model = mock(Model.class);

		// when
		boolean result = service.validate(model);

		// then
		assertTrue(result);
	}
}
