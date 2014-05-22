/**
  ---
  整数を入れておくことのできる、双方向リンク構造の
  コンテナクラス。Java の整数型はリンクの腕を
  持たないので、ラッパークラス DLinkInt に包んで格納する。
  反復は反復子を用いずに、リストクラスで行う。したがって、
  １つのリストを同時に複数反復することはできない。
  反復中にオブジェクトをリストから外した場合、その後の反復処理の継続が
  問題となる。この DList クラスは、直接 DLink ラッパクラスを使用して
  反復を行うので、その前後のオブジェクトには直接アクセス可能である。
*/

package util;

import util.DLinkInt;

public class DListInt {
  public int num;
  public DLinkInt first,last;

  // コンストラクタ
  public DListInt(){
    num=0;
    first=last=null;
  }

  // 要素の検索
  // リニアサーチを行う
  // 効率が悪いので、要素を指定しての処理はお勧めできない
  public DLinkInt search(int o){
    for(DLinkInt dl=first;dl!=null;dl=dl.next)
      if(dl.data==o) return dl;
    return null;
  }

  // リストの末尾に追加
  public void add(int o){
    DLinkInt dl=new DLinkInt(o);
    if(last!=null){
      dl.prev=last;
      last.next=dl;
      last=dl;
    }
    else
      first=last=dl;
    num++;
  }

  // 指定オブジェクトの後ろに追加
  // dl==null の時は、何もしない
  public void add(int o,DLinkInt dl){
    if(dl==null) return;
    DLinkInt d=new DLinkInt(o);
    if(dl.next!=null) dl.next.prev=d;
    d.prev=dl;
    d.next=dl.next;
    dl.next=d;
    if(dl==last) last=d;
    num++;
  }
  public void add(int o1,int o2){
    add(o1,search(o2));
  }

  // リストの先頭に挿入
  public void insert(int o){
    DLinkInt dl=new DLinkInt(o);
    if(first!=null){
      dl.next=first;
      first.prev=dl;
      first=dl;
    }
    else
      first=last=dl;
    num++;
  }

  // 指定オブジェクトの前に挿入
  // dl==null の時は、何もしない
  public void insert(int o,DLinkInt dl){
    if(dl==null) return;
    DLinkInt d=new DLinkInt(o);
    if(dl.prev!=null) dl.prev.next=d;
    d.prev=dl.prev;
    d.next=dl;
    dl.prev=d;
    if(dl==first) first=d;
    num++;
  }
  public void insert(int o1,int o2){
    insert(o1,search(o2));
  }

  // 削除
  void remove(DLinkInt dl){
    if(dl==null) return;
    if(dl==first) first=dl.next;
    if(dl==last) last=dl.prev;
    if(dl.prev!=null) dl.prev.next=dl.next;
    if(dl.next!=null) dl.next.prev=dl.prev;
    dl=null;
    num--;
  }
  public void remove(int o){
    remove(search(o));
  }

  // 先頭の要素を削除して取り出す
  public int shift(){
    int o=first.data;
    remove(first);
    return o;
  }

  // 末尾の要素を削除して取り出す
  public int pop(){
    int o=last.data;
    remove(last);
    return o;
  }

  // すべての要素を破棄する
  public void removeAll(){
    while(first!=null)
      remove(first);
  }

  // 要素を逆順に並べ変える
  public void reverse(){
    DLinkInt d;
    for(DLinkInt dl=first;dl!=null;dl=dl.prev){
      d=dl.prev;
      dl.prev=dl.next;
      dl.next=d;
    }
    d=first;
    first=last;
    last=d;
  }
}
