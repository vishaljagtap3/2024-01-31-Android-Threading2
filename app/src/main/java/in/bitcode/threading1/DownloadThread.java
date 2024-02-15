package in.bitcode.threading1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

class DownloadThread extends AsyncTask<String, Integer, Float> {

    private Handler handler;

    public DownloadThread(Handler handler) {

        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //txtInfo.setText("Result: awaited");
        Message message = new Message();
        message.obj = "Result: Awaited";
        message.what = 1;
        handler.sendMessage(message);
    }

    @Override
    protected Float doInBackground(String ... files) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(String file : files) {
            for (int i = 0; i <= 100; i++) {
                Log.e("tag", "Downloading SomeImpFile.pdf" + i + "%");
                //txtInfo.setText("Downloading " + i + "%");

                Integer [] progressArr = new Integer[1];
                progressArr[0] = i;
                publishProgress(progressArr);
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
        //txtInfo.setText("Downloading " + values[0] + "%");
        Message message = new Message();
        message.obj = values[0];
        message.what = 2;
        handler.sendMessage(message);
    }

    @Override
    protected void onPostExecute(Float res) {
        super.onPostExecute(res);
        Message message = new Message();
        message.obj = res;
        message.what = 3;
        handler.sendMessage(message);
        //txtInfo.setText("Result: " + res);
    }
}
