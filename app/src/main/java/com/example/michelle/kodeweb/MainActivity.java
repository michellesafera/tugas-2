package com.example.michelle.kodeweb;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener {

    TextView tv;
    Spinner spinner;
    EditText input;
    Button btn;
    public String url = null;
    ProgressBar load;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sp_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tv = (TextView) findViewById(R.id.textView);
        input = (EditText) findViewById(R.id.web);
        load = (ProgressBar) findViewById(R.id.load);
        load.setVisibility(View.GONE);

        btn=(Button)findViewById(R.id.btnGet);
    }

    public void onClick(View view) {
        url = spinner.getSelectedItem() + input.getText().toString();
        boolean valid = Patterns.WEB_URL.matcher(url).matches();

        if (valid) {
            getSupportLoaderManager().restartLoader(0,null,this);
            load.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
        } else {
            Loader loader = getSupportLoaderManager().getLoader(0);
            if (loader != null) {
                loader.cancelLoad();
            }
            load.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            tv.setText("Can't find the Website");

        }
    }



    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new ConnectInternet(this,url);
    }


    public void onLoadFinished(android.support.v4.content.Loader<String> loader, String data) {
        load.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
        tv.setText(data);
    }

    public void onLoaderReset(android.support.v4.content.Loader<String> loader) {

    }



}
