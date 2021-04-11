package BackendTestControllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.adedoyin.backend.question2.controllers.BackendTestController;
import com.adedoyin.backend.question2.dao.CardSchemeDao;
import com.adedoyin.backend.question2.interfaces.CardSchemeRepository;
import com.adedoyin.backend.question2.models.CardScheme;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BackendTestController.class)

public class BackendTestControllerTest {
	@InjectMocks
	private BackendTestController controller;

	@Autowired
	private MockMvc mock;

	@MockBean
	private CardSchemeDao cardSchemeDao;

	@MockBean
	private CardSchemeRepository cardSchemeRepository;

	@Mock
	CardScheme cardScheme;

	@Test
	void testSayHello() throws Exception {
		RequestBuilder builder = MockMvcRequestBuilders.get("/");
		MvcResult result = mock.perform(builder).andReturn();
		assertEquals("Hello! from Ayeni Jeremiah", result.getResponse().getContentAsString());
	}

	@Test
	void getAllTest() throws Exception {
		RequestBuilder builder = MockMvcRequestBuilders.get("/card-scheme/stats/");
		MvcResult result = mock.perform(builder).andReturn();
		assertEquals("application/json", result.getResponse().getContentType());
	}

	@Test
	void cardSchemeVerifyTest() throws Exception {
		String number = "5585558555855583";
		CardScheme card = new CardScheme("1", "visa", "debit", "UBC", 0);
		Optional<CardScheme> thisCard = Optional.of(card);
		when(cardSchemeDao.getCardDetails(number)).thenReturn(thisCard);

		RequestBuilder builder = MockMvcRequestBuilders.get("/card-scheme/verify/" + number);
		MvcResult result = mock.perform(builder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("application/json", result.getResponse().getContentType());
	}

	@Test
	void cardSchemeVerifyNotExistTest() throws Exception {
		String number = "55855585558555831";

		RequestBuilder builder = MockMvcRequestBuilders.get("/card-scheme/verify/" + number);
		MvcResult result = mock.perform(builder).andReturn();
		assertEquals("/card-scheme/verify/55855585558555831", result.getRequest().getPathInfo());
		assertEquals(404, result.getResponse().getStatus());
		assertEquals("application/json", result.getResponse().getContentType());
	}

	@Test
	void cardSchemeStatsTest() throws Exception {

		RequestBuilder builder = MockMvcRequestBuilders.get("/card-scheme/stats?start=-20&limit=0");
		MvcResult result = mock.perform(builder).andReturn();
		assertEquals("-20", result.getRequest().getParameter("start"));
		assertEquals("0", result.getRequest().getParameter("limit"));
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("application/json", result.getResponse().getContentType());
	}

	@Test
	void cardSchemeStatsParseErrorTest() throws Exception {

		RequestBuilder builder = MockMvcRequestBuilders.get("/card-scheme/stats?start=a&limit=0");
		MvcResult result = mock.perform(builder).andReturn();
		assertEquals("a", result.getRequest().getParameter("start"));
		assertEquals("0", result.getRequest().getParameter("limit"));
		assertEquals(400, result.getResponse().getStatus());
		assertEquals("application/json", result.getResponse().getContentType());
	}

}
