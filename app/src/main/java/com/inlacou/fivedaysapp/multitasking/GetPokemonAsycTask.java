package com.inlacou.fivedaysapp.multitasking;

import android.os.AsyncTask;

import com.inlacou.fivedaysapp.http.OkhttpApiCtrl;

import okhttp3.Request;

public class GetPokemonAsycTask extends AsyncTask<String, Integer, Request> {

    private Callback callback;

    public GetPokemonAsycTask(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        /**
         * @param progress as X of 4
         */
        void onProgressUpdate(Integer progress);
        void onPreExecute();
        void onPostExecute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onPreExecute();
    }

    @Override
    protected Request doInBackground(String... strings) {
        //Paso1
        publishProgress(1);
        //Paso2
        //Paso3
        //Paso4
        return OkhttpApiCtrl.instance.getPokemonSync(strings[0]);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        callback.onProgressUpdate(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Request aBoolean) {
        super.onPostExecute(aBoolean);
        callback.onPostExecute();
    }

    @Override
    protected void onCancelled(Request aBoolean) {
        super.onCancelled(aBoolean);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
