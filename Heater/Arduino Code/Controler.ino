#include <SPI.h>
#include "TimerOne.h"
const int AnalogPin = A1;   //Analog pin for temp read
//const int AnalogPin2 = A2;  //Analog pin for reading referent value

//Pins
int PWM_pin = 3;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             ;

float Temp;                 //Temperatur
float Vref;                 //Referent voltage
float Vout;                 //Voltage after adc
float SenVal;               //Sensor value
float SenVal2;              //Sensor value from referent pin


//Variables
float temperature_read = 0.0;
float set_temperature = 65 ;
float PID_error = 0;
float previous_error = 0;
float elapsedTime, Time, timePrev;
int PID_value = 0;

int a = 0;

int minute = 10;
int second = 10;
int hour = 5;
//PID constants
int kp = 14;   int ki = 1.2;   int kd = 1.7;
int PID_p = 0;    int PID_i = 0;    int PID_d = 0;
int en = 0;
String comdata = "";
int numdata[5] = {0};
int mark = 0;

void setup()
{ //pinMode(PWM_pin, OUTPUT);
  Timer1.initialize(1000000);//初始化定时器为1s
Timer1.attachInterrupt( timerIsr );
  Serial.begin(115200);
}

void loop() {


  Serial.print(hour);
  Serial.print("h ");
  Serial.print(minute);
  Serial.print("min ");
  Serial.print(second);
  Serial.println("s  ");

  int j = 0;

  while (Serial.available() > 0)
  {
    comdata += char(Serial.read());
    delay(2);
    mark = 1;
  }

  if (mark == 1) //如果接收到数据则执行comdata分析操作，否则什么都不做。
  {
    Serial.println(comdata);
    for (int i = 0; i < comdata.length() - 1 ; i++)
    {

      if (comdata[i] == ',')
      {
        j++;
      }
      else
      {

        numdata[j] = numdata[j] * 10 + (comdata[i] - '0' );
      }
    }

    comdata = String("");


    for (int i = 0; i < 5; i++)
    {
      Serial.println(numdata[i]);

      numdata[i] = {0};

    }
    mark = 0;
  }
  en = numdata[0];

  Serial.println(en);
  //set_temperature= numdata[1]; //set temperature
  /*hour=numdata[2];
    minute=numdata[3];
    second=numdata[4];*/
  Serial.println(comdata);
  delay(300);
  SenVal = analogRead(A1);    //Analog value from temperature
  //SenVal2 = analogRead(A1);   //Analog value from refferent pin
  SenVal2 = 1.291;


  // Vref = (SenVal2 *5.0) / 1024.0;   //Conversion analog to digital for referent value
  Vout = (SenVal * 5.0) / 1024.0;   //Conversion analog to digital for the temperature read voltage

  Temp = (Vout - 1.291) / 0.005;   //Temperature calculation

  Serial.print("Temperature= ");
  Serial.print(Temp);

  //Serial.print(set_temperature);

  //PID control
  //Calculate the error between setpoint and real value
  PID_error = set_temperature - Temp;
  //Calculate the P value
  PID_p = kp * PID_error;
  //Calculate i value in a range on+-3
  if (-2 < PID_error < 2)
  {
    PID_i = PID_i + (ki * PID_error);
  }

  //For derivative we need real time to calculate speed change rate
  timePrev = Time;                            // the previous time is stored before the actual time read
  Time = millis();                            // actual time read
  elapsedTime = (Time - timePrev) / 1000;


  //Now we can calculate the D calue
  PID_d = kd * ((PID_error - previous_error) / elapsedTime);
  //Final total PID value is the sum of P + I + D
  PID_value = PID_p + PID_i + PID_d;

  //We define P WM range between 0 and 255
  if (PID_value < 0)
  {
    PID_value = 0;
  }
  if (PID_value > 255)
  {
    PID_value = 255;
  }
  //Now we can write the PWM signal to the mosfet on digital pin D3
  if (en == 1)
  {
    analogWrite(PWM_pin, PID_value);

    // Serial.println(PID_value);
    previous_error = PID_error;     //Remember to store the previous error for next loop.
    //delay(149.8);
    // }
    else
    {
      analogWrite(PWM_pin, 0);
    }
  }

  void timerIsr()//timer
  {
    second--;
    if (second == -1)
    {
      if (minute > 0)
      {
        second = 59;
        minute--;
      }
      else
      {
        if (hour > 0)
        {
          minute = 59;
          hour--;
          second = 59;
        }
        else
        {
          second = 0;
        }
      }
    }
  }

  //void sending()//send data to mobile
