int receivedChar;
boolean newData = false;

void setup() {
 Serial.begin(9600);
 Serial.println("<Arduino is ready>");
}

void loop() {
 recvOneChar();
 showNewData();
}

void recvOneChar() {
 if (Serial.available() > 0) {
 receivedChar = Serial.read();
 newData = true;
 }
}

void showNewData() {
  if (newData == true) {
    if (receivedChar == 1){
      Serial.println("speeding up");
      newData = false;
    }
    if (receivedChar == 2){
      Serial.println("slowing down");
      newData = false;
    }
 }
}

