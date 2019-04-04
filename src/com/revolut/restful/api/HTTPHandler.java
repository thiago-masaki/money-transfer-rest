package com.revolut.restful.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class HTTPHandler implements HttpHandler {

	class HTTPRequest {
		private Map<String, String> arguments;
		private String requestbody;

		public HTTPRequest(HttpExchange t) {
			queryToMap(t);
			bodyToString(t);
		}

		public String getParameter(String arg) {
			return arguments.get(arg);
		}

		public String getBody() {
			return requestbody;
		}

		private void bodyToString(HttpExchange t) {
			StringBuilder body = new StringBuilder();
			try {
				InputStreamReader reader = new InputStreamReader(t.getRequestBody(), "UTF-8");
				char[] buffer = new char[256];
				int read;
				while ((read = reader.read(buffer)) != -1) {
					body.append(buffer, 0, read);
				}
				requestbody = body.toString();
			} catch (Exception e) {
				body = null;
			}
		}

		private void queryToMap(HttpExchange t) {
			String query = t.getRequestURI().getQuery();

			if (query == null) {
				arguments = null;
				return;
			}

			arguments = new HashMap<>();
			for (String param : query.split("&")) {
				String[] entry = param.split("=");
				if (entry.length > 1) {
					arguments.put(entry[0], entry[1]);
				} else {
					arguments.put(entry[0], "");
				}
			}
		}
	}

	class HTTPResponse {
		private HttpExchange exchange;
		PrintWriter printwriter;

		public HTTPResponse(HttpExchange t) {
			exchange = t;
			printwriter = new PrintWriter(t.getResponseBody());
		}

		public void sendResponseHeaders(int statusCode, int length) throws IOException {
			exchange.sendResponseHeaders(statusCode, length);
		}

		public PrintWriter getWriter() {
			return printwriter;
		}

		public void write() throws IOException {
			printwriter.close();
		}

		public void write(String arg) throws IOException {
			exchange.getResponseBody().write(arg.getBytes());
			exchange.getResponseBody().close();
		}
	}

	@SuppressWarnings("restriction")
	@Override
	public void handle(HttpExchange t) {
		System.out.println(t.getRequestMethod());

		try {
			if (t.getRequestMethod().equals("GET"))
				this.doGet(t);
			else //if (t.getRequestMethod().equals("POST"))
				this.doPost(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doGet(HttpExchange t) {
		HTTPRequest req = new HTTPRequest(t);
		HTTPResponse resp = new HTTPResponse(t);
		try {
			doGet(req, resp);
			resp.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doPost(HttpExchange t) {
		HTTPRequest req = new HTTPRequest(t);
		HTTPResponse resp = new HTTPResponse(t);
		try {
			doPost(req, resp);
			resp.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HTTPRequest request, HTTPResponse response) throws Exception {}

	public void doPost(HTTPRequest request, HTTPResponse response) throws Exception {}

}
