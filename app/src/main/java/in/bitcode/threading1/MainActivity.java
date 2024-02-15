package in.bitcode.threading1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtInfo;
    private Button btnDownload;
    private ProgressDialog progressDialog;

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
                        String[] files = {
                                "https://bitcode.in/files/file1.zip",
                                "https://bitcode.in/files/file2.zip",
                                "https://bitcode.in/files/file3.zip",
                        };
                        new DownloadThread(
                                new DownloadHandler()
                        ).execute(files);
                    }
                }
        );
    }

    private class DownloadHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Pre Results: " + (String)msg.obj);
                txtInfo.setText("Pre Res: " + (String) msg.obj);
            }
            if(msg.what == 2) {
                txtInfo.setText("Progress Res: " + (Integer) msg.obj);
                progressDialog.setMessage("Progress");
                progressDialog.setProgress((Integer) msg.obj);
            }
            if(msg.what == 3) {
                txtInfo.setText("Final Res: " + (Float) msg.obj);
                progressDialog.setMessage("Final Result: " + (Float) msg.obj);
                progressDialog.dismiss();
            }
        }
    }
}