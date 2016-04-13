package com.example.gjun.t0411puzzle;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Bitmap mBitmap;
    int mScreenWidth;
    int mScreenHeight;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    ArrayList index = new ArrayList();
    int row = 5;
    int col = 5;
    ImageButton[] ib;
    ImageButton magicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ib = new ImageButton[25];

        for(int i =0 ; i < ib.length ; i ++){
            index.add(i);
        }
        Collections.shuffle(index);
        for (int i = 0; i <ib.length; i++) {

            ib[i] = new ImageButton(this);
            ib[i].setId(i);
            //註冊 ImageButton 事件
            ib[i].setOnClickListener(this);
        }

        TableLayout layout = new TableLayout(this);
        initialize(layout);
        setContentView(layout);
        super.getSupportActionBar().hide();
    }

    //判斷是否是鄰近的圖片
    private boolean isClosed(int location, int location2) {
        if (Math.abs(location / col - location2 / col) == 1) {
            if (location % col == location2 % col) {
                return true;
            }
        } else if (location / row == location2 / row) {
            if (Math.abs(location % row - location2 % row) == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(this, "ID:" + view.getId(), Toast.LENGTH_SHORT).show();
        ImageButton t = (ImageButton) view; //獲取被點擊的按鈕
        boolean x = false;
        //判斷點選圖形是否相鄰
        x = isClosed(t.getId(), magicButton.getId());
        if (x) {
            swap(t, magicButton);
        }
    }

    void swap(ImageButton v1, ImageButton v2) {
        //交換兩個陣列元素的圖片
        v2.setImageDrawable(v1.getDrawable());
        v1.setImageBitmap(null);
        //找出圖片空白那個陣列元素的編號
        magicButton = v1;
    }


    private void initialize(TableLayout tableLayout) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.g1)).getBitmap();
        mBitmap = Bitmap.createScaledBitmap(bmp, mScreenWidth, mScreenHeight, true);
        int count = -1;

        for (int h = 0; h < mScreenHeight; h += mScreenHeight / row) {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(WC, WC));
            try {
                for (int w = 0; w < mScreenWidth; w += mScreenWidth / col) {
                    count++;
                    ib[(int) index.get(count)].setImageBitmap(Bitmap.createBitmap(mBitmap, w, h,
                            mScreenWidth / row-1 , mScreenHeight / col-1, null, true));
                    tableRow.addView(ib[count], mScreenWidth / row, mScreenHeight / col);
                }
            } catch (Exception e) {
                Log.d("Exception", e.getMessage());
            }
        }
        ib[count-1].setImageBitmap(null);
        magicButton = ib[count-1];

    }
}