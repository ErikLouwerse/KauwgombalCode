int receivedChar;
boolean newData = false;
boolean dispose;

void setup() {
 Serial.begin(9600);
 Serial.println("<Arduino1 is ready>");
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
    if (receivedChar == 3){
      newData = false;
      dispose = true;
      Serial.println("Disposing unnecissary balls");
    }
    if (receivedChar == 4){
      dispose = false;
      newData = false;
      Serial.println("Sorting unnecissary balls");
    }
 }
}

