package com.revolut.model.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.revolut.restful.json.ObjectToJsonConverter;

public class DataStore {
	private static HashMap<String, ClientAccount> clients = new HashMap<String, ClientAccount>();

	public static void initializeClientsMockData() {
		clients.put("0001" + "000001-0", new ClientAccount("001", "Thiago Masaki", "01/01/1980",
				"Figueiras Street, 2000", "0001", "000001-0", 0.0));
		clients.put("0001" + "000002-0",
				new ClientAccount("002", "Joao", "02/02/1990", "Street One, 1000", "0001", "000002-0", 0.0));
		clients.put("0001" + "000003-0",
				new ClientAccount("003", "Maria", "03/03/2000", "Street One, 1000", "0001", "000003-0", 0.0));
	}

	public static String getClientList() {
		StringBuilder clientList = new StringBuilder();
		clientList.append("{ \"Accounts\": [\n");
		Iterator cit = clients.entrySet().iterator();
		while (cit.hasNext()) {
			Map.Entry pair = (Entry) cit.next();
			ClientAccount value = (ClientAccount) pair.getValue();
			clientList.append("\"").append(value.getName()).append("\"").append(":").append(value.getBalance());
			if (cit.hasNext())
				clientList.append(",\n");
		}
		clientList.append("\n]\n}\n");
		return clientList.toString();
	}

	public static String executeTransfer(Transfer transferObject) {
		ObjectToJsonConverter obj2json = new ObjectToJsonConverter();

		ClientAccount receiver = DataStore.getClient(transferObject.getRecipient().getBank().getBranch(),
				transferObject.getRecipient().getBank().getAccountNumber());
		ClientAccount sender = DataStore.getClient(transferObject.getSender().getBank().getBranch(),
				transferObject.getSender().getBank().getAccountNumber());

		if (!receiver.getSocialID().equals(transferObject.getRecipient().getSocialID())) {
			sender.withdrawal(transferObject.getAmount());
			receiver.deposit(transferObject.getAmount());
			return obj2json
					.convertToJson(new TransactionStatus("FAILED", "The SocialID of receiver does not match", -1));
		}
		return obj2json.convertToJson(new TransactionStatus("SUCCESS", "Transfer completed successfully", 0));
	}

	public static ClientAccount getClient(String Branch, String AccountNumber) {
		return clients.get(Branch + AccountNumber);
	}

	public static HashMap<String, ClientAccount> getClients() {
		return clients;
	}

	public static void setClients(HashMap<String, ClientAccount> clients) {
		DataStore.clients = clients;
	}
}
