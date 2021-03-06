package com.mcm.mobileservices.pizzaorder.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

@SuppressWarnings("deprecation")
public class RestClient {

	private List<NameValuePair> params;

	private List<NameValuePair> headers;

	private String url;

	private int responseCode;

	private String message;

	private String response;

	public enum RequestMethod {
		GET, POST
	}

	public RestClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public String execute(RequestMethod method) throws Exception {

		String gsonResponse = "";

		switch (method) {
		case GET: {
			// add parameters
			String combinedParams = "";
			if (!params.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : params) {
					String paramString = p.getName() + "="
							+ URLEncoder.encode(p.getValue(), "UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
			}

			HttpGet request = new HttpGet(url + combinedParams);

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}

			gsonResponse = executeRequest(request, url);
			break;
		}
		case POST: {
			HttpPost request = new HttpPost(url);

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}

			if (!params.isEmpty()) {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}

			gsonResponse = executeRequest(request, url);
			break;
		}
		}
		return gsonResponse;
	}

	private String executeRequest(HttpUriRequest request, String url) {

		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();

		HttpResponse httpResponse;

		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);

				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return response;
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public static void main(String[] args) {
		// String url =
		// "http://tomcat7-shrikanthavale.rhcloud.com/PizzaOrderService/rest/pizzaorder/readusername?telephonenumber=12345678901";
		// String url =
		// "http://localhost:8086/PizzaOrderService/rest/pizzaorder/readusername?telephonenumber=1234567890&sessionid=dsadasdas";
		// String url =
		// "http://localhost:8086/PizzaOrderService/rest/pizzaorder/readfiveactivepizza?sessionid=2bc6777a-251e-407f-b8ee-d3ecf4616ed1";
		// String url =
		// "http://localhost:8086/PizzaOrderService/rest/pizzaorder/readpizzanameselectedbyuser?sessionid=2bc6777a-251e-407f-b8ee-d3ecf4616ed1&pizzanumber=5";
		// String url =
		// "http://localhost:8086/PizzaOrderService/rest/pizzaorder/readpizzadetailsselectedbyuser?sessionid=2bc6777a-251e-407f-b8ee-d3ecf4616ed1&pizzanumber=5";
		String url = "http://localhost:8086/PizzaOrderService/rest/pizzaorder/writepizzaaddedinbasket?sessionid=2bc6777a-251e-407f-b8ee-d3ecf4616ed1&pizzanumber=4&size=large&numberofpizzas=5";
		// String url =
		// "http://localhost:8086/PizzaOrderService/rest/pizzaorder/getfinalcompleteorder?sessionid=2bc6777a-251e-407f-b8ee-d3ecf4616ed1";
		// String url =
		// "http://localhost:8086/PizzaOrderService/rest/pizzaorder/readuseraddress?telephonenumber=1234567890";
		// String url =
		// "http://localhost:8086/PizzaOrderService/rest/pizzaorder/removeallpizzafrombasket?sessionid=2bc6777a-251e-407f-b8ee-d3ecf4616ed1";
		// http://localhost:8086/PizzaOrderService/rest/pizzaorder/readusername?telephonenumber=12345678901

		RestClient restClient = new RestClient(url);

		try {
			System.out.println(restClient.execute(RequestMethod.GET));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}