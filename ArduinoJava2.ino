int receivedChar;
int amountYellow = -1;
int amountRed = -1;
int amountGreen = -1;
int amountBlue = -1;
int quantityPackage = -1;
boolean newData = false;

void setup() {
 Serial.begin(9600);
 Serial.println("<Arduino2 is ready>");
}

void loop() {
 recvOneChar();
 ProcesOrder();
 recvOneChar();
 ProcesOrder();
 recvOneChar();
 ProcesOrder();
 recvOneChar();
 ProcesOrder();
 recvOneChar();
 ProcesOrder();
 if (quantityPackage != -1){
  for (int i = -1; i < quantityPackage; i++){
   if (i == quantityPackage - 1){
    Serial.println("<Order is ready>");
    amountYellow = -1;
    amountRed = -1;
    amountGreen = -1;
    amountBlue = -1;
    quantityPackage = -1;
   }
   for (int i = 0; i < amountYellow; i++){
      Serial.println("Dropping yellow ball");
   }
   for (int i = 0; i < amountRed; i++){
      Serial.println("Dropping red ball");
   }
   for (int i = 0; i < amountGreen; i++){
      Serial.println("Dropping green ball");
   }
   for (int i = 0; i < amountBlue; i++){
      Serial.println("Dropping blue ball");
   }
  }
 }
}

void recvOneChar() {
 if (Serial.available() > 0) {
  receivedChar = Serial.read();
  newData = true;
 }
}

void ProcesOrder() {
  if (newData == true) {
    
    if (amountYellow == -1){
      amountYellow = receivedChar;
      Serial.println(amountYellow);
      newData = false;
    }
    else if (amountRed == -1){
      amountRed = receivedChar;
      Serial.println(amountRed);
      newData = false;
    }
    else if (amountGreen == -1){
      amountGreen = receivedChar;
      Serial.println(amountGreen);
      newData = false;
    }
    else if (amountBlue == -1){
      amountBlue = receivedChar;
      Serial.println(amountBlue);
      newData = false;
    }
    else if (quantityPackage == -1){
      quantityPackage = receivedChar;
      Serial.println(quantityPackage);
      Serial.println("Starting order");
      newData = false;
    }
  }
}

