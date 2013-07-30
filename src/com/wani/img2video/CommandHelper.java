package com.wani.img2video;

/**
 * Created with IntelliJ IDEA.
 * User: wani
 * Date: 13/7/5
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 */
public class CommandHelper {
    public static String commandConvertImgToVideo(String ffmpegBinaryPath, String inputImgPath, String outputVideoPath) {
        return ffmpegBinaryPath + " -r 24 -i " + inputImgPath + " -c:v libx264 -crf 23 -pix_fmt yuv420p -s 640x480 " + outputVideoPath;
    }

    public static String commandChangeFilePermissionForExecuting(String filePath) {
        return "chmod 777 " + filePath;
    }
}
