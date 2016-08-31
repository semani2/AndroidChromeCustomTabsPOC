package com.thestartup.chromecustomtabspoc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by sai on 8/31/16.
 */
public class WebViewDialogFragment extends DialogFragment {

    private WebView webView;
    private TextView closeTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.WebViewDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.web_view_fragment, container, false);
        initWebView(v);
        closeTextView = (TextView) v.findViewById(R.id.closeTextView);
        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return v;
    }

    private void initWebView(View v) {
        webView = (WebView) v.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollContainer(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(MainActivity.URL);
    }
}
