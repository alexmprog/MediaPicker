package com.renovavision.mediapicker.image;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.renovavision.mediapicker.R;
import com.renovavision.mediapicker.utils.PermissionsChecker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImagePicker {

    private static final String TAG = ImagePicker.class.getSimpleName();

    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 200;

    private ImagePickerOption selectedOption;
    private String cameraFilePath;

    public void selectImage(@NonNull final Activity activity) {
        selectImage(activity, new Callback() {
            @Override
            public void openCamera() {
                activity.startActivityForResult(getCameraIntent(), REQUEST_CAMERA);
            }

            @Override
            public void openGallery() {
                activity.startActivityForResult(getGalleryIntent(), REQUEST_GALLERY);
            }
        });
    }

    public void selectImage(@NonNull final Fragment fragment) {
        selectImage(fragment.getActivity(), new Callback() {

            @Override
            public void openCamera() {
                fragment.startActivityForResult(getCameraIntent(), REQUEST_CAMERA);
            }

            @Override
            public void openGallery() {
                fragment.startActivityForResult(getGalleryIntent(), REQUEST_GALLERY);
            }

        });
    }


    private void selectImage(@NonNull final Activity activity, @NonNull final Callback callback) {
        final CharSequence[] items = ImagePickerOption.getOptionItems();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.select_image);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionsChecker.checkPermission(activity);
                selectedOption = ImagePickerOption.getImageOption(items[item].toString());
                if (selectedOption == ImagePickerOption.CAMERA) {
                    if (result) {
                        callback.openCamera();
                    }
                } else if (selectedOption == ImagePickerOption.GALLERY) {
                    if (result) {
                        callback.openGallery();
                    }
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public String onActivityResult(@NonNull Context context, int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY)
                return onSelectFromGalleryResult(context, data);
            else if (requestCode == REQUEST_CAMERA)
                return cameraFilePath;
        }

        return null;
    }


    public void onRequestPermissionsResult(final @NonNull Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        onRequestPermissionsResult(new Callback() {
            @Override
            public void openCamera() {
                activity.startActivityForResult(getCameraIntent(), REQUEST_CAMERA);
            }

            @Override
            public void openGallery() {
                activity.startActivityForResult(getGalleryIntent(), REQUEST_GALLERY);
            }
        }, requestCode, permissions, grantResults);
    }

    public void onRequestPermissionsResult(final @NonNull Fragment fragment, int requestCode, String[] permissions, int[] grantResults) {

        onRequestPermissionsResult(new Callback() {
            @Override
            public void openCamera() {
                fragment.startActivityForResult(getCameraIntent(), REQUEST_CAMERA);
            }

            @Override
            public void openGallery() {
                fragment.startActivityForResult(getGalleryIntent(), REQUEST_GALLERY);
            }
        }, requestCode, permissions, grantResults);
    }

    private void onRequestPermissionsResult(@NonNull Callback callback, int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case PermissionsChecker.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (selectedOption == ImagePickerOption.CAMERA) {
                        callback.openCamera();
                    } else if (selectedOption == ImagePickerOption.GALLERY) {
                        callback.openGallery();
                    }
                } else {
                    //code for deny
                }
                break;
        }
    }

    private String onSelectFromGalleryResult(@NonNull Context context, @NonNull Intent data) {
        Uri selectedImage = data.getData();
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = context.getContentResolver().query(selectedImage, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String picturePath = c.getString(columnIndex);
        c.close();
        return picturePath;
    }

    private Intent getCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = createImageFile();
        cameraFilePath = file.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        return intent;
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        try {
            return File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            Log.e(TAG, "Can not create file");
            return null;
        }
    }

    private Intent getGalleryIntent() {
        return new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    private interface Callback {
        void openCamera();

        void openGallery();
    }
}
