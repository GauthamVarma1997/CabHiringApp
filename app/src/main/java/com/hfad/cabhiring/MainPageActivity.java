package com.hfad.cabhiring;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karan.churi.PermissionManager.PermissionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    FirebaseAuth auth;
    FirebaseUser user;
    String user_id;
    DatabaseReference databaseReference;
    TextView t1, t2;
    private GoogleMap mMap;
    GoogleApiClient client;
    LocationRequest request;
    LatLng startLatLng,endLatLng;
    String name,email;
    Marker currentmarker,destinationmarker;
    Button b3_sourcebutton, b5_ridenow, b7_ridelater;
    AutoCompleteTextView b4_destinationbutton;
    ImageView i1;

    PermissionManager permissionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_page);


        auth = FirebaseAuth.getInstance();
        i1 = (ImageView)findViewById(R.id.imageView2);

        permissionManager = new PermissionManager()
        {

        };
        permissionManager.checkAndRequestPermissions(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        b3_sourcebutton = (Button)findViewById(R.id.button3);

        b5_ridenow = (Button) findViewById(R.id.button5);
        b7_ridelater = (Button) findViewById(R.id.button7);

        //Autocomplete Text View for Destiantion
        b4_destinationbutton = (AutoCompleteTextView) findViewById(R.id.button4);
        String[] locations ={"Madukarai","Kovai Pudur","Coimbatore","Walayar","Kanjikode","Palakad","Podanur","Hell"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,locations);
        b4_destinationbutton.setThreshold(1);
        b4_destinationbutton.setAdapter(adapter);


        //setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent myintent = new Intent(MainPageActivity.this, MainActivity.class);
            startActivity(myintent);
            finish();
        } else {
            user_id = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    t1 = findViewById(R.id.name_text);
                    t2 = findViewById(R.id.email_text);
                    t1.setText(name);
                    t2.setText(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        b3_sourcebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    Intent i = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(MainPageActivity.this);
                    startActivityForResult(i,200);
                }catch (GooglePlayServicesNotAvailableException e)
                {
                    e.printStackTrace();
                }catch (GooglePlayServicesRepairableException e)
                {
                    e.printStackTrace();
                }
            }
        });

        /*b4_destinationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    Intent i = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(MainPageActivity.this);
                    startActivityForResult(i,400);

                }catch (GooglePlayServicesNotAvailableException e)
                {
                    e.printStackTrace();
                }catch (GooglePlayServicesRepairableException e)
                {
                    e.printStackTrace();
                }
            }
        });*/

        b5_ridenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainPageActivity.this,RidenowActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String src = b3_sourcebutton.getText().toString();
                String dest = b4_destinationbutton.getText().toString();

                i.putExtra("source",src);
                i.putExtra("destination",dest);

                if(dest.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter a destination...",Toast.LENGTH_SHORT).show();
                }
                else
                    startActivity(i);


            }
        });

        b7_ridelater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(MainPageActivity.this,RidelaterActivity.class);
//                startActivity(i);
                Toast.makeText(MainPageActivity.this, "uncomment ride later go there", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest().create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(500);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        //LocationServices.FusedLocationApi.removeLocationUpdates(client,this);

        if(location==null)
        {
            Toast.makeText(getApplicationContext(),"Location could not be found",Toast.LENGTH_LONG).show();
        }
        else
        {
            startLatLng = new LatLng(location.getLatitude(),location.getLongitude());

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try
            {
                List<Address> myaddresses = geocoder.getFromLocation(startLatLng.latitude,startLatLng.longitude,1);
                String address = myaddresses.get(0).getAddressLine(0);
                String city  = myaddresses.get(0).getLocality();
                b3_sourcebutton.setText(address + " "+ city);

            }catch(IOException e)
            {
                e.printStackTrace();
            }

            if(currentmarker==null)
            {
                MarkerOptions options = new MarkerOptions();
                options.position(startLatLng);
                options.title("Current Position");
                currentmarker =  mMap.addMarker(options);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng,15));

            }
            else if(currentmarker!=null)
            {
                currentmarker.setPosition(startLatLng);
            }

            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    LatLng center = mMap.getCameraPosition().target;

                    if(b4_destinationbutton.getText().toString().equals("")&&currentmarker!=null)
                    {
                        currentmarker.remove();
                        MarkerOptions m2 = new MarkerOptions();
                        m2.title("Current Location");
                        m2.position(center);
                        currentmarker = mMap.addMarker(m2);
                        startLatLng = currentmarker.getPosition();
                        b3_sourcebutton.setText(getStringAddress(currentmarker.getPosition().latitude,currentmarker.getPosition().longitude));
                    }

                    else
                    {
                        i1.setVisibility(View.GONE);
                    }
                }
            });




        }

    }

    private String getStringAddress(double lat, double lng)
    {

        String address = "";
        String city = "";
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this,Locale.getDefault());
        try
        {
            addresses = geocoder.getFromLocation(lat,lng,1);
            address = addresses.get(0).getAddressLine(0);
            city  = addresses.get(0).getLocality();
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        return address + " "+city;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        client.connect();




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode,permissions,grantResults);

        ArrayList<String> denied_arrays = permissionManager.getStatus().get(0).denied;

        if(denied_arrays.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"User Granted Permission",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_payments) {
            Intent j = new Intent(MainPageActivity.this,paymentActivity.class);
            startActivity(j);
        } else if (id == R.id.nav_trips) {
            Intent j =  new Intent(MainPageActivity.this,yourRides.class);
            startActivity(j);


        } else if (id == R.id.nav_help) {
            Intent j = new Intent(MainPageActivity.this,helpActivity.class);
            startActivity(j);

        } else if (id == R.id.nav_rides) {

        } else if (id == R.id.nav_signout)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null)
            {
                auth.signOut();
                Intent i = new Intent(MainPageActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"User is already signed out",Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==200)
        {
            if(resultCode==RESULT_OK)
            {
                Place place = PlaceAutocomplete.getPlace(this,data);
                String name = place.getName().toString();
                startLatLng = place.getLatLng();
                b3_sourcebutton.setText(name);


                if(currentmarker==null)
                {
                    MarkerOptions options1 = new MarkerOptions();
                    options1.title("Pickup Location");
                    options1.position(startLatLng);

                    currentmarker = mMap.addMarker(options1);
                }
                else
                {
                    currentmarker.setPosition(startLatLng);

                }


            }
        }
        else if(requestCode==400)
        {
            if(resultCode == RESULT_OK)
            {
                Place myplace = PlaceAutocomplete.getPlace(this,data);
                String name = myplace.getName().toString();
                endLatLng = myplace.getLatLng();
                b4_destinationbutton.setText(name);


                if(destinationmarker==null)
                {
                    MarkerOptions options1 = new MarkerOptions();
                    options1.title("Destination");
                    options1.position(endLatLng);

                    destinationmarker = mMap.addMarker(options1);
                }

                else
                {
                    destinationmarker.setPosition(endLatLng);

                }

            }
        }
    }
}
