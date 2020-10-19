/**
 * 
 */
package org.ciudadesabiertas.trafico;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.TraficoDispositivoMedicionController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
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
public class TraficoDispositivoMedicionRSQLTest {

private static final Logger log = LoggerFactory.getLogger(TraficoDispositivoMedicionRSQLTest.class);


@Autowired
private WebApplicationContext wac;

private MockMvc mockMvc;

private String listURL = TraficoDispositivoMedicionController.LIST;

// Manually config for spring to use Parameterised
private TestContextManager testContextManager;

@Parameter(value = 0)
public String expression;

@Parameter(value = 1)
public Integer expected;

// Posibles valores que tomaran los parámetros anteriores
@Parameters(name = "{index}: test {0}")
public static Collection<Object[]> data() {
  
  Collection<Object[]> params = new ArrayList<>();
  params.add(new Object[] { "id", "TRAFDISMED01", 1 });
  params.add(new Object[] { "description",
	  "Dispositivo que detecta los cambios que se producen en un campo electromagnético cuando circula un vehículo (masa metálica) sobre un punto determinado de la calzada. Registra el número total de vehículos que pasan y pueden clasificarlos por su longitud, número de ejes y masas.",
	  1 });
  params.add(new Object[] { "numSentidos", "2", 2 });
  params.add(new Object[] { "numCarriles", "2", 1 });
  params.add(new Object[] { "urbano", "true", 2 });
  params.add(new Object[] { "tipoEquipoTrafico", "lazo-magnetico", 1 });
  params.add(new Object[] { "monitorea", "TRAFTRAM01", 1 });
  params.add(new Object[] { "enServicio", "false", 1 });
  params.add(new Object[] { "frecuenciaMedicion", "5 minutos", 1 });
  params.add(new Object[] { "observes", "carga", 2 });
  params.add(new Object[] { "frecuenciaMedicion", "5 minutos", 1 });
  params.add(new Object[] { "xETRS89", "440124.33", 1 });
  params.add(new Object[] { "yETRS89", "4474637.17", 1 });
  params.add(new Object[] { "streetAddress", "Bravo Murillo 265", 1 });
  params.add(new Object[] { "portalId", "PORTAL000012", 1 });
  params.add(new Object[] { "postalCode", "28039", 1 });
  params.add(new Object[] { "municipioId", "28079", 1 });
  params.add(new Object[] { "municipioTitle", "Madrid", 1 });
  params.add(new Object[] { "barrioId", "280796062", 1 });
  params.add(new Object[] { "barrioTitle", "Cuatro Caminos",1 });
  params.add(new Object[] { "distritoId", "28079606", 1 });
  params.add(new Object[] { "distritoTitle", "Tetuán", 1 });
  
 Collection<Object[]> paramsRSQL = new ArrayList<>();
  
  for (Object[] obj:params)
  {
	paramsRSQL.add(new Object[] { obj[0]+"=='"+obj[1]+"'", obj[2] });
  }
    

  return paramsRSQL;
}

@Before
public void setup() throws Exception {
  this.testContextManager = new TestContextManager(getClass());
  this.testContextManager.prepareTestInstance(this);
  this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
}



@Test
public void test_evaluador_RSQL() throws Exception {
  
  JSONArray records = TestUtils.extractRecords(listURL, "q", expression, mockMvc);
  
  try
	{
	assertTrue(records.size() == expected);
	}
	catch (AssertionError e)
	{
	  log.error("Assertion error");
	  log.error("  Expression: "+expression);
	  log.error("  Expected: "+expected);
	  
	  throw new AssertionError("Incorrect value on Expresion "+expression+": "+records.size(), new Throwable("Expected: "+expected));
	}
}

}
