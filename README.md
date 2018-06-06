# UberLikeNavigation-Android
This is an open source Uber Like Navigation for easy navigation .


<h1>How to use this library :</h1>

<h2>Step #1 : Adding JitPack repository</h2>
Add it in your root build.gradle at the end of repositories:


```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
		}
	}
```
Step #1 : Adding Dependecy
-------

Add the dependency
```
dependencies {
compile 'com.github.smtrz:UberLikeNavigation-Android:0.1.1'

}
```
Follow the steps and then you would be able to get the benefit of using this library .


This is the initial release of this project and i will be adding more and more features to it soon.. 

For clarity you can view the sample android project.

<h2>Step #3 : Using inside your app</h2>
You just need to implement you Activity/Fragment with <b>"NaviInterfaces"</b>,and override method Get_LatLng which will return the list of all the point of interest .

For reference :
```
@Override
    public List<LatLng> Get_LatLng() {
        ArrayList<LatLng> newlist = new ArrayList<>();
        newlist.add(new LatLng(24.925911, 67.141012));
        newlist.add(new LatLng(24.925989, 67.140824));
        newlist.add(new LatLng(24.927434, 67.140516));
        newlist.add(new LatLng(24.928479, 67.140229));
        newlist.add(new LatLng(24.929853, 67.140364));
        newlist.add(new LatLng(24.930234, 67.139632));
        newlist.add(new LatLng(24.931441, 67.139416));


        return newlist;
    }
```
on the map onready event :
```
  mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
          NavigationUtils  nu = new NavigationUtils();
          nu.delay = 3000;
            nu.MapZoom = 15.5f;
                  nu.CreatePath(mMap, MainActivity.this);


                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng latLng : MainActivity.this.Get_LatLng()) {
                    builder.include(latLng);
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                mMap.animateCamera(mCameraUpdate);

                nu.StartAnimation(MainActivity.this.Get_LatLng(), mMap);


            }
        });
```
