package in.bitcode.threading1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtInfo;
    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtInfo = findViewById(R.id.txtInfo);
        btnDownload = findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //start a thread which will download the data
                        String [] files = {
                                "https://bitcode.in/files/file1.zip",
                                "https://bitcode.in/files/file2.zip",
                                "https://bitcode.in/files/file3.zip",
                        };
                        new DownloadThread().execute(files);
                    }
                }
        );
    }

    class DownloadThread extends AsyncTask<String, Integer, Float> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtInfo.setText("Result: awaited");
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("BitCode Server");
            progressDialog.setMessage("Downloading SomeImpFile.pdf");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected Float doInBackground(String ... files) {

            for(String file : files) {
                progressDialog.setMessage("Downloading " + file);
                for (int i = 0; i <= 100; i++) {
                    Log.e("tag", "Downloading SomeImpFile.pdf" + i + "%");
                    //txtInfo.setText("Downloading " + i + "%");

                    Integer [] progressArr = new Integer[1];
                    progressArr[0] = i;
                    publishProgress(progressArr);

                    progressDialog.setProgress(i);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return 12.12f;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            txtInfo.setText("Downloading " + values[0] + "%");
        }

        @Override
        protected void onPostExecute(Float res) {
            super.onPostExecute(res);
            progressDialog.dismiss();
            txtInfo.setText("Result: " + res);
        }
    }
}