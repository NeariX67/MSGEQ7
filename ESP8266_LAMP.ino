#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#define FASTLED_ALLOW_INTERRUPTS 0
#include <FastLED.h>

//PINS
const int DATA_PIN = 5;
int analogPin = 0;
int resetPin = 16;
int strobePin = 5;
//LEDs
#define NUM_LEDS 300
CRGB leds[NUM_LEDS];


//MSGEQ7
int filter = 95;
int spectrumValue[7];
int counter = 25;


//WiFi
const char* ssid = "SSID";
const char* password = "YOURPASSWORD";
unsigned int localUdpPort = 4210;
char incomingPacket[128];
WiFiUDP Udp;

//Time
unsigned long currtime;
unsigned long lasttime;

//modes
double fadebuff;
int mmode = 3;
int fade = 30;
int cycle = 30;
int sparkle = 30;
int brightness = 40;
int distance = 30;
boolean state = true;
boolean music = false;
String buffinc;
String buffinc2;
String buffred;
String buffgreen;
String buffblue;
int red;
int green;
int blue;
int diff;
uint8_t gHue = 0;
double ran1;
double ran2;

void setup() {
  delay(1000);
  Serial.begin(115200);
  Serial.println();
  pinMode(DATA_PIN, OUTPUT);
  pinMode(analogPin, INPUT);
  pinMode(strobePin, OUTPUT);
  pinMode(resetPin, OUTPUT);
  FastLED.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);
  FastLED.setDither(0);
  FastLED.setBrightness(brightness);
  leds[0].red = 255; //Status LED
  FastLED.show();
  Serial.printf("Connecting to %s ", ssid);
  WiFi.begin(ssid, password);
  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println(" Connected");

  Udp.begin(localUdpPort);
  Serial.printf("IP: %s, Port: %d \n", WiFi.localIP().toString().c_str(), localUdpPort);
  delay(2000);
}

