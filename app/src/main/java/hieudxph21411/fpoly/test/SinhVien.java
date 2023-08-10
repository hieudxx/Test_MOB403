package hieudxph21411.fpoly.test;

public class SinhVien {
    private String _id;
    private String maSV;
    private String hoTen;
    private String email;
    private String address;
    private int point;
    private String img;

    public SinhVien(String _id, String maSV, String hoTen, String email, String address, int point, String img) {
        this._id = _id;
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.email = email;
        this.address = address;
        this.point = point;
        this.img = img;
    }

    public SinhVien() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}


