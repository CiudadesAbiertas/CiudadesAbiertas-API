/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package org.ciudadesabiertas.contratos;

import static org.junit.Assert.assertFalse;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.AwardController;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwardTestFK {

	@Autowired
	private WebApplicationContext wac;

	private static boolean activeFK = StartVariables.activeFK;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test01_Add() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "AW_TEST01";
			String item = " {\n" + "    \"id\": \"" + id + "\",\n" 
					+ "    \"isSupplierFor\": \"B22183370\",\n"
					+ "    \"awardDate\": \"2018-11-29T00:00:00\",\n" 
					+ "    \"valueAmount\": \"63032.6\",\n"
					+ "    \"description\": \"Descripcion\"\n" + "}";

			item = new String(item.getBytes(), "UTF-8");

			this.mockMvc.perform(MockMvcRequestBuilders.post(AwardController.ADD)
					.contentType(MediaType.APPLICATION_JSON).content(item))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		}
	}

	@Test
	public void test01_Add_ERROR_FK() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "AW_TEST02";
			String item = " {\n" + "    \"id\": \"" + id + "\",\n" + "    \"isSupplierFor\": \"A_NOEXISTE\",\n"
					+ "    \"awardDate\": \"2018-11-29T00:00:00\",\n" + "    \"valueAmount\": \"63032.6\",\n"
					+ "    \"description\": \"Descripcion\"\n" + "}";

			item = new String(item.getBytes(), "UTF-8");

			this.mockMvc.perform(MockMvcRequestBuilders.post(AwardController.ADD)
					.contentType(MediaType.APPLICATION_JSON).content(item))
					.andExpect(MockMvcResultMatchers.status().isConflict());

		}
	}

	@Test
	public void test07_Update() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "AW_TEST01";
			String item = " {\n" + "    \"id\": \"" + id + "\",\n" + "    \"isSupplierFor\": \"B22183370\",\n"
					+ "    \"awardDate\": \"2018-11-29T00:00:00\",\n" + "    \"valueAmount\": \"163032.6\",\n"
					+ "    \"description\": \"XXXDescripcion\"\n" + "}";

			String itemUPDATE = new String(item.getBytes(), "UTF-8");

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.put(AwardController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON).content(itemUPDATE))

					.andExpect(MockMvcResultMatchers.status().isOk());

		}
	}

	@Test
	public void test07_Update_ERROR_FK() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "AW_TEST01";
			String item = " {\n" + "    \"id\": \"" + id + "\",\n" + "    \"isSupplierFor\": \"A_NOEXISTE\",\n"
					+ "    \"awardDate\": \"2018-11-29T00:00:00\",\n" + "    \"valueAmount\": \"163032.6\",\n"
					+ "    \"description\": \"XXXDescripcion\"\n" + "}";

			String itemUPDATE = new String(item.getBytes(), "UTF-8");

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.put(AwardController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON).content(itemUPDATE))

					.andExpect(MockMvcResultMatchers.status().isConflict());

		}
	}

	@Test
	public void test08_Delete_FK_ERROR() throws Exception {
		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "0085526-19-AW1";

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.delete(AwardController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON))

					.andExpect(MockMvcResultMatchers.status().isConflict());

		}

	}

	@Test
	public void test09_Delete_FK_ERROR() throws Exception {
		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "0013496-19-AW1";

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.delete(AwardController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON))

					.andExpect(MockMvcResultMatchers.status().isConflict());

		}

	}

	@Test
	public void test99_Delete() throws Exception {
		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id = "AW_TEST01";

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.delete(AwardController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON))

					.andExpect(MockMvcResultMatchers.status().isOk());

		}

	}

}
