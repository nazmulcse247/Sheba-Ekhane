package com.example.shebaekhane;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class UserLocationFragment extends Fragment {
    //private Context context;
    private Button nextBtn;
    private FusedLocationProviderClient providerClient;
    private static final int LOCATION_REQUEST_CODE = 111;
    private GoogleMap mMap;


    public UserLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_location, container, false);
        providerClient = LocationServices.getFusedLocationProviderClient(getActivity());
        nextBtn = view.findViewById(R.id.btn_next);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                if (checkLocationPermission()){
                    getDeviceCurrentLocation();
                }

            }
        });

        return view;
    }



    private boolean checkLocationPermission(){
        String [] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions, LOCATION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ( requestCode == LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getDeviceCurrentLocation();
        }
    }

    private void getDeviceCurrentLocation() {
        providerClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null){
                            return;
                        }
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        addMarkerToMyPosition(latitude,longitude);
                        Toast.makeText(getActivity(),"আপনার হোম এড্রেস ম্যাপে দেখানো হয়েছে।", Toast.LENGTH_LONG).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "lat long error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMarkerToMyPosition(double latitude, double longitude) {
        LatLng myPosition = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(myPosition).title("User current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 13f));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_userLocationFragment_to_serviceCategoryFragment);

            }
        });

    }
}