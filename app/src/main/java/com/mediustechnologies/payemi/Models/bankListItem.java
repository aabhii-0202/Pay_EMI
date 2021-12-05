package com.mediustechnologies.payemi.Models;

import android.widget.ImageView;
import android.widget.TextView;

public class bankListItem {
    private int Bank_Logo;
    private String Bank_Name;

    public bankListItem(int Bank_Logo, String Bank_Name) {
        this.Bank_Logo = Bank_Logo;
        this.Bank_Name = Bank_Name;
    }

    public int getBank_Logo() {
        return Bank_Logo;
    }

    public void setBank_Logo(int bank_Logo) {
        Bank_Logo = bank_Logo;
    }

    public String getBank_Name() {
        return Bank_Name;
    }

    public void setBank_Name(String bank_Name) {
        Bank_Name = bank_Name;
    }
}
