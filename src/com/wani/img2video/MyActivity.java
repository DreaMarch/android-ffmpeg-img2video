package com.wani.img2video;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
import com.example.img2video.R;

public class MyActivity extends Activity {

    private static VideoView mVideoView;
    private static FfmpegController mFfmpegController;
    private static Utility mUtility;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();

        initInstances();
    }

    private void initUi()
    {
        setContentView(R.layout.main);

        mVideoView = (VideoView) findViewById(R.id.videoView);
    }

    private void initInstances()
    {
        mFfmpegController = new FfmpegController(this);

        mUtility = new Utility(this);
    }

    public void onClickConvertImg2Video(View view)
    {
        /*
        It takes a long time to convert images into a video,
        So we add loading progress dialog while converting.
         */

        AsyncTask asyncTask = new AsyncTask() {

            ProgressDialog mProgressDialog;

            @Override
            protected void onPreExecute() {
                mProgressDialog = new ProgressDialog(MyActivity.this);

                mProgressDialog.setMessage("Converting...");

                mProgressDialog.setCancelable(false);

                mProgressDialog.show();
            }

            @Override
            protected Object doInBackground(Object... params) {

                saveInputImgToAppInternalStorage();

                mFfmpegController.convertImageToVideo(mUtility.getPathOfAppInternalStorage() + "/" + "img%05d.png");

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                mProgressDialog.dismiss();
            }
        };

        asyncTask.execute();
    }

    public void onClickPlayVideo(View view)
    {
        playVideo();
    }

    public void playVideo()
    {
        mVideoView.setVideoPath(mFfmpegController.pathOuputVideo());

        mVideoView.start();
    }

    private void saveInputImgToAppInternalStorage()
    {
        /*
        The testing images in R.raw will be saved into the app internal storage,
        so we can use ffmpeg command to convert images into an video.
         */

        for (int i = 0; i <= 299 ; i++)
        {
            String imgName = String.format("img%05d",i);

            int imgResId = getResources().getIdentifier(imgName,"raw",getPackageName());

            mUtility.saveFileToAppInternalStorage(getResources().openRawResource(imgResId),imgName + ".png");
        }
    }
}
