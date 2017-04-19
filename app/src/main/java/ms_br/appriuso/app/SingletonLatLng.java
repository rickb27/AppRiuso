package ms_br.appriuso.app;


import com.google.android.gms.maps.model.LatLng;

public class SingletonLatLng {

    private static SingletonLatLng mInstance = null;

    private LatLng latLng;

    private SingletonLatLng(){latLng = new LatLng(0,0);}

    public static SingletonLatLng getInstance(){
        if(mInstance == null)
        {
            mInstance = new SingletonLatLng();
        }
        return mInstance;
    }

    public LatLng getLatLng(){
        return this.latLng;
    }

    public void setLatLng(LatLng value){
        latLng = value;
    }
}