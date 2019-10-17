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

package org.ciudadesabiertas.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class HttpUtil
{

	private static final Logger log = LoggerFactory.getLogger(Util.class);
		
		
		private static class DefaultTrustManager implements X509TrustManager
		{

			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
			{
			}

			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
			{
			}

			public X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}
		}

		private static CloseableHttpClient createAcceptSelfSignedCertificateClient(RequestConfig config) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
		{

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
			SSLContext.setDefault(sslContext);

			// we can optionally disable hostname verification.
			// if you don't want to further weaken the security, you don't have to include
			// this.
			HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

			// create an SSL Socket Factory to use the SSLContext with the trust self signed
			// certificate strategy
			// and allow all hosts verifier.
			SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);

			// finally create the HttpClient using HttpClient factory methods and assign the
			// ssl socket factory
			return HttpClients.custom().setSSLSocketFactory(connectionFactory).setDefaultRequestConfig(config).build();
		}

		public static String getPetition(String url) throws Exception
		{

			int timeout = 5;

			StringBuffer result = new StringBuffer();

			RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

			// CloseableHttpClient client =
			// HttpClientBuilder.create().setDefaultRequestConfig(config).build();

			CloseableHttpClient client = null;
			try
			{
				client = createAcceptSelfSignedCertificateClient(config);
			} catch (Exception e)
			{
				log.error("Error generating httpclient", e);
			}

			HttpGet get = new HttpGet(url);
			

			HttpResponse response = client.execute(get);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null)
			{
				result.append(line+System.getProperty("line.separator"));
			}

			client.close();

			return result.toString();
		}

		
		public static String getPetition(String url, List<Header> headers) throws Exception
		{

			int timeout = 5;

			StringBuffer result = new StringBuffer();

			RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
			
		
			CloseableHttpClient client = null;
			try
			{
				client = createAcceptSelfSignedCertificateClient(config);				
			} catch (Exception e)
			{
				log.error("Error generating httpclient", e);
			}

			HttpGet get = new HttpGet(url);
			for (Header h:headers)
			{
				get.addHeader(h);
			}

			HttpResponse response = client.execute(get);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

			String line = "";
			while ((line = rd.readLine()) != null)
			{
				result.append(line+System.getProperty("line.separator"));
			}
			
			Map<String,String> myHeaders=new HashMap<String,String>();
			Header[] allHeaders = response.getAllHeaders();
			for (Header header:allHeaders)
			{
				myHeaders.put(header.getName(), header.getValue());
			}

			
			
			
			client.close();
			
			
			
			return result.toString();
		}
		
		
	
		
				
		
		
		
		
		
	

}
