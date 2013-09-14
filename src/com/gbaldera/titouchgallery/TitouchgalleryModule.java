/**
 * @author Gustavo Rodriguez Baldera
 * @link https://github.com/gbaldera/TiTouchGallery
 * */

 package com.gbaldera.titouchgallery;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;

@Kroll.module(name="Titouchgallery", id="com.gbaldera.titouchgallery")
public class TitouchgalleryModule extends KrollModule
{

	// Standard Debugging variables
	private static final String TAG = "TitouchgalleryModule";
    @Kroll.constant public static final String EVENT_VIEW_TOUCHED = "viewtouched";

	public TitouchgalleryModule()
	{
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app)
	{
		Log.d(TAG, "inside onAppCreate");
		// put module init code that needs to run when the application is created
	}

}

