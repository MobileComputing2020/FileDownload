package com.sleepycoder.filedownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String url_to_download = "https://file-examples.com/wp-content/uploads/2017/02/file_example_CSV_5000.csv";

    TextView downloaded_data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloaded_data = findViewById(R.id.downloaded_data);


        Downloader downloader = new Downloader();
        downloader.execute();
    }


    private class Downloader extends AsyncTask<Object, Object, Object> {


        @Override
        protected Object doInBackground(Object... objects) {

            try {
                File file = new File(getFilesDir().getAbsolutePath() + "/" + url_to_download.split("/")[url_to_download.split("/").length - 1]);
                FileOutputStream fos = new FileOutputStream(file);
                URL url = new URL(url_to_download);
                InputStream is = url.openStream();
                byte[] buffer = new byte[4096];
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                is.close();
                fos.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            StringBuilder sb = new StringBuilder("");
            File file = new File(getFilesDir().getAbsolutePath() + "/" + url_to_download.split("/")[url_to_download.split("/").length - 1]);
            try {
                FileInputStream fis = new FileInputStream(file);

                byte[] buffer = new byte[4096];
                while (fis.read(buffer) > 0) {
                    sb.append(new String(buffer));
                }

                downloaded_data.setText(sb.toString());

                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
