package com.challenge.bizbuzzz.Utility;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Guidezie on 12-09-2017.
 */

public class BizBuzzzUtility {

    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";
    private static final String ImagaeFolderName = "BizBuzzz";
    public static boolean isDeviceSupportCamera(Context context) { //Checking device has camera hardware or not
        if (context.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    public static Uri getOutputMediaFileUri(int type) {//Creating file uri to store image/video
        return Uri.fromFile(BizBuzzzUtility.getOutputMediaFile(type));
    }

    public static File getOutputMediaFile(int type) {//returning image / video
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ImagaeFolderName);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new java.util.Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == 2) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String getFileExtension(Context context,Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public static boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;

    }
    public static boolean isConnected()
    {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec (command).waitFor() == 0);
        }catch (Exception e) {
            return false;
        }
    }
    public static void displayMessageAlert(String Message, Context c) {
        try {
            new AlertDialog.Builder(c)
                    .setMessage(Message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    }).show();
        } catch (Exception e) {

        }
    }
}
