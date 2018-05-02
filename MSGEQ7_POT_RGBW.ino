-// This code is from Justin Kühner @ 16.11.2017
-// I'm 16 years old. So don't be shy, if a 16 year old guy can do this, you can do this too. =)
-// If u find your code here, it could even be your code. CTRL+C and CTRL+V was my friend.
-// For any help, contact me at dergamer846@gmail.com


#include "FastLED.h"

int analogPin = 0,
    strobePin = 2,
    resetPin  = 3,
    filter    = 87,
    potPin    = 1;
    
int spectrumValue[7];
int PotMeter = 0;
int counter = 25;

#define DATA_PIN 6
#define NUM_LEDS 296
#define BRIGHTNESS 70
#define COLOR_ORDER GRB
#define FRAMES_PER_SECOND 120
#define LED_TYPE    WS2812B
CRGB leds[NUM_LEDS];


void setup(){
  FastLED.addLeds<LED_TYPE,DATA_PIN,COLOR_ORDER>(leds, NUM_LEDS).setCorrection(TypicalLEDStrip);
  FastLED.setBrightness(BRIGHTNESS);
  pinMode(analogPin, INPUT);
  pinMode(strobePin, OUTPUT);
  pinMode(resetPin, OUTPUT);
  pinMode(potPin, INPUT);
  digitalWrite(resetPin, LOW);
  digitalWrite(strobePin, HIGH);
  Serial.begin(9600);
}
uint8_t gHue = 0;

void loop() {
  digitalWrite(resetPin, HIGH);
  digitalWrite(resetPin, LOW);
  digitalWrite(strobePin, LOW);
  delay(5);
  spectrumValue[0]=analogRead(analogPin);
  spectrumValue[0]=map(spectrumValue[0], filter,1023,0,296);
  digitalWrite(strobePin, HIGH);
  delay(5);  
  Serial.println(spectrumValue[0]);
  PotMeter = (analogRead(potPin) / 3.75);
  gHue = PotMeter;
  if (spectrumValue[0] < 5 and counter <= 29) {
    EVERY_N_SECONDS(1) {
      counter++;      
    }
  }
  if (spectrumValue[0] > 5 and counter > 0) {
    counter--;
  } 
  if(counter > 29 and PotMeter > 265) {
    fullWhite();
  }
  if(counter > 29 and PotMeter <= 264) {
    fullRGB();
  }  
  if(counter <= 29 and PotMeter > 265) {
    BassWhite();
  }  
  if(counter <= 29 and PotMeter <= 264) {
    Bass();
  }  
  FastLED.show();
}
void fullWhite() {
  for(int i = 0; i < NUM_LEDS;i++) {
    leds[i] = CRGB(60, 85, 60);
  }
  delay(100);
}

void fullRGB() {
  for(int i = 0; i < NUM_LEDS;i++) {
    leds[i] = CHSV( gHue, 255, 160);
  }
  delay(25);
}

void Bass() {
  delay(5);
  for (int i = 0;i < spectrumValue[0]; i++) {
    leds[i] = CHSV( gHue, 255, 200);
  }
  fadeToBlackBy( leds, NUM_LEDS, 50);
}

void BassWhite() {
  delay(5);
  for (int i = 0;i < spectrumValue[0]; i++) {
    leds[i] = CRGB(60, 85, 60);
  }
  fadeToBlackBy( leds, NUM_LEDS, 50);
}
