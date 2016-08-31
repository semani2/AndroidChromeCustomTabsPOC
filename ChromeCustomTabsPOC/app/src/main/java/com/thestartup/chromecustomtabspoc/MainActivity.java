package com.thestartup.chromecustomtabspoc;

import android.content.ComponentName;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    private CustomTabsServiceConnection customTabsServiceConnection;
    private CustomTabsClient customTabsClient;
    private CustomTabsSession customTabsSession;

    private Button webViewButton;
    private Button customTabButton;

    public static final String URL = "https://www.sharefile.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webViewButton = (Button) findViewById(R.id.webViewButton);
        customTabButton = (Button) findViewById(R.id.customTabButton);

        webViewButton.setOnClickListener(this);
        customTabButton.setOnClickListener(this);

        customTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                customTabsClient = client;
                customTabsClient.warmup(0L);
                customTabsSession = customTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                customTabsClient = null;
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomTabsClient.bindCustomTabsService(MainActivity.this, CUSTOM_TAB_PACKAGE_NAME,
                customTabsServiceConnection);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == webViewButton.getId()) {
            getSupportFragmentManager().beginTransaction()
                    .add(new WebViewDialogFragment(), "WebView")
                    .commit();
        }
        else if(view.getId() == customTabButton.getId()){
            CustomTabsIntent intent = new CustomTabsIntent.Builder(customTabsSession)
                    .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setShowTitle(true)
                    .build();

            intent.launchUrl(this, Uri.parse(URL));
        }
    }
}
