## Requirements
This App was developerd by using [Android studio](https://developer.android.com/studio) with [Java](https://www.java.com/en/) language. To run this App, an Andorid phone with [Android Version](https://www.android.com/) > 5.0 is needed (we recommend Android 10). 

## Using guide
>Before installing this App, please make sure the [Java](https://www.java.com/en/) environment and [Android studio](https://developer.android.com/studio) have been installed on your computer, and you have switched on the developer mode on your Android smartphone ([Android Version](https://www.android.com/) __> 5.0__).

The App is the core of this IoT diagnostic platform, it has three functions:
* Control the mobile heater
* Communicate with the Blockchain 
* Running the Deeplearning model(CNN)


Because the server used in previoud study was turned off, we did't provide an __.apk__ installation package. You may need to run your own server and slightly modify the code. 

Once you have your server running you need to chnage some urls in the source code.  
* In ``` /Assay/app/src/main/java/FetchData.java ``` file line 32 you will find: 
```Java
Request request = new Resquest.Builder()
    .url("http://xx.xx.xx")+LoginActivity.port+"/api/"+urlPath)
    .build();
```
Just simply change the url to your server address, __e.g__. 
```java
Request request = new Resquest.Builder()
    ..url("http://localhost:3000")+LoginActivity.port+"/api/"+urlPath)
    .build();
```

* In ``` /Assay/app/src/main/java/DevInfoManufacturerActivity.java ``` file line 133 change the url to your server address,
  __e.g__.

From
```Java
Request request = new Resquest.Builder()
    .url("http://xx.xx.xx")+LoginActivity.port+"/api/ProduceDevice")
    .build();
```
To
```java
Request request = new Resquest.Builder()
    ..url("http://localhost:3000")+LoginActivity.port+"/api//api/ProduceDevice")
    .build();
```

And you need to change the urls in  ``` /Assay/app/src/main/java/LoginActicaty.java ``` line 22 and  ``` /Assay/app/src/main/java/PatientInfoActivity.java ``` line 200 to your blockchain rest server address in the same way.

>An firebase storage was used for saving the images during the test, if you want to use your own bucket you will need to modify the ```CustomCamActivity.java``` and ```PatientInfoActivity.java```. Details please see [Firebase documentation](https://firebase.google.com/docs/storage/android/start).

