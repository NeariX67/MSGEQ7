#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#define FASTLED_ESP8266_RAW_PIN_ORDER
#define FASTLED_ALLOW_INTERRUPTS 0
#include <FastLED.h>

//PINS
const int DATA_PIN = 14;

//LEDs
#define NUM_LEDS 170
CRGB leds[NUM_LEDS];


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
String buffinc;
String buffinc2;
String buffred;
String buffgreen;
String buffblue;
int red;
int green;
int blue;
uint8_t gHue = 0;

void setup() {
  delay(1000);
  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);
  Serial.begin(115200);
  Serial.println();
  FastLED.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);
  FastLED.setDither(0);
  FastLED.setBrightness(brightness);
  Serial.printf("Connecting to %s ", ssid);
  WiFi.begin(ssid, password);
  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println(" Connected");

  Udp.begin(localUdpPort);
  Serial.printf("IP: %s, Port: %d", WiFi.localIP().toString().c_str(), localUdpPort);
  
  delay(2000);
}

void loop() {
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
    Serial.println(buffinc);
    
    switch(buffinc.charAt(0)) {
      case 'M':
        buffinc2.replace("M", "");
        mmode = buffinc2.toInt();
        fadebuff = (double) distance/200.0;
        break;
      case 'F':
        buffinc2.replace("F", "");
        fade = buffinc2.toInt();
        break;
      case 'D':
        buffinc2.replace("D", "");
        distance = buffinc2.toInt();
        fadebuff = (double) distance/200.0;
        break;
      case 'B':
        buffinc2.replace("B", "");
        brightness = buffinc2.toInt();
        FastLED.setBrightness(brightness);
        break;
      case 'O':
        if(buffinc.equals("ON")) {
          state = true;
        }
        else {
          state = false;
        }
        break;
      default:
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
        else {
          Serial.print("ERROR: ");
          Serial.println(buffinc);
        }
     }
  }

  //Mode handling
  if(state) {
    switch(mmode) {
    case 1:
      for(int i = 0; i < NUM_LEDS;i++) {
        leds[i] = CHSV(gHue, 255, 255);
      }
      break;
    case 2:
      for(int i = 0; i < NUM_LEDS;i++) {
        leds[i] = CHSV(gHue + (i*0.7), 255, 255);
      }
      break;
    case 3:
      for(int i = 0; i < NUM_LEDS;i++) {
        leds[i] = CRGB(red, green, blue);
      }
      break;
    case 4:
      for(int i = 0; i < NUM_LEDS;i++) {
        leds[i] = CRGB(red, green, blue);
      }
    default:
      break;
    }
  }
  else {
    for(int i = 0; i < NUM_LEDS;i++) {
      leds[i] = CRGB(0,0,0);
    }
  }
  
  FastLED.show();
}
