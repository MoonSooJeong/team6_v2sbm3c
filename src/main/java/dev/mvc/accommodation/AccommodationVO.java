package dev.mvc.accommodation;

public class AccommodationVO {
  private int accommodationno; // 숙박시설 번호, NUMBER(10)에 해당
  private String name;          // 이름, VARCHAR2(100)에 해당
  private String contact;       // 연락처, VARCHAR2(100)에 해당
  private String address;       // 주소, VARCHAR2(200)에 해당
  private String description;   // 설명, VARCHAR2(400)에 해당
  private Float rating;         // 평점, NUMBER(2,1)에 해당
  public int getAccommodationno() {
    return accommodationno;
  }
  public void setAccommodationno(int accommodationno) {
    this.accommodationno = accommodationno;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getContact() {
    return contact;
  }
  public void setContact(String contact) {
    this.contact = contact;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Float getRating() {
    return rating;
  }
  public void setRating(Float rating) {
    this.rating = rating;
  }
  @Override
  public String toString() {
    return "AccommodationVO [accommodationNo=" + accommodationno + ", name=" + name + ", contact=" + contact
        + ", address=" + address + ", description=" + description + ", rating=" + rating + "]";
  }

  
}
