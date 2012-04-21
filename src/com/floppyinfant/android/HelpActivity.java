package com.floppyinfant.android;

import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebView;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

		final WebView view = (WebView) findViewById(R.id.webview);
		view.getSettings().setJavaScriptEnabled(true);
		initWebView(view, this);
		// dies hier ginge auch:
		// view.loadUrl("http://www.visionera.de");
		// view.loadUrl("file:///android_asset/www/index.html");	// PhoneGap: Class DroidGap.class
		view.bringToFront();
	}

	/**
	 * Initialisiert WebKit mit einer HTML-Seite aus dem
	 * Ressourcen-Ordner.
	 * 
	 * @param view WebView zur Darstellung von Webinhalten
	 * @param context Kontext der Anwendung
	 * @param res raw-ressource (HTML-Seite in Directory /res/raw)
	 */
	private void initWebView(final WebView view, final Context context) {
		final String mimetype = "text/html";
		final String encoding = "UTF-8";
//		final int res = R.raw.index;
		final String url = "www/index.html";

		// Folder /res/raw
//		final InputStream ins = context.getResources().openRawResource(res);
		
		// Folder /assets
		AssetManager assetManager = this.getAssets();
		InputStream ins;
		
		try {
			ins = assetManager.open(url);
			if (ins != null && ins.available() > 0) {
				final byte[] bytes = new byte[ins.available()];
				ins.read(bytes);
				String html = new String(bytes);
				view.loadDataWithBaseURL(null, html, mimetype, encoding, null);
			}
		} catch (IOException e) {

		}
	}
}