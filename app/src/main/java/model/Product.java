package model;

public class Product {
    private String pID;
    private String pName;
    private String pCategory;
    private String pVendor;
    private String pPrice;
    private String[] pImg_src;

    public Product(String pID, String pName, String pCategory, String pVendor, String pPrice, String[] pImg_src) {
        this.pID = pID;
        this.pName = pName;
        this.pCategory = pCategory;
        this.pVendor = pVendor;
        this.pPrice = pPrice;
        this.pImg_src = pImg_src;
    }
    public Product() {
        this.pID = "No value";
        this.pName = "No value";
        this.pCategory = "No value";
        this.pVendor = "No value";
        this.pPrice = "No value";
        this.pImg_src = new String[]{"No value"};
    }
    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getpVendor() {
        return pVendor;
    }

    public void setpVendor(String pVendor) {
        this.pVendor = pVendor;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String[] getpImg_src() {
        return pImg_src;
    }

    public void setpImg_src(String pImg_src) {
        String sub = pImg_src.substring(pImg_src.indexOf("["),pImg_src.indexOf("]"));
        this.pImg_src = sub.split(",");
    }

    public String toString(){

        return "Product Info:\n"+
        "SKU: "+pID+"\n"+
        "Tên sản phẩm:"+pName+"\n"+
        "Nhà cung cấp:"+pVendor+"\n"+
        "Giá:"+pPrice+"\n"+
        "Thông tin bổ sung:";
    }
}