void loop() {
  while(WiFi.status() != WL_CONNECTED) {
    leds[0].red = 255;
    FastLED.show();
  }
  //This reads the 63 Hz Frequency of the MSGEQ7
  if(mmode != 4 && music) {
    digitalWrite(resetPin, HIGH);
    digitalWrite(resetPin, LOW);
    digitalWrite(strobePin, LOW);
    delay(3);
    spectrumValue[0]=analogRead(analogPin);
    spectrumValue[0]=map(spectrumValue[0], filter, 1023, 0, NUM_LEDS);
    digitalWrite(strobePin, HIGH);
  }
  
  
  EVERY_N_SECONDS(1) {
    if(spectrumValue[0] < 5 && counter <= 31){
      counter++;
    }
    //Serial.println(FastLED.getFPS());
  }
  EVERY_N_SECONDS(5) { //Checks every 5 seconds if the music is running and turns on the music mode
    if(!music) {
      digitalWrite(resetPin, HIGH);
      digitalWrite(resetPin, LOW);
      digitalWrite(strobePin, LOW);
      delay(3);
      spectrumValue[0]=analogRead(analogPin);
      spectrumValue[0]=map(spectrumValue[0], filter, 1023, 0, NUM_LEDS);
      digitalWrite(strobePin, HIGH);
      if(spectrumValue[0] > NUM_LEDS*0.2) {
        music = true;
      }
    }
  }
  currtime = millis();
  if((currtime - lasttime) > fade) {
    gHue++;
    lasttime = millis();
  }

  //UDP
  int packetSize = Udp.parsePacket();
  if(packetSize) {
    int len = Udp.read(incomingPacket, 255);
    if(len > 0) {
      incomingPacket[len] = 0;
    }

    
    buffinc = String(incomingPacket);
    buffinc2 = buffinc;
    //Serial.println(buffinc);

    
    //Package starts with an Index (M/S/B....) to indicate the method i want to execute.
    //This is how i can control the Brightness etc with "B140" for example
    switch(buffinc.charAt(0)) {
      //Mode: "M0"
      case 'M':
        buffinc2.replace("M", "");
        mmode = buffinc2.toInt();
        fadebuff = (double) distance/200.0;
        break;
      //Fade: "F000"
      case 'F':
        buffinc2.replace("F", "");
        fade = buffinc2.toInt();
        break;
      //Rainbow effect: "D000"
      case 'D':
        buffinc2.replace("D", "");
        distance = buffinc2.toInt();
        fadebuff = (double) distance/200.0;
        break;
      //Brightness: "B000"
      case 'B':
        buffinc2.replace("B", "");
        brightness = buffinc2.toInt();
        FastLED.setBrightness(brightness);
        break;
      //Sound mode on/off: SON / SOFF
      //Or Sparkle Chance: "S000"
      case 'S':
        if(buffinc.charAt(1) == 'O') {
          if(buffinc.equals("SON")) {
            music = true;
          }
          else {
            music = false;
          }
        }
        //Sparkle
        else {
          buffinc2.replace("S", "");
          sparkle = buffinc2.toInt();
        }
        break;
      //On or Off: ON / OFF
      case 'O':
        if(buffinc.equals("ON")) {
          state = true;
        }
        else {
          state = false;
        }
        break;
        //Nothing else? -> Do RGB
      default:
        //Message: "RRR,GGG,BBB" -> "," is at 4th and 8th Pos.
        if(buffinc.charAt(3) == ',' && buffinc.charAt(7) == ',') {
          buffred = incomingPacket[0];
          buffred += incomingPacket[1];
          buffred += incomingPacket[2];
          buffgreen = incomingPacket[4];
          buffgreen += incomingPacket[5];
          buffgreen += incomingPacket[6];
          buffblue = incomingPacket[8];
          buffblue += incomingPacket[9];
          buffblue += incomingPacket[10];
          red = buffred.toInt();
          green = buffgreen.toInt();
          blue = buffblue.toInt();
        }
        //Something new added?
        else {
          Serial.print("ERROR: ");
          Serial.println(buffinc);
        }
     }
  }

  //Mode handling
  if(state) {
    switch(mmode) {
    //Fade
    case 1:
      if(!music) {
        for(int i = 0; i < NUM_LEDS;i++) {
          leds[i] = CHSV(gHue, 255, 255);
        }
      }
      else {
        fadeToBlackBy(leds, NUM_LEDS, 40);
        for(int i = 0; i < spectrumValue[0];i++) {
          leds[i] = CHSV(gHue, 255, 255);
        }
      }
      break;
    //Rainbow
    case 2:
      if(!music) {
        for(int i = 0; i < NUM_LEDS; i++) {
          leds[i] = CHSV(gHue + (i*fadebuff), 255, 255);
        }
      }
      else {
        fadeToBlackBy(leds, NUM_LEDS, 40);
        for(int i = 0; i < spectrumValue[0];i++) {
          leds[i] = CHSV(gHue + (i*fadebuff), 255, 255);
        }
      }
      break;
    //RGB Mode
    case 3:
      if(spectrumValue[0] > 5 and counter > 0) {
        counter--;
      }
      if(music) {
        fadeToBlackBy(leds, NUM_LEDS, 40);
        for(int i = 0; i < spectrumValue[0]; i++) {
          leds[i] = CRGB(red, green, blue);
        }
      }
      else {
        for(int i = 0; i < NUM_LEDS; i++) {
          leds[i] = CRGB(red, green, blue);
        }
      }
      //Serial.println(spectrumValue[0]);
      break;
    //Sparkle Mode
    case 4:
      for(int i = 0; i < NUM_LEDS;i++) {
        leds[i] = CRGB(red, green, blue);
      }
      ran1 = random(120);
      ran2 = random(120);
      if(ran1 < ran2) {
        diff = ran2 - ran1;
      }
      else {
        diff = ran1 - ran2;
      }
      if(diff > sparkle) {
        leds[random(NUM_LEDS)] = CRGB(255, 255, 255);
      }
    default:
      break;
    }
  }
  //If a wrong mode is detected -> do nothing
  else {
    for(int i = 0; i < NUM_LEDS;i++) {
      leds[i] = CRGB(0,0,0);
    }
  }
  FastLED.show();
  delay(3);
}
