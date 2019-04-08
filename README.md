# money-transfer-rest
A standalone java web server for RESTful money transfer transactions service.

Compiled and tested with: Java 8 (jdk_1.8_202)
External dependency: groupId: org.glassfish, artifactId javax.json (1.0.4)

```
git clone https://github.com/thiago-masaki/money-transfer-rest.git

cd money-transfer-rest

#Compile:
mvn install

#Start Server
java -cp target/RevolutRESTfulAPI-0.0.1-SNAPSHOT.jar:$HOME/.m2/repository/org/glassfish/javax.json/1.0.4/javax.json-1.0.4.jar com.revolut.restful.api.MoneyTransfer

#Execute Tests:
./tests.sh

```
#Example of Transfer  
```
POST 
http://hostname:8000/transfer
{                                           
    "Sender":{                        
        "Name":"Thiago",                     
        "SocialID":"001",                   
        "Bank":{
                "Name":"Revolut",
                "Branch":"0001",
                "AccountNumber":"000001-0"
        }
    },
    "Recipient":{
        "Name":"Vanessa",
        "SocialID":"002",
        "Bank":{
                "Name":"Revolut",
                "Branch":"0001",
                "AccountNumber":"000002-0"
        }
    },
    "Amount":100.0,
    "Currency":"USD"
}
```

#List Clients and their balance
```
GET 
http://hostname:8000/clients
```
