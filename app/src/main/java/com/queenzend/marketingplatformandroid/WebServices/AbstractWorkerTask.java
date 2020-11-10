package com.queenzend.marketingplatformandroid.WebServices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;

import com.queenzend.marketingplatformandroid.Constants.ServiceConstants;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

//import app.num.imageslider.Constants.ServiceConstants;


public abstract class AbstractWorkerTask<Params, Progress, Result> extends
        AsyncTask<Params, Integer, Result> implements ServiceConstants {

    private final TaskNotifier<Result> notifier;
    private Throwable throwable;
    public String baseUrl = "";
    public String baseUrl1 = "";
    public String baseUrl2 = "";
    private int progressCount;
    private int progressTotal;
    private int cutrrentPprogress;
    private int err_state = ServiceConstants.ERR_CODE_SUCCESS;
    public String resultMessage;
    Context context;

    public AbstractWorkerTask(final TaskNotifier<Result> notifier, Context context) {

        this.cutrrentPprogress = 0;
        this.progressTotal = 1;// if assigned to 0 then it may throw divide by
        // zero if not implemented
        this.progressCount = 0;
        this.notifier = notifier;
        this.baseUrl = String.format(homeUrlFormat);
//       this.baseUrl1 = String.format(homeUrlFormat,
//               homeUrlSubFormat1,
//               homeUrlSubFormat3);
//        this.baseUrl2 = String.format(homeUrlFormat,
//                homeUrlSubFormat1,
//                homeUrlSubFormat4);
        this.context = context;
        this.resultMessage = "";
    }

    public TaskNotifier<Result> getNotifier() {
        return notifier;
    }

    public void setThrowable(final Throwable throwable) {
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (progress.length == 1) {
            if (progress[0] > cutrrentPprogress) {
                getNotifier().onProgressChange(progress[0]);
                cutrrentPprogress = progress[0];
            }
        }
    }

    protected String encodeUrl(final String url)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(url, "UTF-8");
    }

    protected URI getRequestUrl(final String requestQuery)
            throws UnsupportedEncodingException, URISyntaxException {
        String requestPath = URLEncoder.encode(requestQuery, "UTF-8");
        return new URI(requestPath);
    }

    /**
     * Error For Data updation
     *
     * @param err_state the err_state to set
     */
    public void setError_state(int err_state) {
        this.err_state = err_state;
    }

    /**
     * @return the err_state
     */
    public int getError_state() {
        return err_state;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getProgressCount() {
        return progressCount;
    }

    public int getProgressTotal() {
        return progressTotal;
    }

    public synchronized void setProgressTotal(int progressTotal) {
        this.progressTotal = progressTotal;
    }

    public synchronized void notifyProgress() {

        progressCount++;
        float total = (float) (getProgressTotal() * 0.01);
        if (progressCount < 0) {
            publishProgress(0);
        } else if (progressCount > total) {
            publishProgress(100);
        } else {
            int percent = (int) ((progressCount * 100) / total);
            publishProgress(percent);
        }
    }

    /**
     * Provides Information About Network is Available Or Not
     */
    protected boolean isNetworkAvailable() {

        ConnectivityManager connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] nwInfo = connMan.getAllNetworkInfo();

        for (int i = 0; i < nwInfo.length; i++) {
            if (nwInfo[i].getType() == ConnectivityManager.TYPE_WIFI
                    || nwInfo[i].getType() == ConnectivityManager.TYPE_MOBILE) {
                if (nwInfo[i].getState() == State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}
