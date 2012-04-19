package com.floppyinfant.android;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

/**
 * @author Arno Becker, 2010 visionera gmbh
 */
public class HelpActivity extends Activity {

  /** Kuerzel fuers Logging. */
  private static final String TAG = HelpActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.v(TAG, "onCreate(): entered...");
    setContentView(R.layout.help);

    final WebView view = (WebView) findViewById(R.id.webview);
    view.getSettings().setJavaScriptEnabled(true);
    // dies hier ginge auch:
    // view.loadUrl("http://www.visionera.de");
    // view.loadUrl("file:///android_asset/www/index.html");	// PhoneGap: Class DroidGap.class
    initialisiereWebKitEinfach(view, this);
    view.bringToFront();
  }

  /*private void initialisiereWebKit(WebView view, Context context) {
    final String mimetype = "text/html";
    final String encoding = "UTF-8";
    String htmldata;
    
    // Welche Hilfe soll angezeigt werden? 
    // Default: vollständige Hilfeseite
    final Bundle extras = getIntent().getExtras();
    int contextMenueId = 0;
    InputStream is = null;
    if (extras != null) {
      if (extras.containsKey(
          Startseite.CONTEXTMENUE_HILFE)) {
        contextMenueId = 
          extras.getInt(Startseite.CONTEXTMENUE_HILFE);
      } 
    } else {
      contextMenueId = R.raw.hilfe_komplett;
    } 
    
    is = context.getResources()
        .openRawResource(contextMenueId);

    try {
      if (is != null && is.available() > 0) {
        final byte[] bytes = new byte[is.available()];
        is.read(bytes);
        htmldata = new String(bytes);
        // Log.v(TAG, "initWebkit(): HTML der Hilfeseite: "
        // + htmldata);

        // WICHTIGER HINWEIS:
        // Workaround fuer Bug in Android (bis min. Version
        // 1.5!):
        // view.loadData(htmldata, mimetype, encoding)
        // funktioniert nicht!
        // WebView haengt sich an '<style type="text/css">'
        // auf. Saemtliche
        // CSS-Definitionen werden ignoriert und es kommt zu
        // einer Fehlermeldung
        // im WebView.
        // Loesung: loadDataWithBaseURL funktioniert!

        // view.loadData(htmldata, mimetype, encoding);
        view.loadDataWithBaseURL(null, htmldata, mimetype,
            encoding, null);

        Log.v(TAG, "initWebkit(): HTML wird angezeigt...");
      }
    } catch (IOException e) {
      Log.v(TAG, "initWebkit(): Fehler: " + e.toString());
    }
  } */
  
  /**
   * Initialisiert WebKit mit einer HTML-Seite aus dem
   * Ressourcen-Ordner.
   * 
   * @param view WebView zur Darstellung von 
   *     Webinhalten.
   * @param context Kontext der Anwendung
   */
  private void initialisiereWebKitEinfach(
    final WebView view, final Context context) {
    final String mimetype = "text/html";
    final String encoding = "UTF-8";
    String htmldata;
    
    final InputStream is = context.getResources().openRawResource(R.raw.index);    // TODO: use assets<DIR>

    try {
      if (is != null && is.available() > 0) {
        final byte[] bytes = new byte[is.available()];
        is.read(bytes);
        htmldata = new String(bytes);
        view.loadDataWithBaseURL(null, htmldata, mimetype, encoding, null);
      }
    } catch (IOException e) {
    	
    }
  }
}