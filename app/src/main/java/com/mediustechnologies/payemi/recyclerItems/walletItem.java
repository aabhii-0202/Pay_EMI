package com.mediustechnologies.payemi.recyclerItems;

public class walletItem {

    private int image;
    private String walletname;

    public walletItem(int paytmwallet, String walletItem ) {
        this.image = paytmwallet;
        this.walletname = walletItem;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getWalletname() {
        return walletname;
    }

    public void setWalletname(String walletname) {
        this.walletname = walletname;
    }

}
