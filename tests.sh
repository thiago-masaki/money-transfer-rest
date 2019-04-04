printf "Request POST:\n";
cat test1.json
printf "Response:\n";
curl  -d@test1.json http://0.0.0.0:8000/transfer 2>/dev/null
printf "\n\n************************************************\n"

printf "\nRequest POST:\n";
cat test2.json
printf "Response:";
curl  -d@test2.json http://0.0.0.0:8000/transfer 2>/dev/null
printf "\n\n************************************************\n"

printf "\n\nRequest GET:\nhttp://0.0.0.0:8000/testTransfer\nResponse:\n" 
curl http://0.0.0.0:8000/clients 2>/dev/null


