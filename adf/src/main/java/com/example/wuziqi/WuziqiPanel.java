package com.example.wuziqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.lshy.wuziqi.Position;
import com.lshy.wuziqi.Qipan;
import com.lshy.wuziqi.Sousuo;

import java.util.ArrayList;
import java.util.List;

public class WuziqiPanel extends View {
    private int mPanelWidth;
    private float mLineHight;
    private int MAX_LINE = 15;

    private Paint mPaint = new Paint();
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    private float ratioPieceOfLineHight = 3 * 1.0f / 4;

    private boolean mIsWith = true;
    private List<Point> mWitharry = new ArrayList<Point>();
    private List<Point> mBlackarry = new ArrayList<Point>();

    private boolean mIsGemOver;
    private boolean mIsWhiteWinner;
    private Qipan qipan;
    private boolean dinaothink;

    public WuziqiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        // setBackgroundColor(0x44ff0000);
        init();

    }

    private void init() {
        mPaint.setColor(0x44ff0000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
        mWitharry.clear();
        mBlackarry.clear();
        mIsWith = true;
        mIsGemOver=false;
        qipan = new Qipan(MAX_LINE);
        dinaothink();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (dinaothink) {
            return false;
        }
        if (mIsGemOver) {
            init2();
            ((View) getParent()).invalidate();
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getVaLidPoint(x, y);
            if (mWitharry.contains(p) || mBlackarry.contains(p)) {
                return false;
            }
            qipan.luoZi(new Position(p.x, p.y), -1);
            lu(p);
            dinaothink();
        }
        return true;
    }

    private void init2() {
        mWitharry.clear();
        mBlackarry.clear();
        mIsWith = true;
        mIsGemOver=false;
        qipan = new Qipan(MAX_LINE);
        dinaothink();
    }

    private void dinaothink() {
        dinaothink = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Position a = ((Position) Sousuo.fenxi(qipan));
                qipan.luoZi(a, 1);
                post(new Runnable() {
                    @Override
                    public void run() {
                        lu(new Point(a.x, a.y));
                    }
                });
                dinaothink = false;
            }
        }).start();
    }

    private void lu(Point p) {
        if (mIsWith) {
            mWitharry.add(p);
        } else {
            mBlackarry.add(p);
        }
        invalidate();
        mIsWith = !mIsWith;
    }

    private Point getVaLidPoint(int x, int y) {
        return new Point((int) (x / mLineHight), (int) (y / mLineHight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heighSize);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heighSize;
        } else if (heighMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHight = mPanelWidth * 1.0f / MAX_LINE;
        int PiceWhite = (int) (mLineHight * ratioPieceOfLineHight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, PiceWhite, PiceWhite, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, PiceWhite, PiceWhite, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        draePicec(canvas);
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whithWin = chechFiveInLine(mWitharry);
        boolean blickWin = chechFiveInLine(mBlackarry);
        if (whithWin || blickWin) {
            mIsGemOver = true;
            mIsWhiteWinner = whithWin;
            String text = mIsWhiteWinner ? "白棋胜利" : "黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean chechFiveInLine(List<Point> mWitharry2) {
        for (Point p : mWitharry2) {
            int x = p.x;
            int y = p.y;

            boolean win = checkHorizontal(x, y, mWitharry2);
            if (win) return true;
            win = checkVertIcal(x, y, mWitharry2);
            if (win) return true;
            win = checkLeftDiagonal(x, y, mWitharry2);
            if (win) return true;
            win = checkRightDiagonl(x, y, mWitharry2);
            if (win) return true;
        }
        return false;
    }

    private boolean checkHorizontal(int x, int y, List<Point> mWitharry2) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) return true;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
            if (count == 5) return true;
        }
        return false;
    }

    private boolean checkRightDiagonl(int x, int y, List<Point> mWitharry2) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) return true;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
            if (count == 5) return true;
        }
        return false;
    }

    private boolean checkLeftDiagonal(int x, int y, List<Point> mWitharry2) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) return true;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
            if (count == 5) return true;
        }
        return false;
    }

    private boolean checkVertIcal(int x, int y, List<Point> mWitharry2) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 5) return true;
        for (int i = 1; i < 5; i++) {
            if (mWitharry2.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
            if (count == 5) return true;
        }
        return false;
    }

    private void draePicec(Canvas canvas) {
        for (int i = 0, n = mWitharry.size(); i < n; i++) {
            Point whitePoint = mWitharry.get(i);
            canvas.drawBitmap(mWhitePiece, (whitePoint.x + (1 - ratioPieceOfLineHight) / 2) * mLineHight,
                    (whitePoint.y + (1 - ratioPieceOfLineHight) / 2) * mLineHight, null);
        }
        for (int i = 0, n = mBlackarry.size(); i < n; i++) {
            Point blackPoint = mBlackarry.get(i);
            canvas.drawBitmap(mBlackPiece, (blackPoint.x + (1 - ratioPieceOfLineHight) / 2) * mLineHight,
                    (blackPoint.y + (1 - ratioPieceOfLineHight) / 2) * mLineHight, null);
        }
    }

    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHight;
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

}
