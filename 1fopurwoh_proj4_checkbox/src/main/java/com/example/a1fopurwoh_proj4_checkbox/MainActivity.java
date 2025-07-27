package com.example.a1fopurwoh_proj4_checkbox;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity {

    CheckBox cbNasiAyam, cbNasiAyamCrispy, cbKentang, cbSoftDrink, cbAirMineral;
    TextView tvNasiAyam, tvNasiAyamCrispy, tvKentang, tvSoftDrink, tvAirMineral, tvTotal;
    RadioGroup radioGroup;
    RadioButton radioCash, radioCard;
    Button btnConfirm;

    int priceNasiAyam = 15000;
    int priceNasiAyamCrispy = 17000;
    int priceKentang = 8000;
    int priceSoftDrink = 7000;
    int priceAirMineral = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind CheckBoxes
        cbNasiAyam = findViewById(R.id.checkbox_nasi_ayam);
        cbNasiAyamCrispy = findViewById(R.id.checkbox_nasi_ayam_crispy);
        cbKentang = findViewById(R.id.checkbox_kentang_goreng);
        cbSoftDrink = findViewById(R.id.checkbox_soft_drink);
        cbAirMineral = findViewById(R.id.checkbox_air_mineral);

        // Bind TextViews
        tvNasiAyam = findViewById(R.id.textview_price_nasi_ayam);
        tvNasiAyamCrispy = findViewById(R.id.textview_nasi_ayam_crispy);
        tvKentang = findViewById(R.id.textview_price_kentang_goreng);
        tvSoftDrink = findViewById(R.id.textview_price_soft_drink);
        tvAirMineral = findViewById(R.id.textview_price_air_mineral);
        tvTotal = findViewById(R.id.textview_total);

        // Payment options
        radioGroup = findViewById(R.id.radio_group_payment);
        radioCash = findViewById(R.id.radio_cash);
        radioCard = findViewById(R.id.radio_card);

        // Confirm button
        btnConfirm = findViewById(R.id.button_confirm);

        // Update prices based on checkbox
        cbNasiAyam.setOnCheckedChangeListener((b, isChecked) ->
                tvNasiAyam.setText(isChecked ? formatRupiah(priceNasiAyam) : formatRupiah(0)));

        cbNasiAyamCrispy.setOnCheckedChangeListener((b, isChecked) ->
                tvNasiAyamCrispy.setText(isChecked ? formatRupiah(priceNasiAyamCrispy) : formatRupiah(0)));

        cbKentang.setOnCheckedChangeListener((b, isChecked) ->
                tvKentang.setText(isChecked ? formatRupiah(priceKentang) : formatRupiah(0)));

        cbSoftDrink.setOnCheckedChangeListener((b, isChecked) ->
                tvSoftDrink.setText(isChecked ? formatRupiah(priceSoftDrink) : formatRupiah(0)));

        cbAirMineral.setOnCheckedChangeListener((b, isChecked) ->
                tvAirMineral.setText(isChecked ? formatRupiah(priceAirMineral) : formatRupiah(0)));

        // Confirm button logic
        btnConfirm.setOnClickListener(v -> {
            int total = 0;
            if (cbNasiAyam.isChecked()) total += priceNasiAyam;
            if (cbNasiAyamCrispy.isChecked()) total += priceNasiAyamCrispy;
            if (cbKentang.isChecked()) total += priceKentang;
            if (cbSoftDrink.isChecked()) total += priceSoftDrink;
            if (cbAirMineral.isChecked()) total += priceAirMineral;

            int discount = 0;
            if (radioCard.isChecked()) {
                discount = (int) (total * 0.10); // 10% discount
            }

            int finalTotal = total - discount;
            tvTotal.setText("Total: " + formatRupiah(finalTotal));
        });
    }

    // Format price as Indonesian-style currency (e.g., Rp 15.000)
    private String formatRupiah(int amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        return "Rp " + formatter.format(amount);
    }

}
