package co.com.millennialapps.utils.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import co.com.millennialapps.utils.R;


/**
 * Created by erick on 22/6/2017.
 */

public class DialogManager {

    private static ProgressDialog loadingDialog;

    public static void showLoadingDialog(final Context context, final int title, final String message, final boolean indeterminate, final boolean cancelable) {
        dismissLoadingDialog();
        loadingDialog = new ProgressDialog(context);
        loadingDialog.setTitle(title);
        loadingDialog.setMessage(message);
        loadingDialog.setIndeterminate(indeterminate);
        loadingDialog.setCancelable(cancelable);
        loadingDialog.show();
    }

    public static void showLoadingDialog(final Context context, final int title, final int message, final boolean indeterminate, final boolean cancelable) {
        dismissLoadingDialog();
        loadingDialog = new ProgressDialog(context);
        loadingDialog.setTitle(title);
        loadingDialog.setMessage(context.getString(message));
        loadingDialog.setIndeterminate(indeterminate);
        loadingDialog.setCancelable(cancelable);
        loadingDialog.show();
    }

    public static void updateLoadingDialog(final int title, final String message) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setTitle(title);
            loadingDialog.setMessage(message);
        }
    }

    public static void updateLoadingDialog(final String title, final String message) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setTitle(title);
            loadingDialog.setMessage(message);
        }
    }

    public static void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public static void showListDialog(Context context, int title, BaseAdapter adapter, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle(title);

        builderSingle.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(adapter, onClickListener);
        builderSingle.show();
    }

    public static void showListDialog(Context context, String title, BaseAdapter adapter, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle(title);

        builderSingle.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(adapter, onClickListener);
        builderSingle.show();
    }

    public static void showCustomDialog(Context context, int title, String message, DialogInterface.OnClickListener onClickListener, View... views) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(64, 0, 64, 0);

        for (View view : views) {
            layout.addView(view, layoutParams);
        }

        builder.setView(layout);

        builder.setPositiveButton(R.string.accept, onClickListener);

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public static void showConfirmationDialog(Context context, int title, int message,
                                              DialogInterface.OnClickListener acceptAction,
                                              DialogInterface.OnClickListener cancelAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.accept, acceptAction)
                .setNegativeButton(R.string.cancel, cancelAction);
        builder.create().show();
    }

    public static void showConfirmationDialog(Context context, int title, String message,
                                              DialogInterface.OnClickListener acceptAction,
                                              DialogInterface.OnClickListener cancelAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.accept, acceptAction)
                .setNegativeButton(R.string.cancel, cancelAction);
        builder.create().show();
    }

    public static void showMessageDialog(Context context, int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(true);
        builder.create().show();
    }

    public static void showMessageDialog(Context context, int title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(true);
        builder.create().show();
    }

    public static void showPopUp(Activity activity, View view, int idResource, PopupMenu.OnMenuItemClickListener listener){
        PopupMenu popup = new PopupMenu(activity, view);
        popup.getMenuInflater().inflate(idResource, popup.getMenu());
        popup.setOnMenuItemClickListener(listener);
        popup.show();
    }
}
