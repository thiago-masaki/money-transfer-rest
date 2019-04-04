package com.revolut.restful.api;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.revolut.model.data.DataStore;
import com.revolut.model.data.Transfer;
import com.revolut.restful.json.JsonToObjectConverter;
import com.sun.net.httpserver.HttpServer;

public class MoneyTransfer {

	public static void main(String[] args) throws Exception {
		DataStore.initializeClientsMockData();

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/transfer", new TransferMicroService());
		server.createContext("/clients", new ClientsMicroService());
		server.setExecutor(null); // creates a default executor
		System.out.println("Server Listening on port 8000");
		server.start();
	}

	static class TransferMicroService extends HTTPHandler {
		public void doPost(HTTPRequest req, HTTPResponse resp) throws Exception {
			JsonToObjectConverter json2obj = new JsonToObjectConverter<com.revolut.model.data.Transfer>();
			Transfer transferObject = (Transfer) json2obj.convertToObject(req.getBody(), Transfer.class);

			String jsonresp = DataStore.executeTransfer(transferObject);

			resp.sendResponseHeaders(200, jsonresp.length());
			resp.getWriter().append(jsonresp);
		}
	}

	static class ClientsMicroService extends HTTPHandler {
		public void doGet(HTTPRequest req, HTTPResponse resp) throws IOException {
			String list = DataStore.getClientList();
			System.out.println(list);
			resp.sendResponseHeaders(200, list.length());
			resp.getWriter().append(list);
		}
	}
}