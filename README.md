# TiTouchGallery Module

## Description

TiTouchGallery is a native Android module, which allows you to create a gallery view, with zoom (pinch / double tap) and pan support, in your Titanium Android app.

##Copyright

This module is a fork of the android gallery widget from [Dreddik](https://github.com/Dreddik/AndroidTouchGallery) with some changes.
<br>Thanks Dreddik for share this.

##Download

Grab the lastest build from the [dist folder](https://github.com/gbaldera/TiTouchGallery/tree/develop/dist)

## Accessing the TiTouchGallery Module

Simply add the following lines to your `tiapp.xml` file:
    
    <modules>
        <module platform="android">com.gbaldera.titouchgallery</module> 
    </modules>

To access this module from JavaScript, you would do the following:

	var titouchgallery = require("com.gbaldera.titouchgallery");

The titouchgallery variable is a reference to the Module object.	

## Properties

####images : String[]

Array of images to display in the gallery (Only Remote images are supported for now)

####currentPage : Number

ndex of the active page

## Methods

List of methods supported in this module:

#### Gallery creation

	var proxy = titouchgallery.createTouchGallery({
        images: [
            "http://cs407831.userapi.com/v407831207/18f6/jBaVZFDhXRA.jpg",
            "http://cs407831.userapi.com/v4078f31207/18fe/4Tz8av5Hlvo.jpg",
            "http://cs407831.userapi.com/v407831207/1906/oxoP6URjFtA.jpg",
            "http://cs407831.userapi.com/v407831207/190e/2Sz9A774hUc.jpg",
            "http://cs407831.userapi.com/v407831207/1916/Ua52RjnKqjk.jpg",
            "http://cs407831.userapi.com/v407831207/191e/QEQE83Ok0lQ.jpg"
        ],
        currentPage:2
    });

#### getImages : String[]

Get the array with the images displayed in the gallery.

##### Arguments

None

#### setImages

Set the array with the images to be displayed in the gallery.

##### Arguments

* Images [array]: Images to be displayed in the gallery

#### addImage

Adds a new image to the gallery

##### Arguments

* Image [string]: Image URL to be added to the gallery

#### removeImage

Removes the specified image from the gallery

##### Arguments

* Image [string]: Image URL to be removed from the gallery

#### getCount : Number

Get the total number of images in the gallery.

##### Arguments

None

#### moveNext

Sets the current page to the next consecutive page in images.

##### Arguments

None

#### movePrevious

Sets the current page to the previous consecutive page in images.

##### Arguments

None

#### getCurrentPage : Number

Gets the value of the currentPage property.

##### Arguments

None

#### setCurrentPage

Sets the value of the currentPage property.

##### Arguments

* currentPage [number]: New value for the currentPage property


## Events

### singletap

Fired when the device detects a single tap against the view.

#### Properties

* currentPage : Index of the image.
* url : URL of the image.
* source : Source object that fired the event.
* type : String Name of the event fired.
* x : NumberX coordinate of the event from the source view's coordinate system.
* y : NumberY coordinate of the event from the source view's coordinate system.

### longpress

Fired when the device detects a long press against this view.

#### Properties

* currentPage : Index of the image.
* url : URL of the image.
* source : Source object that fired the event.
* type : String Name of the event fired.
* x : NumberX coordinate of the event from the source view's coordinate system.
* y : NumberY coordinate of the event from the source view's coordinate system.

### scroll

Fired when the gallery changed the page

#### Properties

* currentPage : Index of the image.
* url : URL of the image.


## Usage

```

var win = Ti.UI.createWindow({
    backgroundColor:'#000'
});

var titouchgallery = require('com.gbaldera.titouchgallery');

if (Ti.Platform.name == "android") {
    var proxy = titouchgallery.createTouchGallery({
        images: [
            "http://cs407831.userapi.com/v407831207/18f6/jBaVZFDhXRA.jpg",
            "http://cs407831.userapi.com/v4078f31207/18fe/4Tz8av5Hlvo.jpg",
            "http://cs407831.userapi.com/v407831207/1906/oxoP6URjFtA.jpg",
            "http://cs407831.userapi.com/v407831207/190e/2Sz9A774hUc.jpg",
            "http://cs407831.userapi.com/v407831207/1916/Ua52RjnKqjk.jpg",
            "http://cs407831.userapi.com/v407831207/191e/QEQE83Ok0lQ.jpg"
        ]
    });
    proxy.addEventListener("scroll", function(e){
        Ti.API.debug("Scroll event fired: " + JSON.stringify(e));
    });
    proxy.addEventListener("singletap", function(e){
        alert("Page: " + e.currentPage);
        Ti.API.debug("SingleTap event fired: " + JSON.stringify(e));
    });
    proxy.addEventListener("longpress", function(e){
        alert("Page: " + e.currentPage);
        Ti.API.debug("LongPress event fired: " + JSON.stringify(e));
    });

    win.add(proxy);
}

win.open();

```

## TODO

* Add support for local images.
* Cache remote images.

## Changelog
* 1.1:
  * Fixed single tap and added long press events
* 1.0: Initial version

## Author

Gustavo Rodriguez Baldera

[www.gbaldera.com](www.gbaldera.com) 

## License

It's open source and it's under [DBAD License](http://www.dbad-license.org/)
