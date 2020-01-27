#include <Servo.h>

// colour sensor
int S0[3] = {53, 52, 23};
int S1[3] = {51, 50, 25};
int S2[3] = {49, 48, 27};
int S3[3] = {47, 46, 29};
int Sout[3] = {45, 44, 31};

int rotorPins[3] = {37, 36, 30};
int finPins[3] = {6, 5, 7};

Servo r[3];
Servo f[3];

int red[3] = {};
int green[3] = {};
int blue[3] = {};
int angles[3] = {};

int corrections[3] = {};

//String line;

void setup() {

  for (int i = 0; i < 3; i++) {
    // colour sensor pins
    pinMode(S0[i], OUTPUT);
    pinMode(S1[i], OUTPUT);
    pinMode(S2[i], OUTPUT);
    pinMode(S3[i], OUTPUT);
    // PWM colour sensor pins
    pinMode(Sout[i], INPUT);

    // rotor servo motors
    r[i].attach(rotorPins[i]);

    // fin servo motor (PWM)
    f[i].attach(finPins[i]);

    // set frequency scaling to 20% for Arduino
    digitalWrite(S0[i], HIGH);
    digitalWrite(S1[i], LOW);
  }

  Serial.begin(9600);

}

void loop() {
  r[0].write(50);
  r[1].write(50);
  r[2].write(40);
  // get inputs of colours
  setColour('r');
  delay(50);
  setColour('g');
  delay(50);
  setColour('b');
  delay(50);

  // generate angles
  generateAngles();
  correct();
  writeOut();

  // get inputs
  char c = 'a';
  while (Serial.available() > 0) {
    c = Serial.read();
    if (c == 'T') testFins();
  }
  //    if (Serial.available()>0){
  //        c = Serial.read();
  //    }
  //    if (c == 'Y') {
  //        testFins();
  //    }

}

void setColour(char colour) {
  int s2 = 0, s3 = 0;

  if (colour == 'b') {
    s3 = 1;
  }
  else if (colour == 'g') {
    s2 = 1;
  }

  for (int i = 0; i < 3; i++) {
    digitalWrite(S2[i], s2);
    digitalWrite(S3[i], s3);
    if (colour == 'r') {
      red[i] = pulseIn(Sout[i], 0);
    }
    else if (colour == 'g') {
      green[i] = pulseIn(Sout[i], 0);
    }
    else {
      blue[i] = pulseIn(Sout[i], 0);
    }
  }
}

// generates proper angles, creates proper corrections
void generateAngles() {

  // if == 1
  // this correction
  int eps = 3;

  //  for (int i = 0; i < 3; i++) {
  //
  //  }

  if (abs(0.461 * red[0] + 16.27 - blue[0]) < eps && abs(0.769 * red[0] + 16.0519 - green[0]) < eps) {
    angles[0] = (int) (4.453 * red[0] - 189.897 + 0.5 + 180);
  }
  else {
    angles[0] = (int) (2.177 * green[0] - 114.083 + 0.5);
  }
  if (abs(0.449 * red[0] + 16.158 - blue[1]) < eps && abs(0.659 * red[1] + 16.0519 - green[1]) < eps) {
    angles[1] = (int) (4.196 * red[1] - 145.604 +  0.5 + 180);
  }
  else {
    angles[1] = (int) (2.467 * green[1] - 131.688 + 0.5);
  }
  if (abs(1.32 * red[0] + 10.688 - blue[2]) < eps && abs(0.750 * red[2] + 20.588 - green[2]) < eps) {
    angles[2] = (int) (3.101 * red[0] - 117.669 + 0.5);
  }
  else {
    angles[2] = (int) (3.68 * green[2] - 187 + 180 + 0.5);
  }

  for (int i = 0; i < 3; i++) {
    angles[i] %= 360;
    if (angles[i] <= 90) corrections[i] = angles[i] + 90;
    else if (angles[i] <= 180) corrections[i] = angles[i] - 90;
    else if (angles[i] <= 270) corrections[i] = angles[i] - 90;
    else if (angles[i] <= 360) corrections[i] = angles[i] - 270;
  }
}

// write output to Serial
void writeOut() {
  // 1 2 3     5 6 7      9 10 11
  // J0,R001,G023,B321,J1,R001,G023,B321,J2,R001,G023,B321,e
  for (int i = 0; i < 3; i++) {

    Serial.print("J");
    Serial.print(i);
    // red
    if (red [i] < 10) {
      Serial.print(",R00");
    }
    else if (red [i] < 100) {
      Serial.print(",R0");
    }
    else {
      Serial.print(",R");
    }
    Serial.print(red[i]);

    //green
    if (green[i] < 10) {
      Serial.print(",G00");
    }
    else if (green[i] < 100) {
      Serial.print(",G0");
    }
    else {
      Serial.print(",G");
    }
    Serial.print(green[i]);

    // blue
    if (blue[i] < 10) {
      Serial.print(",B00");
    }
    else if (blue[i] < 100) {
      Serial.print(",B0");
    }
    else {
      Serial.print(",B");
    }
    Serial.print(green[i]);
    Serial.print(",");
  }

  Serial.print("e");
  Serial.println();
  //    Serial.println("G1,R" + red[0]+);
}

// apply corrections to the fins
void correct() {
  for (int i = 0; i < 3; i++) {
    f[i].write(corrections[i]);
  }
}

void testFins() {
  // go in one direction
  for (int i = 0; i < 3; i++) {

    for (int j = 0; j < 180; j++) {
      f[i].write(j);
      Serial.print("FinT,");
      Serial.print(i);

      if (j < 10) {
        Serial.print(",00");
      }
      else if (j < 100) {
        Serial.print(",0");
      }
      else {
        Serial.print(",");
      }
      Serial.print(j);
      Serial.print(",");
      delay(50);
    }
  }
  Serial.print("f");
  Serial.println();

  // go in other direction
  for (int i = 0; i < 3; i++) {
    for (int j = 180; j >= 0; j--) {
      f[i].write(j);
      Serial.print("FinT,");
      Serial.print(i);

      if (j < 10) {
        Serial.print(",00");
      }
      else if (j < 100) {
        Serial.print(",0");
      }
      else {
        Serial.print(",");
      }
      Serial.print(j);
      Serial.print(",");
      delay(50);
    }
  }
  Serial.print("f");
  Serial.println();

}
