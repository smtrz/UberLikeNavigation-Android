package com.highbryds.navigation_uber;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.highbryds.navigation_uber.Interfaces.NaviInterfaces;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class NavigationUtils {
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private Polyline blackPolyline, greyPolyLine;
    private Runnable r;
    private int next, index;
    private Handler handler;
    private float v;
    private double lat, lng;
    private LatLng startPosition, endPosition;
    ValueAnimator valueAnimator;
    Marker marker;
    public  long delay = 3000;
    public  float MapZoom = 15.5f;
// the way you can get the list of latitude and longitudes.


    // to obtain the bearing between starting and ending point
    private final float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

    //calling the animate method on your google map object.
    public void CreatePath(final GoogleMap googleMap, final NaviInterfaces ni) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : ni.Get_LatLng()) {
            builder.include(latLng);
        }

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.GRAY);
        polylineOptions.width(5);
        polylineOptions.startCap(new SquareCap());
        polylineOptions.endCap(new SquareCap());
        polylineOptions.jointType(ROUND);
        polylineOptions.addAll(ni.Get_LatLng());
        greyPolyLine = googleMap.addPolyline(polylineOptions);

        blackPolylineOptions = new PolylineOptions();
        blackPolylineOptions.width(5);
        blackPolylineOptions.color(Color.BLACK);
        blackPolylineOptions.startCap(new SquareCap());
        blackPolylineOptions.endCap(new SquareCap());
        blackPolylineOptions.jointType(ROUND);
        blackPolyline = googleMap.addPolyline(blackPolylineOptions);
        //destination

        ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
        polylineAnimator.setDuration(2000);
        polylineAnimator.setInterpolator(new LinearInterpolator());
        polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                List<LatLng> points = greyPolyLine.getPoints();
                int percentValue = (int) valueAnimator.getAnimatedValue();
                int size = points.size();
                int newPoints = (int) (size * (percentValue / 100.0f));
                List<LatLng> p = points.subList(0, newPoints);
                blackPolyline.setPoints(p);
            }
        });
        polylineAnimator.start();
        marker = googleMap.addMarker(new MarkerOptions().position(ni.Get_LatLng().get(0))
                .flat(true)

                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));


    }

    //Start Animation
    public void StartAnimation(final List<LatLng> polyLineList, final GoogleMap googleMap) {


        handler = new Handler();
        index = -1;
        next = 1;
        r = new Runnable() {
            @Override
            public void run() {
                if (index < polyLineList.size() - 2) {
                    if (index < polyLineList.size() - 1) {
                        index++;
                        next = index + 1;
                    }
                    if (index < polyLineList.size() - 1) {
                        startPosition = polyLineList.get(index);
                        endPosition = polyLineList.get(next);
                    }
                    valueAnimator = ValueAnimator.ofFloat(0, 1);
                    valueAnimator.setDuration(delay);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            v = valueAnimator.getAnimatedFraction();
                            lng = v * endPosition.longitude + (1 - v)
                                    * startPosition.longitude;
                            lat = v * endPosition.latitude + (1 - v)
                                    * startPosition.latitude;
                            LatLng newPos = new LatLng(lat, lng);
                            marker.setPosition(newPos);
                            marker.setAnchor(0.5f, 0.5f);
                            marker.setRotation(getBearing(startPosition, newPos));
                            googleMap.moveCamera(CameraUpdateFactory
                                    .newCameraPosition
                                            (new CameraPosition.Builder()
                                                    .target(newPos)
                                                    .zoom(MapZoom)
                                                    .build()));
                        }
                    });
                    valueAnimator.start();
                    handler.postDelayed(r, delay);


                } else {
                    handler.removeCallbacks(r);
                    valueAnimator.removeAllUpdateListeners();
                    valueAnimator.end();
                    marker.remove();


                }
            }
        };
        handler.postDelayed(r, delay);

    }


    //stop animation
    public void StopAnimation() {

        try {
            valueAnimator.end();
            valueAnimator.removeAllUpdateListeners();
            handler.removeCallbacks(r);
        } catch (NullPointerException e) {

        }
    }


}

