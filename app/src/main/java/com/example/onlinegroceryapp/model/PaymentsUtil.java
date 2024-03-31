package com.example.onlinegroceryapp.model;

import android.content.Context;

import com.google.android.gms.wallet.CardRequirements;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import java.util.Arrays;

public class PaymentsUtil {

    public static PaymentsClient createPaymentsClient(Context context) {
        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST) // Change to ENVIRONMENT_PRODUCTION for production
                .build();

        return Wallet.getPaymentsClient(context, walletOptions);
    }

    public static PaymentDataRequest createPaymentDataRequest() {
        // Create a PaymentDataRequest object
        // Customize the request as needed for your app
        PaymentDataRequest.Builder request = PaymentDataRequest.newBuilder()
                .setTransactionInfo(
                        TransactionInfo.newBuilder()
                                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                                .setTotalPrice("10.00") // Example: $10.00
                                .setCurrencyCode("RS")
                                .build()
                )
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                .setCardRequirements(
                        CardRequirements.newBuilder()
                                .addAllowedCardNetworks(Arrays.asList(
                                        WalletConstants.CARD_NETWORK_VISA,
                                        WalletConstants.CARD_NETWORK_MASTERCARD
                                ))
                                .build()
                )
                .setShippingAddressRequired(true)
                .setPhoneNumberRequired(true)
                .setEmailRequired(true);

        return request.build();
    }
}
