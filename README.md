# AndroidCalendarLib

**AndroidCalendarLib is a tool that you can generate a calendar easily in your android app.**



![AndroidCalendarLib](https://raw.github.com/Ken-Yang/AndroidCalendarLib/master/screenshot.png "AndroidCalendarLib")


## Requirements
AndroidCalendarLib needs to depend on the following environment:
    
    1. Java 1.6 or above
    2. Eclipse
    3. Android SDK

## Deployment

### 1. Get repository
Please clone this lib to your workspace first.

    git clone https://github.com/Ken-Yang/AndroidCalendarLib.git
    
### 2. Setup
#### 2.1 Import to Eclipse
    
    File->Import->Existing Projects into Workspace
    
#### 2.2 Reference this lib to your project
    
    Right Click on your project -> Properties -> Android -> Add -> <<Select AndroidCalendarLib>> -> OK


## How to use?

### 1. Add calendar view element in your layout.xml
```XML
<net.kenyang.libcalendarview.CalendarView
    android:id="@+id/cvTest"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" >
</net.kenyang.libcalendarview.CalendarView>
```

### 2. Get the Object for generating a calendar
```Java
CalendarView cv = (CalendarView) findViewById(R.id.cvTest);
Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
cv.fnGenerate(calendar);
```

## License
Copyright 2013 Ken Yang
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.



