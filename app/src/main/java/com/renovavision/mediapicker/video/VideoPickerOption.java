package com.renovavision.mediapicker.video;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public enum VideoPickerOption {

    CAMERA("Take video"),
    GALLERY("Choose from gallery");

    private String text;

    VideoPickerOption(@NonNull String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static VideoPickerOption getImageOption(@NonNull String text) {
        for (VideoPickerOption option : values()) {
            if (TextUtils.equals(option.getText(), text)) {
                return option;
            }
        }

        return null;
    }

    public static CharSequence[] getOptionItems() {
        final CharSequence[] items = new CharSequence[VideoPickerOption.values().length];
        int i = 0;
        for (VideoPickerOption option : values()) {
            items[i] = option.getText();
            i++;
        }
        return items;
    }
}
