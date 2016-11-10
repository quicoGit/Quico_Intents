package com.plumbaria.intents;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pgWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://campus.somtic.net/"));
        startActivity(intent);
    }

    public void llamadaTelefono(View view) {
        // A partir de Marshmallow cambian los permisos y ahora se piden cada vez, por lo menos los mas peligrosos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:966870700"));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:966870700"));
            startActivity(intent);
        }
    }

    public void googleMaps(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:38.553468,-0.121579"));
        startActivity(intent);
    }

    public void hacerFoto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    public void mandarCorreo(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "asunto");
        intent.putExtra(Intent.EXTRA_TEXT, "texto del correo");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"smira@iesperemaria.com" });
        startActivity(intent);
    }

    private boolean isGoogleMapsInstalled() {
    //Compruobamos si tenemos google maps instalado
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0 );
            return true;
        } catch(PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void busquedaWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, "IES Pere Maria Orts");                //Busca mediante Google IES Pere Maria Orts
        startActivity(intent);
    }

    public void dialer(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:966870700")); //No llama, solo marca en el Teléfono el número 966 870 700
        startActivity(intent);
    }

    public void streetView(View view) {
        //Comprobamos que está Google Maps instalado en el terminal porque sino, deja de funcionar la aplicación.
        if(isGoogleMapsInstalled()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.streetview:cbll=38.553468,-0.121579&cbp=1,yaw,,pitch,zoom&mz=mapZoom"));   //Muestra en Google Maps el StreetView de las coordenadas establecidas
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.necesitaGoogleMaps), Toast.LENGTH_LONG).show();
        }
    }

    public void compartirCon(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Compartido desde IES Pere Maria Orts");
        intent.setType("text/plain");
        String titulo = getResources().getString(R.string.btn_compartir);
        Intent chooser = Intent.createChooser(intent, titulo);
        if(chooser.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);  //Comparte el texto con la aplicación seleccionada por el usuario
        }
    }
}