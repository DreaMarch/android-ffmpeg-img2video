package com.wani.img2video;

import android.content.Context;
import com.example.img2video.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class FfmpegController {

    private static Context mContext;
    private static Utility mUtility;
    private static String mFfmpegBinaryPath;

    public FfmpegController(Context context) {

        mContext = context;

        mUtility = new Utility(context);

        initFfmpeg();
    }

    private void initFfmpeg()
    {
        /*
        Save the ffmpeg binary to app internal storage, so we can use it by executing java runtime command.
         */

        mFfmpegBinaryPath = mUtility.getPathOfAppInternalStorage() + "/ffmpeg";

        if (Utility.isFileExsisted(mFfmpegBinaryPath))
            return;

        InputStream inputStream = mContext.getResources().openRawResource(R.raw.ffmpeg);

        mUtility.saveFileToAppInternalStorage(inputStream, "ffmpeg");

        Utility.excuteCommand(CommandHelper.commandChangeFilePermissionForExecuting(mFfmpegBinaryPath));
    }

    public void convertImageToVideo(String inputImgPath)
    {
        /*
        Delete previous video.
         */

        if (Utility.isFileExsisted(pathOuputVideo()))
            Utility.deleteFileAtPath(pathOuputVideo());

        /*
        Save the command into a shell script.
         */

        saveShellCommandImg2VideoToAppDir(inputImgPath);

        Utility.excuteCommand("sh" + " " + pathShellScriptImg2Video());
    }

    public String pathOuputVideo()
    {
        return mUtility.getPathOfAppInternalStorage() + "/out.mp4";
    }

    private String pathShellScriptImg2Video()
    {
        return mUtility.getPathOfAppInternalStorage() + "/img2video.sh";
    }

    private void saveShellCommandImg2VideoToAppDir(String inputImgPath)
    {
        String command = CommandHelper.commandConvertImgToVideo(mFfmpegBinaryPath, inputImgPath, pathOuputVideo());

        InputStream is = new ByteArrayInputStream(command.getBytes());

        mUtility.saveFileToAppInternalStorage(is, "img2video.sh");
    }
}

