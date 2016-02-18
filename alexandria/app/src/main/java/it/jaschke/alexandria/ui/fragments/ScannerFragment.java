package it.jaschke.alexandria.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerFragment extends Fragment implements ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;
    private ScannerCallback mCallbacks;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (ScannerCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZBarScannerView(getActivity());
        List<BarcodeFormat> formats = new ArrayList<>();
        BarcodeFormat isbn10 = BarcodeFormat.ISBN10;
        BarcodeFormat isbn13 = BarcodeFormat.ISBN13;
        BarcodeFormat ean13 = BarcodeFormat.EAN13;
        formats.add(isbn10);
        formats.add(isbn13);
        formats.add(ean13);
        mScannerView.setFormats(formats);
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (!"0123456789".equals(rawResult.getContents())) {
            if (rawResult.getBarcodeFormat().getId() == BarcodeFormat.ISBN10.getId()) {
                rawResult.setContents("978" + rawResult.getContents());
            }
            mCallbacks.onScanResult(rawResult);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface ScannerCallback {
        /**
         * Called when the result of a scan is ready.
         */
        void onScanResult(Result rawResult);
    }
}
