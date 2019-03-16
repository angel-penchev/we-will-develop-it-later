#include <SoftwareSerial.h>
#include <Servo.h>

Servo ser1;
Servo ser2;
SoftwareSerial bt(12,11);

void setup(){
  Serial.begin(38400);
  bt.begin(9600);
  ser1.attach(10);
  ser1.write(0);
  ser2.attach(9);
  ser2.write(0);
}

void loop(){
  
  if(bt.available()>0){
    int val=bt.read() & ~(1<<7);
    Serial.println(val,DEC);
    Serial.println(val,BIN);
    int pos1=val & 7;
    int pos2=(val & 56)>>3;
    Serial.print(pos1,BIN);
    Serial.print(" ");
    Serial.println(pos2,BIN);
    Serial.print(pos1,DEC);
    Serial.print(" ");
    Serial.println(pos2,DEC);
    ser1.write((pos1)*22.5);
    ser2.write((pos2)*22.5);
  }
}
