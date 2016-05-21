package com.renovavision.mediapicker.image;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public enum ImagePickerOption {

    CAMERA("Take photo"),
    GALLERY("Choose from gallery");

    private String text;

    ImagePickerOption(@NonNull String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static ImagePickerOption getImageOption(@NonNull String text) {
        for (ImagePickerOption option : values()) {
            if (TextUtils.equals(option.getText(), text)) {
                return option;
            }
        }

        return null;
    }

    public static CharSequence[] getOptionItems() {
        final CharSequence[] items = new CharSequence[ImagePickerOption.values().length];
        int i = 0;
        for (ImagePickerOption option : values()) {
            items[i] = option.getText();
            i++;
        }
        return items;
    }
}
