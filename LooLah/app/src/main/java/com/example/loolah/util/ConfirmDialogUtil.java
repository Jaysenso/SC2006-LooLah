package com.example.loolah.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.loolah.R;

import java.util.Objects;

public class ConfirmDialogUtil extends DialogFragment {
    private ConfirmDialogListener confirmDialogListener;
    private String confirmDialogMessage;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_confirm, null);

        ((TextView) view.findViewById(R.id.tv_dialog_message)).setText(confirmDialogMessage);
        view.findViewById(R.id.btn_dialog_confirm).setOnClickListener(v -> confirmDialogListener.onClickConfirm());
        view.findViewById(R.id.btn_dialog_cancel).setOnClickListener(v -> Objects.requireNonNull(ConfirmDialogUtil.this.getDialog()).cancel());

        builder.setView(view);
        return builder.create();
    }

    public void setConfirmDialogListener(ConfirmDialogListener confirmDialogListener) {
        this.confirmDialogListener = confirmDialogListener;
    }

    public void setConfirmDialogMessage(String confirmDialogMessage) {
        this.confirmDialogMessage = confirmDialogMessage;
    }

    public interface ConfirmDialogListener {
        void onClickConfirm();
    }
}
