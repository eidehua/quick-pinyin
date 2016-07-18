package com.eidehua.quickpinyin;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Edward on 7/17/2016.
 * Specialized case from chinese -> english and pinyin
 */
public class MyPinyinDialogFragment extends DialogFragment {

    public static MyPinyinDialogFragment newInstance(int title) {
        MyPinyinDialogFragment frag = new MyPinyinDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar);
        //dialog.setC
        return dialog;
    }
}
