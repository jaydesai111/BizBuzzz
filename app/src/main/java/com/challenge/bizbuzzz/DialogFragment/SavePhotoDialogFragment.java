package com.challenge.bizbuzzz.DialogFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.challenge.bizbuzzz.R;
import com.challenge.bizbuzzz.Utility.BizBuzzzUtility;


public class SavePhotoDialogFragment extends DialogFragment implements View.OnClickListener{
    View view;
    Button bt_upload;
    private int _Camera = 0, _Gallery = 1;
    private Uri fileUri;
    String picturePath;
    ImageView iv_photo;
    private final int PERMISSION_REQUEST_CODE=1;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL,android.R.style.Theme_Holo_Light_NoActionBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_fragment_savephoto, container, false);
        bt_upload = view.findViewById(R.id.bt_upload);
        iv_photo = view.findViewById(R.id.iv_photo);

        iv_photo.setOnClickListener(this);
        bt_upload.setOnClickListener(this);
       checkPermission();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_upload:
                SelectImage();
                break;
            case R.id.iv_photo:
                if(checkPermission()) {
                    SelectImage();
                }
                break;
        }
    }
    private void SelectImage() {
        CharSequence colors[] = new CharSequence[]{"Gallery", "Camera"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    CallGallery();
                } else if (which == 1) {
                    CallCamera();
                }
            }
        });
        builder.show();
    }
    //This will call the gallery.
    private void CallGallery() {

        Intent GalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent, _Gallery);
    }

    //this method will call the camera
    private void CallCamera() {
        if (!BizBuzzzUtility.isDeviceSupportCamera(getActivity())) {
            BizBuzzzUtility.showToast(getActivity(), "Device does not support camera.");
        } else {
            captureImage();
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = BizBuzzzUtility.getOutputMediaFileUri(1);
        if (fileUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, _Camera);
        } else {
            BizBuzzzUtility.showToast(getActivity(), "Device does not support camera.");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == _Camera) {

            gotImageFromCamera(resultCode);
        } else if (requestCode == _Gallery && resultCode == getActivity().RESULT_OK && null != data) {

            gotImageFromGallory(data);
        } else if (requestCode == _Gallery && resultCode == getActivity().RESULT_CANCELED) {
        }
    }

    private void gotImageFromCamera(int resultCode) {
        if (resultCode == getActivity().RESULT_OK) {
            Bitmap bitmap = previewCapturedImage();


            if (bitmap != null) {
                iv_photo.setImageBitmap(bitmap);
            }
        } else if (resultCode == getActivity().RESULT_CANCELED) {
            BizBuzzzUtility.showToast(getActivity(), "Canceled image.");
        } else {
            BizBuzzzUtility.showToast(getActivity(), "Failed to capture image.");
        }
    }

    private Bitmap previewCapturedImage() {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            picturePath = fileUri.getPath();
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            return bitmap;
        } catch (NullPointerException e) {
            BizBuzzzUtility.showToast(getActivity(), "Failed to capture image.");
            e.printStackTrace();
        }
        return null;
    }

    private void gotImageFromGallory(Intent data) {
        Uri selectedImage = data.getData();

        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);

        if (cursor == null) {

            picturePath = selectedImage.getPath();
        }
        else {

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            iv_photo.setImageBitmap(BitmapFactory.decodeFile(picturePath, options));

           // checkUpload();



    }
    private boolean checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA);
            if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {

                return true;

            } else {
                requestPermission();
                return false;

            }

        } else {
            return true;
        }

    }
    private void requestPermission() {



        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]== PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "enable permission", Toast.LENGTH_LONG).show();


                } else {

                    Toast.makeText(getActivity(), "denied permission", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


}
