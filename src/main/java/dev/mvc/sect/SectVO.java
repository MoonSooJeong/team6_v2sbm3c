package dev.mvc.sect;

//CREATE TABLE SECT(
//    SECTNO                            NUMBER(10)     NOT NULL    PRIMARY KEY,
//    NAME                              VARCHAR2(30)     NOT NULL,
//    CNT                               NUMBER(7)    DEFAULT 0     NOT NULL,
//    RDATE                             DATE     NOT NULL
//);
public class SectVO {
  private int sectno;
  private String name;
  private int cnt;
  private String rdate;
  private int seqno;
  private String visible;
  
  public int getSectno() {
    return sectno;
  }
  public void setSectno(int sectno) {
    this.sectno = sectno;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getCnt() {
    return cnt;
  }
  public void setCnt(int cnt) {
    this.cnt = cnt;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  public int getSeqno() {
    return seqno;
  }
  public void setSeqno(int seqno) {
    this.seqno = seqno;
  }
  public String getVisible() {
    return visible;
  }
  public void setVisible(String visible) {
    this.visible = visible;
  }
  @Override
  public String toString() {
    return "SectVO [sectno=" + sectno + ", name=" + name + ", cnt=" + cnt + ", rdate=" + rdate + ", seqno=" + seqno
        + ", visible=" + visible + "]";
  }

}

