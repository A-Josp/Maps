package com.example.mapa;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;

    // Se agregan 5 paradas
    private final List<Pair<GeoPoint, String>> paradas = Arrays.asList(
            new Pair<>(new GeoPoint(15.5049, -88.0256), "Parada 1 - Centro SPS"),
            new Pair<>(new GeoPoint(15.5100, -88.0300), "Parada 2 - Mercado Guamilito"),
            new Pair<>(new GeoPoint(15.5065, -88.0220), "Parada 3 - Parque Central"),
            new Pair<>(new GeoPoint(15.5080, -88.0285), "Parada 4 - 7ma Avenida"),
            new Pair<>(new GeoPoint(15.5072, -88.0340), "Parada 5 - Estadio Olímpico")
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration.getInstance().setUserAgentValue(getPackageName());
        mapView = findViewById(R.id.mapView);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(paradas.get(0).first);

        agregarMarcadores();

        ((FloatingActionButton) findViewById(R.id.fabRuta)).setOnClickListener(v -> {
            // Ruta entre la primera y segunda parada como ejemplo
            mostrarRuta(paradas.get(0).first, paradas.get(1).first);
        });
    }

    private void agregarMarcadores() {
        for (Pair<GeoPoint, String> parada : paradas) {
            Marker marker = new Marker(mapView);
            marker.setPosition(parada.first);
            marker.setTitle(parada.second);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mapView.getOverlays().add(marker);
        }
        mapView.invalidate();
    }

    private void mostrarRuta(GeoPoint inicio, GeoPoint fin) {
        mapView.getOverlays().removeIf(overlay -> overlay instanceof Polyline);
        Polyline line = new Polyline();
        line.setPoints(Arrays.asList(inicio, fin));
        line.setColor(Color.BLUE);
        line.setWidth(8f);
        mapView.getOverlays().add(line);
        mapView.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
