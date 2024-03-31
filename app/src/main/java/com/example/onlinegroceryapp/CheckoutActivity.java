package com.example.onlinegroceryapp;// Import necessary libraries

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinegroceryapp.model.PaymentsUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;

public class CheckoutActivity extends AppCompatActivity {

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 123;

    private PaymentsClient paymentsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize Google Pay API
        paymentsClient = PaymentsUtil.createPaymentsClient(this);

        // Handle checkout button click
        findViewById(R.id.googlePayButton).setOnClickListener(view -> requestPayment());
    }

    private void requestPayment() {
        PaymentDataRequest request = PaymentsUtil.createPaymentDataRequest();
        AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                this,
                LOAD_PAYMENT_DATA_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    PaymentData paymentData = PaymentData.getFromIntent(data);
                    if (paymentData != null) {
                        // Process payment data
                        handlePaymentSuccess(paymentData);
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    // Payment was cancelled by the user
                    break;
                case AutoResolveHelper.RESULT_ERROR:
                    // Error occurred during payment processing
                    Status status = AutoResolveHelper.getStatusFromIntent(data);
                    handleError(status);
                    break;
            }
        }
    }

    private void handlePaymentSuccess(PaymentData paymentData) {
        // Process successful payment
        Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show();
    }

    private void handleError(Status status) {
        // Handle payment error
        Toast.makeText(this, "Payment error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
    }
}
