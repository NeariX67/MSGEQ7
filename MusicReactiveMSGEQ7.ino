// This code is from Justin Kühner @ 16.11.2017
// I'm 16 years old. So don't be shy, if a 16 year old guy can do this, you can do this too. =)
// If u find your code here, it could even be your code. CTRL+C and CTRL+V was my friend.
// For any help, contact me at dergamer846@gmail.com
//
// This programm is developed to show bass (spectrumValue[0]). 
// If you wan't to change it, replace the 0 with any numbers from 0 to 6 or use "i" if u want to use all frequencies.


#include "FastLED.h"
int analogPin=0;
int strobePin=2;
int resetPin=3;
int spectrumValue[7];
int filter=87;

#define DATA_PIN 6
#define NUM_LEDS 296
#define BRIGHTNESS          150
#define COLOR_ORDER GRB
#define FRAMES_PER_SECOND   240
#define LED_TYPE    WS2812B
CRGB leds[NUM_LEDS];


void setup(){
  FastLED.addLeds<LED_TYPE,DATA_PIN,COLOR_ORDER>(leds, NUM_LEDS).setCorrection(TypicalLEDStrip);
  FastLED.setBrightness(BRIGHTNESS);
  pinMode(analogPin, INPUT);
  pinMode(strobePin, OUTPUT);
  pinMode(resetPin, OUTPUT);
  digitalWrite(resetPin, LOW);
  digitalWrite(strobePin, HIGH);
}

void loop() {
  digitalWrite(resetPin, HIGH);
  digitalWrite(resetPin, LOW);
  for (int i=0;i<7;i++){
    digitalWrite(strobePin, LOW);
    delay(5);
    spectrumValue[i]=analogRead(analogPin);
    spectrumValue[i]=constrain(spectrumValue[i], filter, 1023);
    spectrumValue[i]=map(spectrumValue[i], filter,1023,0,NUM_LEDS);
    digitalWrite(strobePin, HIGH);
  }  
  fadeToBlackBy( leds, NUM_LEDS, 50); // EDIT HOW FAST LEDS GO OFF
  for (int i = 0;i < spectrumValue[0]; i++) {
    if (spectrumValue[0] > 6 and spectrumValue[0] <= 119) {
      leds[i] = CRGB( 0, 0, 100); // EDIT COLORS HERE
    }
    if (spectrumValue[0] > 120 and spectrumValue[0] <= 199) {
      leds[i] = CRGB( 0, 50, 100); // EDIT COLORS HERE
    }
    if (spectrumValue[0] > 200) {
      leds[i] = CRGB( 0, 100, 100); // EDIT COLORS HERE
    }
  }
  FastLED.show();
}
  

