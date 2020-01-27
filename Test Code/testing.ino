#include <Servo.h>

//#define S0 4
//#define S1 5
//#define S2 6
//#define S3 7
//#define sensorOut 8
#define S0 22
#define S1 24
#define S2 26
#define S3 28 
#define sensorOut 30

// Stores frequency read by the photodiodes
int redFrequency = 0;
int greenFrequency = 0;
int blueFrequency = 0;
int clearFrequency = 0;

// Stores the red. green and blue colors
int redColor = 0;
int greenColor = 0;
int blueColor = 0;
int cnt = 0;
int l = 3;

Servo s, s1;
int p = 3, p1 = 8;

void setup() {
  // put your setup code here, to run once:

  // Setting the outputs
  pinMode(S0, OUTPUT);
  pinMode(S1, OUTPUT);
  pinMode(S2, OUTPUT);
  pinMode(S3, OUTPUT);
//  pinMode(l, OUTPUT);
//   Setting the sensorOut as an input
  pinMode(sensorOut, INPUT);
//  Serial.print("Hello World");
//   Setting frequency scaling to 20%
  digitalWrite(S0,HIGH);
  digitalWrite(S1,LOW);
//  
  Serial.begin(9600);
  s.attach(p);
//  s1.attach(p1);
}

//void loop() {
//  // put your main code here, to run repeatedly:
////  Serial.println("Hello World");
////  for (int i = 0; i <= 180; i+=2) {
//////    s.write(i);
////    delay(1000);
////  }
//  // s.write(90);
////  s.write(50);
////  delay(7000);
////  s.write(130);
////  delay(7000);
////  s.write(60);
////  delay(7000);
////  s.write(80);
////  delay(7000);
//
//  s.write(0);
//} 
  
void loop() {
  // turn on LED
  digitalWrite(l, HIGH);
  s.write(30);
  cnt++  ;
  // Setting RED (R) filtered photo diodes to be read
  digitalWrite(S2,LOW);
  digitalWrite(S3,LOW);
  // Reading the output frequency
  redFrequency = pulseIn(sensorOut, LOW);
  // Remaping the value of the RED (R) frequency from 0 to 255
  // You must replace with your own values. Here's an example: 
//   redColor = map(redFrequency, 70, 120, 255,0);
//  redColor = map(redFrequency, XX, XX, 255,0);
  
  // Printing the RED (R) value
  Serial.print("R = ");
//  Serial.print(redColor);
Serial.print(redFrequency);
  delay(100);
  
  // Setting GREEN (G) filtered photodiodes to be read
  digitalWrite(S2,HIGH);
  digitalWrite(S3,HIGH);
  
  // Reading the output frequency
  greenFrequency = pulseIn(sensorOut, LOW);
  // Remaping the value of the GREEN (G) frequency from 0 to 255
  // You must replace with your own values. Here's an example: 
//   greenColor = map(greenFrequency, 100, 199, 255, 0);
//  greenColor = map(greenFrequency, XX, XX, 255, 0);
  
  // Printing the GREEN (G) value  
  Serial.print(" G = ");
//  Serial.print(greenColor);
Serial.print(greenFrequency);
  delay(100);
 
  // Setting BLUE (B) filtered photodiodes to be read
  digitalWrite(S2,LOW);
  digitalWrite(S3,HIGH);
  
  // Reading the output frequency
  blueFrequency = pulseIn(sensorOut, LOW);
  // Remaping the value of the BLUE (B) frequency from 0 to 255
  // You must replace with your own values. Here's an example: 
//   blueColor = map(blueFrequency, 38, 84, 255, 0);
//  blueColor = map(blueFrequency, XX, XX, 255, 0);
  
  // Printing the BLUE (B) value 
  Serial.print(" B = ");
//  Serial.print(blueColor);
Serial.print(blueFrequency);
  delay(100);

//  // Checks the current detected color and prints
//  // a message in the serial monitor
//  if(redColor > greenColor && redColor > blueColor){
//      Serial.println(" - RED detected!");
//  }
//  if(greenColor > redColor && greenColor > blueColor){
//    Serial.println(" - GREEN detected!");
//  }
//  if(blueColor > redColor && blueColor > greenColor){
//    Serial.println(" - BLUE detected!");
//  }
//digitalWrite(S2,HIGH);
//  digitalWrite(S3,LOW);
//  clearFrequency = pulseIn(sensorOut, LOW);
//  Serial.print(" C = ");
//  Serial.print(clearFrequency);
//  delay(100);

  Serial.println();
}
