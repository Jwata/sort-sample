/**
  双方向リンクラッパークラス DLinkInt
  ---
  整数専用のラッパークラスです。
*/

package util;

public class DLinkInt {

  public int data;
  public DLinkInt prev,next;

  DLinkInt(int o){
    data=o;
    prev=next=null;
  }
};
