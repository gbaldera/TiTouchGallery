// This is a test harness for your module
// You should do something interesting in this harness 
// to test out the module and to provide instructions 
// to users on how to use it by example.


// open a single window
var win = Ti.UI.createWindow({
    backgroundColor:'#000'
});

var titouchgallery = require('com.gbaldera.titouchgallery');
Ti.API.info("module is => " + JSON.stringify(titouchgallery));

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
    proxy.addEventListener(titouchgallery.EVENT_VIEW_TOUCHED, function(e){
        alert("Page: " + e.currentPage);
        Ti.API.debug(titouchgallery.EVENT_VIEW_TOUCHED + " event fired: " + JSON.stringify(e));
    });

    win.add(proxy);
}

win.open();

