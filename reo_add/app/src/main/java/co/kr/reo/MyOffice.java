package co.kr.reo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyOffice implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;

    public MyOffice(double lat, double lng, String title){
        mPosition = new LatLng(lat,lng);
        mTitle=title;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return null;
    }
}
