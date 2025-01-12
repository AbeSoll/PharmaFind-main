package com.example.pharmafind;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.model.RectangularBounds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 2;
    private GoogleMap mMap;
    ArrayList<MarkerOptions> arrayList = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize Places API
        Places.initialize(getApplicationContext(), "AIzaSyBRvzeYlDX3ooANb7PEXeAaofcMMyMIjPQ");
        placesClient = Places.createClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Add pharmacy to the arrayList
// Add all pharmacies to the arrayList
        arrayList.add(new MarkerOptions().position(new LatLng(3.06666484902148, 101.48907708950229)).title("Health Lane Family Pharmacy Seksyen 7"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0628060710693052, 101.48217867620068)).title("CARiNG Pharmacy Central i-City"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0623373548861945, 101.48218538172281)).title("Watsons I-City Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.05891235659538, 101.50562682425411)).title("FirstCare Pharmacy"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0768148764533385, 101.494622660439)).title("Jovian Selcare Pharmacy (UNISEL)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0762430413446697, 101.49695909471536)).title("U.N.I FARMASI SEK 7 SHAH ALAM"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.081730829518426, 101.49232243895051)).title("Guardian Seksyen7 Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.075393076462164, 101.48616624570177)).title("MedAid Pharmacy"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0685874227012744, 101.48892445785363)).title("Vcare Pharmacy Seksyen 7"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0614223740239175, 101.47246961515712)).title("Watsons AEON Bukit Raja (Pharmacy)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0653214331382137, 101.47457123050042)).title("RS Homeopati | Homeopathy Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0726379512341944, 101.47619408447048)).title("Remedi Pharmaceuticals (M) Sdn. Bhd."));
        arrayList.add(new MarkerOptions().position(new LatLng(3.046825910977822, 101.50561780775382)).title("Ee Pharmacy (Seksyen 17 Shah Alam)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.087782801895842, 101.47696662309718)).title("BIG PHARMACY ECO ARDENCE"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0588404505263282, 101.46407045378379)).title("Cornerstone Pharmacy"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.056922151446035, 101.45928696912712)).title("BIG Pharmacy Klang Taman Eng Ann"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0705721136661897, 101.51701625378384)).title("Selcare Pharmacy (Pkns)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0566543720716313, 101.4599320671187)).title("AA Pharmacy Taman Eng Ann"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0963467541867034, 101.48100011515714)).title("Health Lane Family Pharmacy Eco Ardence"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.046647601655924, 101.50417565378382)).title("Kaisar Farmasi SS17 Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0715135667468076, 101.51720998447047)).title("Guardian"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.086024649963819, 101.52265398447047)).title("AA Pharmacy Shah Alam Sek9"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0590817757083744, 101.45920218447044)).title("CARiNG Pharmacy Taman Eng Ann, Klang"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0715871295241994, 101.48954129567832)).title("Farmasi Klinik Pakar Hospital Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.072573773346485, 101.52449726912717)).title("BIG PHARMACY SHAH ALAM S14"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.085927469553227, 101.52285495672193)).title("All Day Pharmacy (Seksyen 9)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0455134807555795, 101.51742151297275)).title("Sunway Multicare Pharmacy Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0669704616025304, 101.47401467801153)).title("Dr Eyes Marketing"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0869969493366085, 101.52233823788188)).title("Friends Pharmacy"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0664576528226752, 101.52552026062855)).title("EE Pharmacy (EE Farmasi)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0436879669120067, 101.51782732683824)).title("All Day Pharmacy (Seksyen 18)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0848567750400226, 101.5233660813043)).title("CARiNG Pharmacy Plaza Shah Alam, Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1015406949935307, 101.45988574523581)).title("Alive Pharmacy Setia Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.047373778879632, 101.51670565979886)).title("BIG Pharmacy Ole Ole Mall"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.025653326044031, 101.48114995392866)).title("iMED Pharmacy MY (Jalan Kebun, Kampung Jawa)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.050912237178626, 101.53381706062852)).title("BIG Pharmacy Seksyen 19 Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.038936761945848, 101.51947817650861)).title("Farmasi Ultra Medic Seksyen 24"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.085249070798642, 101.52339455315163)).title("GUARDIAN Plaza Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0428608083617754, 101.44668998581291)).title("AK Pharmacy"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0442882391037553, 101.44786944910467)).title("Biochem Pharmacy"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.067063735358761, 101.52310766964214)).title("Rasumi Medipharma Sdn Bhd"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.065029752745477, 101.46915545939414)).title("KLANG CHEMIST SDN BHD (FARMASI KLANG)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0398340613542936, 101.51897429238872)).title("CELTA PHARMACY SEKSYEN 24 SHAH ALAM"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.067086740493857, 101.45267596755714)).title("Evergreen Pharmacy Sdn. Bhd."));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0991766249917014, 101.4442560991901)).title("ALPRO Pharmacy Setia Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1069422908927984, 101.44322887185177)).title("BIG Pharmacy Setia Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0244034378426337, 101.48237338513842)).title("Farmasi Ideal | Desa Latania"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1028240794376045, 101.44438303788193)).title("First Care Pharmacy - Setia Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.052545861593982, 101.53382735376204)).title("Farmasi Anis"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0586935419685513, 101.54226526115933)).title("FARMASI ULTRA MEDIC SEKSYEN 20"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.102317270629173, 101.4451058680388)).title("Eden Pharmacy"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.092441945496563, 101.5449564611593)).title("Ezycare Pharmacy (Farmasi Seksyen 13 Shah Alam)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.080285650943307, 101.47722354438469)).title("Vcare Pharmacy HQ"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.116432615743845, 101.52610369978342)).title("BIG Pharmacy Bukit Jelutong"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1121680569858787, 101.46057239092322)).title("CARiNG Pharmacy Setia City Mall, Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.034203813916238, 101.53520753840752)).title("Ee Pharmacy (Mr Pharmacy)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.041849116465625, 101.55128953840754)).title("BIG Pharmacy Seksyen 26 Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.101241601141177, 101.44438633840755)).title("AA Pharmacy Setia Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1283000477381577, 101.47185831213984)).title("Vcare Pharmacy Setia Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.104634024129902, 101.44492303840752)).title("Health Lane Family Pharmacy Setia Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.129651548186668, 101.4670955232071)).title("BIG Pharmacy Setia Taipan Shah Alam (Rehab Flagship Store)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.022718326160132, 101.49435573061294)).title("Medplus Pharmacy & Baby (Main Warehouse)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1098766227607144, 101.44567114528697)).title("BIG PHARMACY LOTUS'S SETIA ALAM"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.078862592955787, 101.55098791565572)).title("Health Lane Family Pharmacy Section 13 Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1265918687341, 101.46812091262262)).title("Pharmhouse Pharmacy Setia Taipan 2 (Setia Alam)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0326489366603915, 101.53742286603472)).title("ALPRO Pharmacy Taman Sri Muda - Minute Consult"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1042434060549606, 101.53675471565577)).title("Mylife Healthcare Sdn. Bhd."));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0598872660803185, 101.45868411578843)).title("Farmasi Eng Ann"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0329460115027533, 101.53622378842996)).title("iPHARMACY"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0329661468257396, 101.53634857457506)).title("Farmasi Medishop"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0573544031187248, 101.46270740233072)).title("Tabib Cina Yee Kit Mun"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0115466903098373, 101.46400561829451)).title("SREE SENTOSA PHARMACY (TAMAN SENTOSA)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0331272648073475, 101.53546655197326)).title("Guardian @ Shah Alam, Section 25"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0697120017317805, 101.43025461745528)).title("Revive Pharmacy (KK) Sdn Bhd [ Farmasi Revive ]"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1148316046614744, 101.52563166553905)).title("AA Pharmacy Bukit Jelutong"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.128557887193862, 101.46675602153134)).title("FirstCare Pharmacy Setia Taipan (Setia Gemilang)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0604016664402183, 101.45954227173254)).title("Guardian Health & Beauty Sdn Bhd"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.098382577428926, 101.5559933646587)).title("BIG Pharmacy TTDI Jaya Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.116055928786999, 101.52907826560134)).title("CARiNG Pharmacy Plaza Jelutong, Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.135648343389897, 101.46520388187827)).title("Farmasi Kaisar Setia Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0146299490527975, 101.46520388187827)).title("Health Lane Family Pharmacy Sentosa Klang"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0745543637184927, 101.58705890594823)).title("Unipharm Pharmacy"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0039520952040344, 101.53859166949763)).title("BIG Pharmacy Kota Kemuning"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0786132821111782, 101.54882829514223)).title("CARiNG Pharmacy Aeon Mall Shah Alam, Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0924316442020565, 101.54461407414232)).title("Pusat Rawatan Homeopathy Homeomed Shah Alam"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.062970386043019, 101.46005404060153)).title("Health Spring (M) Sdn. Bhd."));
        arrayList.add(new MarkerOptions().position(new LatLng(3.049599843742933, 101.42915499294106)).title("Vcare pharmacy teluk pulai"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0341720884224714, 101.4284683474375)).title("Constant Pharmacy (Persiaran)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0917679091420918, 101.52288210417778)).title("Caring Pharmacy Plaza Masalam"));
        arrayList.add(new MarkerOptions().position(new LatLng(2.9954303112232545, 101.46726381837621)).title("All Day Pharmacy (Bandar Puteri Klang)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1143939863584027, 101.45971071784973)).title("Watsons Setia City Mall (Pharmacy)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.1411332687560023, 101.43979799824635)).title("ALPRO Pharmacy Pekan Meru - Minute Consult"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.004841013780032, 101.53857790763571)).title("AA Pharmacy Kota Kemuning"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.12022183674313, 101.52391207243313)).title("Watsons Bukit Jelutong"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0770648949348893, 101.54680394279464)).title("Watsons Aeon Shah Alam (Pharmacy)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0142871013625943, 101.43808138448742)).title("99 Pharmacy Bukit Tinggi Klang"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0853378564548617, 101.55759908580792)).title("Health Lane Family Pharmacy Glenmarie"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.008505751726054, 101.56133639956681)).title("EE PHARMACY (Seksyen 28 Shah Alam)"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0748376749548894, 101.58775191734657)).title("CARiNG Pharmacy"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0829615983150207, 101.5270365746547)).title("Farmasi Medicpoint"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0084621587662324, 101.53436284564921)).title("Health Lane Family Pharmacy Kota Kemuning"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.0021988282169283, 101.53371797444815)).title("CARiNG Pharmacy @ Gamuda Walk Mall"));
        arrayList.add(new MarkerOptions().position(new LatLng(3.015834633894057, 101.46587710498383)).title("Sunlight Medical Hall Sdn. Bhd."));
    }

    private void setCurrentLocation(double latitude, double longitude) {
        LatLng specificLatLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(specificLatLng, 15f));
        addMarkersForNearestHospitals(specificLatLng);
    }

    private void addMarkersForNearestHospitals(LatLng currentLatLng) {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(currentLatLng.latitude - 0.1, currentLatLng.longitude - 0.1),
                new LatLng(currentLatLng.latitude + 0.1, currentLatLng.longitude + 0.1));

        // Set the type filter to hospitals
        Autocomplete.IntentBuilder intentBuilder = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, placeFields)
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .setCountry("YOUR_COUNTRY_CODE")
                .setLocationBias(bounds);

        // Start the autocomplete intent
        Intent intent = intentBuilder.build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng placeLatLng = place.getLatLng();
                String placeName = place.getName();

                // Add a marker for the selected place
                mMap.addMarker(new MarkerOptions()
                        .position(placeLatLng)
                        .title(placeName));
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            setCurrentLocation(3.068424820822796, 101.49355750168766);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String destinationTitle = marker.getTitle();

        // Show the location name in a Toast message
        Toast.makeText(this, "Location: " + destinationTitle, Toast.LENGTH_SHORT).show();

        return false; // Return false to allow further processing of the click event.
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (MarkerOptions markerOptions : arrayList) {
            Marker marker = mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerOptions.getPosition()));

            // Set the OnMarkerClickListener for each marker
            mMap.setOnMarkerClickListener(this);

            // Set an OnClickListener for the info window (title)
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null; // Return null to use the default info window
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View infoView = getLayoutInflater().inflate(R.layout.custom_info_window, null);

                    TextView titleTextView = infoView.findViewById(R.id.titleTextView);

                    // Set the title (location name) in the info window
                    titleTextView.setText(marker.getTitle());

                    // Set an OnClickListener for the info window (the entire info window)
                    infoView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Open Google Maps for the selected location
                            Uri gmmIntentUri = Uri.parse("geo:" + marker.getPosition().latitude + "," + marker.getPosition().longitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package for Google Maps

                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(mapIntent);
                            } else {
                                Toast.makeText(MapsActivity.this, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    return infoView;
                }
            });
        }

        // Check location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            setCurrentLocation(3.068424820822796, 101.49355750168766);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
}