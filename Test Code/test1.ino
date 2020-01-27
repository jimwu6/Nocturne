#include <Servo.h>

int rf1 = 0, rf2 = 0, gf1 = 0, gf2 = 0, bf1 = 0, bf2 = 0;
int s01 = 4;
int s11 = 5;
int s21 = 6;
int s31 = 7;
int out1 = 8;
int s02 = 22;
int s12 = 24;
int s22 = 26;
int s32 = 28;
int out2 = 30;

void setup() {
  // put your setup code here, to run once:

  pinMode(s01, OUTPUT);
  pinMode(s11, OUTPUT);
  pinMode(s21, OUTPUT);
  pinMode(s31, OUTPUT);
  pinMode(out1, INPUT);
  
  pinMode(s02, OUTPUT);
  pinMode(s12, OUTPUT);
  pinMode(s22, OUTPUT);
  pinMode(s32, OUTPUT);
  pinMode(out2, INPUT);

  digitalWrite(s01, HIGH);
  digitalWrite(s11, LOW);
  digitalWrite(s02, HIGH);
  digitalWrite(s12, LOW);
  
  Serial.begin(115200);
  
}

void loop() {
  // put your main code here, to run repeatedly:

  // red
  digitalWrite(s21, LOW);
  digitalWrite(s31, LOW);
  digitalWrite(s22, LOW);
  digitalWrite(s32, LOW);
  rf1 = pulseIn(out1, LOW);
  rf2 = pulseIn(out2, LOW);
  delay(50);

  // green
  digitalWrite(s21, HIGH);
  digitalWrite(s31, HIGH);
  digitalWrite(s22, HIGH);
  digitalWrite(s32, HIGH);
  gf1 = pulseIn(out1, LOW);
  gf2 = pulseIn(out2, LOW);
  delay(50);

  // blue
  digitalWrite(s21, LOW);
  digitalWrite(s31, HIGH);
  digitalWrite(s22, LOW);
  digitalWrite(s32, HIGH);
  bf1 = pulseIn(out1, LOW);
  bf2 = pulseIn(out2, LOW);
  delay(50);

  Serial.print("1: R=");
  Serial.print(rf1);
  Serial.print(" G = ");
  Serial.print(gf1);
  Serial.print(" B = ");
  Serial.print(bf1);
  Serial.print(" 2: R=");
  Serial.print(rf2);
  Serial.print(" G = ");
  Serial.print(gf2);
  Serial.print(" B = ");
  Serial.print(bf2);

  Serial.println();
}
