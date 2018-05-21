char incomingByte;
float sign, capacity, x, y;
byte right, left;
boolean minus= false,direct = false, isSecond = false;
 
#define directionR 4
#define directionL 7
#define velocityR 5
#define velocityL 6 

void setup() {
  Serial.begin(9600); // инициализация порта
  for(int i = 4; i < 8; i++)     
        pinMode(i, OUTPUT);  
}
 
void loop() {
  if(Serial.available()){
    incomingByte =Serial.read();
    if(incomingByte=='/'){
      if(!isSecond){
        x = sign;
        if(minus){x=x*(-1);}
        sign = 0;
        minus = false;
        isSecond = true;
      }
      else{
        y = sign;
        sign = 0; 
        if(minus){
          y=y*(-1);
          }
        capacity = sqrt(sq(x)+sq(y))/100;
        if(y<0){direct=true;}
        else{direct=false;}
        if(x>=0){
          right = 255*((100-x)/100)*capacity;
          left = 255 * capacity;
        }
        else{
          left = 255*((100+x)/100)*capacity;
          right = 255 * capacity;
        }
        if(!direct){
          digitalWrite(directionL,HIGH);
          digitalWrite(directionR,LOW);
        }
        else{
          digitalWrite(directionR,HIGH);
          digitalWrite(directionL,LOW);
        }
        analogWrite(velocityL,left);
        analogWrite(velocityR,right);
        isSecond = false;
        minus = false;
      }
    } 
    else{
      if(incomingByte=='-'){
        minus = true;
      }
      else if(incomingByte==','){
        while(!Serial.available()){
          continue;
        }
        incomingByte = Serial.read();
        sign = sign + 0,1*(incomingByte-'0');
      }
      else{
        sign = sign * 10 + (incomingByte-'0');
      }
    }
  }
}
