/**
 * @author Gustavo Rodriguez Baldera
 * @link https://github.com/gbaldera/TiTouchGallery
 * */

 package com.gbaldera.titouchgallery;

import android.app.Activity;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;


@Kroll.proxy(creatableInModule=TitouchgalleryModule.class)
public class TouchGalleryProxy extends TiViewProxy {

    public TouchGalleryProxy()
    {
        super();
    }

    public TouchGalleryProxy(TiContext tiContext)
    {
        this();
    }

    @Override
    public TiUIView createView(Activity activity) {
        TouchGalleryView mainview = new TouchGalleryView(this);
        mainview.getLayoutParams().autoFillsHeight = true;
        mainview.getLayoutParams().autoFillsWidth = true;

        return mainview;
    }

    protected TouchGalleryView getView()
    {
        return (TouchGalleryView) getOrCreateView();
    }

    @Kroll.getProperty @Kroll.method
    public int getCurrentPage()
    {
        return getView().getCurrentPage();
    }

    @Kroll.setProperty @Kroll.method
    public void setCurrentPage(Object page)
    {
        getView().setCurrentPage(page);
    }

    @Kroll.method
    public void movePrevious()
    {
        getView().movePrevious();
    }

    @Kroll.method
    public void moveNext()
    {
        getView().moveNext();
    }

    @Kroll.getProperty @Kroll.method
    public Object getImages()
    {
        return getView().getImages().toArray();
    }

    @Kroll.setProperty @Kroll.method
    public void setImages(Object urls)
    {
        getView().setImages(urls);
    }

    @Kroll.method
    public void addImage(String url)
    {
        getView().addImage(url);
    }

    @Kroll.method
    public void removeImage(String url)
    {
        getView().removeImage(url);
    }

    @Kroll.method
    public int getCount()
    {
        return getView().getCount();
    }

    public void fireScroll(int currentPage, String currentURL)
    {
        if (hasListeners(TiC.EVENT_SCROLL)) {
            KrollDict options = new KrollDict();
            options.put("url", currentURL);
            options.put("currentPage", currentPage);
            fireEvent(TiC.EVENT_SCROLL, options);
        }
    }

    public void fireTouched(int currentPage, String currentURL)
    {
        if (hasListeners(TitouchgalleryModule.EVENT_VIEW_TOUCHED)) {
            KrollDict options = new KrollDict();
            options.put("url", currentURL);
            options.put("currentPage", currentPage);
            fireEvent(TitouchgalleryModule.EVENT_VIEW_TOUCHED, options);
        }
    }
}
