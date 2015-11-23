/**
 * @author Gustavo Rodriguez Baldera
 * @link https://github.com/gbaldera/TiTouchGallery
 * */

 package com.gbaldera.titouchgallery;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiUIView;
import ru.truba.touchgallery.GalleryWidget.BasePagerAdapter;
import ru.truba.touchgallery.GalleryWidget.GalleryViewPager;
import ru.truba.touchgallery.GalleryWidget.UrlPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class TouchGalleryView extends TiUIView {

    private static final String TAG = "TouchGallery";

    private TouchGalleryProxy mProxy;
    private final LinearLayout mContainer;
    private final GestureDetector mGestureDetector;
    private UrlPagerAdapter mAdapter;
    private GalleryViewPager mPager;
    private final List<String> images;

    private int mCurIndex;

    public TouchGalleryView(TiViewProxy proxy) {
        super(proxy);
        mProxy = (TouchGalleryProxy) proxy;

        mGestureDetector = new GestureDetector(proxy.getActivity(), new GestureListener());
        mContainer = (new LinearLayout(proxy.getActivity())
        {
            @Override
            public boolean onTouchEvent(MotionEvent event)
            {
                return mGestureDetector.onTouchEvent(event);
            }

            @Override
            public boolean onInterceptTouchEvent(MotionEvent event)
            {
                return mGestureDetector.onTouchEvent(event);
            }
        });

        images = new ArrayList<String>();

        ViewPager.LayoutParams viewPagerLayoutParams = new ViewPager.LayoutParams();
        viewPagerLayoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
        viewPagerLayoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;

        mAdapter = new UrlPagerAdapter(mContainer.getContext(), images);
        mPager = buildPager(mContainer.getContext(), mAdapter);
        mCurIndex = mAdapter.getCurrentPosition();

        mContainer.addView(mPager, viewPagerLayoutParams);

        setNativeView(mContainer);
    }

    protected GalleryViewPager buildPager(Context context, UrlPagerAdapter adapter)
    {
        GalleryViewPager pager = new GalleryViewPager(context);
        pager.setOffscreenPageLimit(3);

        adapter.setOnItemChangeListener(new BasePagerAdapter.OnItemChangeListener() {
            public void onItemChange(int currentPosition) {
                mCurIndex = currentPosition;
                mProxy.fireScroll(currentPosition, images.get(currentPosition));
                Log.d(TAG, "Current item is " + currentPosition);
            }
        });
        pager.setAdapter(adapter);

        return pager;
    }

    @Override
    public void setProxy(TiViewProxy proxy)
    {
        super.setProxy(proxy);
        mProxy = (TouchGalleryProxy) proxy;
    }

    @Override
    public void processProperties(KrollDict d) {
        if (d.containsKey(TiC.PROPERTY_IMAGES)) {
            setImages(d.get(TiC.PROPERTY_IMAGES));
        }

        if (d.containsKey(TiC.PROPERTY_CURRENT_PAGE)) {
            int page = TiConvert.toInt(d, TiC.PROPERTY_CURRENT_PAGE);
            if (page > 0) {
                setCurrentPage(page);
            }
        }

        super.processProperties(d);
    }

    @Override
    public void propertyChanged(String key, Object oldValue, Object newValue,
                                KrollProxy proxy)
    {
        if (TiC.PROPERTY_CURRENT_PAGE.equals(key)) {
            setCurrentPage(TiConvert.toInt(newValue));
        } else {
            super.propertyChanged(key, oldValue, newValue, proxy);
        }
    }

    public void setImages(Object images) {

        boolean changed = false;
        this.images.clear();

        if(images instanceof Object[])
        {
            Object[] urls = (Object[])images;

            for (Object url1 : urls) {
                if (url1 instanceof String) {
                    String url = (String) url1;
                    this.images.add(url);
                    changed = true;
                }
            }
        }

        if(changed)
        {
            mAdapter.notifyDataSetChanged();
        }
    }

    public List<String> getImages()
    {
        return images;
    }

    public void addImage(String url)
    {
        if (!images.contains(url)) {
            images.add(url);
            getProxy().setProperty(TiC.PROPERTY_IMAGES, images.toArray());
            mAdapter.notifyDataSetChanged();
        }
    }

    public void removeImage(String url)
    {
        if (images.contains(url)) {
            images.remove(url);
            getProxy().setProperty(TiC.PROPERTY_IMAGES, images.toArray());
            mAdapter.notifyDataSetChanged();
        }
    }

    public int getCurrentPage()
    {
        return mCurIndex;
    }

    public void setCurrentPage(Object view)
    {
        scrollTo(view);
    }

    public void moveNext()
    {
        move(mCurIndex + 1);
    }

    public void movePrevious()
    {
        move(mCurIndex - 1);
    }

    private void move(int index)
    {
        if (index < 0 || index >= images.size()) {
            Log.w(TAG, "Request to move to index " + index + " ignored, as it is out-of-bounds.");
            return;
        }
        mCurIndex = index;
        mPager.setCurrentItem(index);
    }

    public void scrollTo(Object view)
    {
        if (view instanceof Number) {
            move(((Number) view).intValue());
        }
    }

    public int getCount()
    {
        return mAdapter.getCount();
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        private KrollDict getEventData(MotionEvent event)
        {
            int index = getCurrentPage();

            KrollDict data = dictFromEvent(event);
            data.put("url", index);
            data.put("currentPage", images.get(index));

            return data;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            Log.d(TAG, "TAP on " + mProxy);

            if (mProxy.hasListeners(TiC.EVENT_SINGLE_TAP)) {
                mProxy.fireEvent(TiC.EVENT_SINGLE_TAP, getEventData(event));
            }

            return true;
        }

        @Override
        public void onLongPress(MotionEvent event)
        {
            Log.d(TAG, "LONGPRESS on " + mProxy);

            if (mProxy.hasListeners(TiC.EVENT_LONGPRESS)) {
                mProxy.fireEvent(TiC.EVENT_LONGPRESS, getEventData(event));
            }
        }

        @Override
        public boolean onDown(MotionEvent event)
        {
            return false;
        }
    }
}
