Android-Google-Places-API
=========================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android--Google--Places--API-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4463)


This project allows you get places in a given radius and other Places API parameter using Google Places API. 


<img src="http://i67.tinypic.com/imj6vc.png" width="200">

Sample
------------

The sample makes use of the Google Places API for Android in order to provide a real life example of how the library can be used. [Dropbox Link](https://www.dropbox.com/s/47eq14a5tj2vqmq/Places-Demo.apk?dl=0)

Download
--------


Grab via Maven:
```xml
<dependency>
  <groupId>noman.placesapi</groupId>
  <artifactId>placesAPI</artifactId>
  <version>1.0.0</version>
</dependency>
```
or Gradle:
```groovy
    compile 'noman.placesapi:placesAPI:1.1.3'
```

Usage
-----

To get places within a radius, pass the central point , API key and just execute the NRPlaces Object.


*You can execute the task in this manner. ( See the example for more details on the exact implementation)



``` java

       new NRPlaces.Builder()
                .listener(this)
                .key("KEY")
                .latlng(33.721328, 73.057838)
                .radius(500)
                .type(PlaceType.GYM)
                .build()
                .execute();
        
```
Callbacks

``` java

    void onPlacesFailure(PlacesException e);

    void onPlacesStart();

    void onPlacesSuccess(List<Place> places);

    void onPlacesFinished();
```

License
--------

    Copyright 2016 Noman Rafique

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.








