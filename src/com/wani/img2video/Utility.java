package com.wani.img2video;


import android.content.Context;
import android.util.Log;

import java.io.*;

public class Utility {

    private final static String TAG = Utility.class.getName();
    private static Context mContext;

    public Utility(Context context) {
        mContext = context;
    }

    public static String excuteCommand(String command)
    {
        try {
            Log.d(TAG, "execute command : " + command);

            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();

            process.waitFor();

            Log.d(TAG, "command result: " + output.toString());

            return output.toString();

        } catch (IOException e) {

            Log.e(TAG, e.getMessage(), e);

        } catch (InterruptedException e) {

            Log.e(TAG, e.getMessage(), e);
        }

        return "";
    }

    public String getPathOfAppInternalStorage()
    {
        return mContext.getApplicationContext().getFilesDir().getAbsolutePath();
    }

    public void saveFileToAppInternalStorage(InputStream inputStream, String fileName)
    {
        File file = new File(getPathOfAppInternalStorage() + "/" + fileName);
        if (file.exists())
        {
            Log.d(TAG, "SaveRawToAppDir Delete Exsisted File");
            file.delete();
        }

        FileOutputStream outputStream;
        try {
            outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0)
            {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public static boolean isFileExsisted(String filePath)
    {
        File file = new File(filePath);
        return file.exists();
    }

    public static void deleteFileAtPath(String filePath)
    {
        File file = new File(filePath);
        file.delete();
    }
}
