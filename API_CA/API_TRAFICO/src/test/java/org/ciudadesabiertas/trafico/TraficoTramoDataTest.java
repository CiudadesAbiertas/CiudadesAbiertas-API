/**
 * 
 */
package org.ciudadesabiertas.trafico;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.TraficoTramoController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Hugo Lafuente Matesanz (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(Parameterized.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TraficoTramoDataTest {

private static final Logger log = LoggerFactory.getLogger(TraficoTramoDataTest.class);

@Autowired
private WebApplicationContext wac;

private MockMvc mockMvc;

private String listURL = TraficoTramoController.LIST;

// Manually config for spring to use Parameterised
private TestContextManager testContextManager;

@Parameter(value = 0)
public String paramName;

@Parameter(value = 1)
public String value;

@Parameter(value = 2)
public Integer expected;

// Posibles valores que tomaran los parámetros anteriores
@Parameters(name = "{index}: test {0}")
public static Collection<Object[]> data() {
  Collection<Object[]> params = new ArrayList<>();
  params.add(new Object[] { "id", "TRAFTRAM01", 1 });
  params.add(new Object[] { "description", "Calles entre el cruce de Alcalá con Gran Vía y la Plaza de la Independencia",  1 });
  params.add(new Object[] { "xETRS89", "440124.33", 1 });
  params.add(new Object[] { "yETRS89", "4474637.17", 1 });
  params.add(new Object[] { "xETRS89Fin", "440124.43", 1 });
  params.add(new Object[] { "yETRS89Fin", "4474637.27", 1 });
  params.add(new Object[] { "municipioId", "28079", 2 });
  params.add(new Object[] { "municipioTitle", "Madrid", 2 });
  params.add(new Object[] { "xETRS89", "440124.33", 1 });
  params.add(new Object[] { "yETRS89", "4474637.17", 1 });


  return params;
}

@Before
public void setup() throws Exception {
  this.testContextManager = new TestContextManager(getClass());
  this.testContextManager.prepareTestInstance(this);
  this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
}

@Test
public void test_evaluador_DATA() throws Exception {

  JSONArray records = TestUtils.extractRecords(listURL, paramName, value, mockMvc);
  try {
	assertTrue(records.size() == expected);
  } catch (AssertionError e) {
	log.error("Assertion error");
	log.error("  Param: " + paramName);
	log.error("  Value: " + value);
	log.error("  Expected: " + expected);

	throw new AssertionError("Incorrect value on expected " + paramName + ": " + records.size(), new Throwable("Expected: " + expected));
  }
}







}
