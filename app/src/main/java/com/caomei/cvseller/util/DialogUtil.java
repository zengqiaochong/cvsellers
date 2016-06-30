package com.caomei.cvseller.util;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.caomei.cvseller.CommonData.IDialogOperation;

public class DialogUtil {

	public static void DefaultDialog(Context mContext, String title,
			String msg, String posString, String negString,final IDialogOperation iOperation) {
		Builder builder = new Builder(mContext);
		builder.setMessage(msg);

		builder.setTitle(title);

		builder.setPositiveButton(posString, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				iOperation.onPositive();
			}
		});
		builder.setNegativeButton(negString, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				iOperation.onNegative();
			}
		});
		builder.create().show();
	}

	public static void DefaultDialog(Context mContext, String title,
			String msg, String posString,final IDialogOperation iOperation) {
		Builder builder = new Builder(mContext);
		builder.setMessage(msg);

		builder.setTitle(title);

		builder.setPositiveButton(posString, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				iOperation.onPositive();
			}
		}); 
		builder.create().show();
	}
}