package kz.partal.app;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.*;
import android.widget.Toast;

public class MainActivity extends Activity {
    PartView view;
    @Override public void onCreate(Bundle b){ super.onCreate(b); getWindow().setStatusBarColor(Color.rgb(6,19,26)); getWindow().setNavigationBarColor(Color.rgb(6,19,26)); view=new PartView(); setContentView(view); }
    void camera(){
        if(android.os.Build.VERSION.SDK_INT>=23 && checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){ requestPermissions(new String[]{Manifest.permission.CAMERA},10); return; }
        try{ startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),20); }catch(Exception e){ Toast.makeText(this,"Камера недоступна — можно продолжить демо",Toast.LENGTH_SHORT).show(); view.screen=2; view.invalidate(); }
    }
    @Override protected void onActivityResult(int r,int c,Intent d){ super.onActivityResult(r,c,d); if(r==20){ view.screen=2; view.invalidate(); view.postDelayed(()->{view.screen=3;view.invalidate();},1800); } }

    class PartView extends View {
        Paint p=new Paint(3); int screen=0; int yellow=Color.rgb(255,214,0), bg=Color.rgb(5,18,25), card=Color.rgb(20,34,43), white=Color.WHITE, muted=Color.rgb(177,188,195);
        PartView(){ super(MainActivity.this); p.setTypeface(Typeface.create("sans",Typeface.NORMAL)); setLayerType(View.LAYER_TYPE_SOFTWARE,null); }
        void txt(Canvas c,String s,float x,float y,float size,int color,boolean bold){ p.setColor(color);p.setTextSize(size);p.setTypeface(Typeface.create("sans",bold?Typeface.BOLD:Typeface.NORMAL));c.drawText(s,x,y,p); }
        void rr(Canvas c,float l,float t,float r,float b,float rad,int col){p.setColor(col);c.drawRoundRect(l,t,r,b,rad,rad,p);}
        void line(Canvas c,float x1,float y1,float x2,float y2,int col,float w){p.setColor(col);p.setStrokeWidth(w);c.drawLine(x1,y1,x2,y2,p);}
        @Override protected void onDraw(Canvas c){ super.onDraw(c); c.drawColor(bg); if(screen==0)home(c); else if(screen==1)cameraScreen(c); else if(screen==2)analyze(c); else if(screen==3)results(c); else if(screen==4)detail(c); else if(screen==5)list(c,"Избранное"); else if(screen==6)list(c,"История поиска"); else profile(c); }
        void header(Canvas c){txt(c,"PART",24,43,27,white,true);txt(c,"AL",103,43,27,yellow,true);txt(c,"⌖ Павлодар",250,41,15,white,false);txt(c,"♧",338,43,25,white,false);}
        void nav(Canvas c,int active){float y=getHeight()-64; String[] a={"⌂","⌕","♡","▣","♙"};String[] b={"Главная","Поиск","Избранное","Сообщения","Профиль"};for(int i=0;i<5;i++){float x=getWidth()/10f+i*getWidth()/5f;txt(c,a[i],x-10,y,25,i==active?yellow:muted,true);txt(c,b[i],x-30,y+24,10,i==active?yellow:muted,false);} }
        void home(Canvas c){header(c);txt(c,"Бери запчасть",24,67,15,white,false);line(c,24,98,76,98,yellow,5);txt(c,"Нажми",24,139,38,yellow,true);txt(c,"на камеру",24,179,38,white,true);txt(c,"и мы найдем",24,216,27,white,false);txt(c,"твою запчасть",24,248,27,white,false);
            rr(c,20,275,getWidth()-20,342,35,yellow);txt(c,"▣",43,318,29,Color.BLACK,true);txt(c,"Сфотографировать",82,318,20,Color.BLACK,true);txt(c,"→",getWidth()-55,319,28,Color.BLACK,true);
            txt(c,"Или найдите другим способом",24,382,19,white,true);String[] x={"⌕\nПо названию","▥\nПо артикулу","VIN\nПо VIN","▣\nПо марке авто"};for(int i=0;i<4;i++){float l=20+i*(getWidth()-40)/4f;float r=l+(getWidth()-55)/4f;rr(c,l,400,r,475,16,card);String[] q=x[i].split("\\n");txt(c,q[0],l+16,431,18,white,true);txt(c,q[1],l+12,457,10,muted,false);}txt(c,"Популярные категории",24,515,21,white,true);String[] cats={"Двигатель","Тормоза","Ходовая","Фильтры","Электрика"};for(int i=0;i<5;i++){float l=16+i*(getWidth()-32)/5f;rr(c,l,532,l+(getWidth()-45)/5f,620,14,card);txt(c,cats[i],l+8,602,10,white,true);}rr(c,18,635,getWidth()-18,700,18,card);txt(c,"Нужна редкая деталь?",34,664,18,white,true);txt(c,"Мы найдем!",34,687,18,yellow,true);nav(c,0); }
        void cameraScreen(Canvas c){txt(c,"‹",20,48,36,white,false);txt(c,"Камера",55,44,18,white,true);rr(c,18,75,getWidth()-18,getHeight()-120,24,Color.rgb(10,28,36));txt(c,"◫",getWidth()/2-40,260,80,muted,true);line(c,55,180,95,180,yellow,4);line(c,55,180,55,220,yellow,4);line(c,getWidth()-55,180,getWidth()-95,180,yellow,4);line(c,getWidth()-55,180,getWidth()-55,220,yellow,4);txt(c,"Фото",getWidth()/2-20,getHeight()-180,14,yellow,true);p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(5);p.setColor(white);c.drawCircle(getWidth()/2,getHeight()-130,34,p);p.setStyle(Paint.Style.FILL);txt(c,"‹ галерея",25,getHeight()-130,12,muted,false);txt(c,"⚡",getWidth()-45,50,22,white,false);}
        void analyze(Canvas c){txt(c,"‹",20,48,36,white,false);txt(c,"Анализируем фото...",70,44,17,white,true);txt(c,"◉",getWidth()/2-35,250,70,yellow,true);txt(c,"Анализируем",getWidth()/2-70,350,24,white,true);txt(c,"Определяем деталь и ищем",getWidth()/2-110,380,14,muted,false);txt(c,"лучшие предложения",getWidth()/2-90,402,14,muted,false);rr(c,40,440,getWidth()-40,447,4,card);rr(c,40,440,getWidth()/2+20,447,4,yellow);rr(c,24,485,getWidth()-24,555,18,card);txt(c,"AI",45,528,20,yellow,true);txt(c,"Распознавание по фото",85,515,13,white,true);txt(c,"и подбор совместимых запчастей",85,538,11,muted,false);}
        void results(Canvas c){txt(c,"‹",20,48,36,white,false);txt(c,"Результаты поиска",58,44,18,white,true);txt(c,"Генератор",24,82,19,white,true);txt(c,"Похож на вашу деталь",24,104,12,Color.GREEN,false);String[] n={"Оригинал · 85 000 ₸","Аналог · 62 000 ₸","Б/у · 45 000 ₸"};for(int i=0;i<3;i++){float y=125+i*118;rr(c,18,y,getWidth()-18,y+103,15,white);txt(c,n[i],35,y+28,15,Color.DKGRAY,true);txt(c,"Toyota · в наличии",35,y+52,11,Color.GRAY,false);txt(c,"★ 4.9",35,y+76,12,Color.DKGRAY,true);rr(c,getWidth()-125,y+62,getWidth()-35,y+90,14,yellow);txt(c,"Подробнее",getWidth()-113,y+81,10,Color.BLACK,true);}nav(c,1);}
        void detail(Canvas c){txt(c,"‹",20,48,36,white,false);txt(c,"♡",getWidth()-52,46,27,white,false);txt(c,"Генератор",24,82,25,white,true);txt(c,"Toyota 27060-0T090",24,108,14,muted,false);txt(c,"85 000 ₸",24,155,30,white,true);txt(c,"★ 4.9  ·  124 отзыва",24,180,13,yellow,true);rr(c,20,205,getWidth()-20,263,28,yellow);txt(c,"Связаться с продавцом",getWidth()/2-92,240,16,Color.BLACK,true);txt(c,"Характеристики",24,310,21,white,true);txt(c,"Бренд",24,345,12,muted,false);txt(c,"Toyota",170,345,13,white,false);txt(c,"Артикул",24,378,12,muted,false);txt(c,"27060-0T090",170,378,13,white,false);txt(c,"Состояние",24,411,12,muted,false);txt(c,"Новый (оригинал)",170,411,13,white,false);}
        void list(Canvas c,String title){txt(c,"‹",20,48,36,white,false);txt(c,title,58,44,19,white,true);String[] a={"Генератор Toyota 27060-0T090","Тормозные колодки","Фильтр масляный","Стойка амортизатора"};for(int i=0;i<4;i++){float y=85+i*86;rr(c,18,y,getWidth()-18,y+70,14,card);txt(c,a[i],35,y+28,13,white,true);txt(c,i==0?"85 000 ₸":"от 4 500 ₸",35,y+52,12,yellow,true);}nav(c,title.equals("Избранное")?2:1);}
        void profile(Canvas c){txt(c,"Профиль",24,55,27,white,true);rr(c,20,82,getWidth()-20,145,18,white);txt(c,"Арман",38,113,18,Color.DKGRAY,true);txt(c,"Мои заказы",24,185,16,white,false);txt(c,"Избранное",24,230,16,white,false);txt(c,"Уведомления",24,275,16,white,false);txt(c,"Настройки",24,320,16,white,false);txt(c,"Помощь",24,365,16,white,false);nav(c,4);}
        @Override public boolean onTouchEvent(android.view.MotionEvent e){if(e.getAction()!=1)return true;float x=e.getX(),y=e.getY();int h=getHeight();
            if(screen==0 && y>270&&y<350){camera();return true;} if(screen==3 && y>120&&y<500){screen=4;invalidate();return true;} if(screen==4&&y>195&&y<280){Toast.makeText(MainActivity.this,"Связь с продавцом — демо",Toast.LENGTH_SHORT).show();return true;}
            if(y>h-85){int idx=(int)(x/(getWidth()/5f)); if(idx==0)screen=0;else if(idx==2)screen=5;else if(idx==4)screen=7;else if(idx==1)screen=3;invalidate();return true;}
            if(screen==5||screen==6||screen==7||screen==3||screen==4||screen==2||screen==1){if(y<70&&x<70){screen=0;invalidate();return true;}}
            return true; }
    }
}
