package com.lshy.a23854.chinachess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lshy.game.Action;
import com.lshy.game.Role;
import com.lshy.game.chinachess.CCAction;
import com.lshy.game.chinachess.CCQiZi;
import com.lshy.game.chinachess.CChessQipan;

import java.util.List;

public class ChinaChessPanel extends View {
    private int mPanelWidth;
    private int mPanelHeight;
    private float mLineHight;
    private int starheight;
    public static final int MAX_LINE = 9;
    public static final int MAX_LINE2 = 10;

    private Paint mPaint = new Paint();
    private Paint mgrayPaint = new Paint();
    private Paint mredPaint = new Paint();
    private Paint mwhitePaint = new Paint();
    private float rate = 2.0f / 6.0f;
    private Paint mdianPaint = new Paint();

    public ChinaChessPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        mPaint.setColor(0x44ff0000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mredPaint.setColor(Color.RED);
        mwhitePaint.setColor(Color.BLACK);
        mgrayPaint.setColor(0xffffcc99);
        mdianPaint.setColor(0xffffffff);
        mgrayPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    boolean activitied;

    public boolean isActivitied() {
        return activitied;
    }

    public void setActivitied(boolean activitied) {
        this.activitied = activitied;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isActivitied()) {
                return false;
            }
            // 获取玩家点击的具体棋子，标记该子选中，并重新绘制
            int rax = (int) event.getX();
            int ray = (int) event.getY();
            int x = ((int) (rax / mLineHight));
            if (x >= MAX_LINE) {
                System.out.println(" 严重的错误发生了 x 越界");
                throw new ArrayIndexOutOfBoundsException();
            }
            int y = (int) ((ray - starheight) / mLineHight);
            if (y >= MAX_LINE2) {
                return false;
            }
            if (!xuanzhong) {  // 没有棋子被选中时，点击选择 我方棋子
                CCQiZi qiZi = chessQipan.getQiZi(x, MAX_LINE2 - 1 - y);
                if (qiZi == null || qiZi.getMyRole() != myrole) {
                    return false;
                }
                xuanzhong = true;
                xuanzhongqizi = qiZi;
                setPointer(null);
                invalidate();
                return true;
            } else {
                // 判断是否是选中棋子的目标走法
                int[] dist = new int[]{x, MAX_LINE2 - 1 - y};
                if (contain(((CCQiZi) xuanzhongqizi).getNextPosion(), dist)) {
                    CCAction ccAction = new CCAction(chessQipan.getCcJuMian(), ((CCQiZi) xuanzhongqizi), dist, ((CCQiZi) xuanzhongqizi).getMyRole());
                    userOperateListener.OnAction(ccAction);
                    xuanzhong = false;
                    xuanzhongqizi = null;
                } else {
                    CCQiZi qiZi = chessQipan.getQiZi(x, MAX_LINE2 - 1 - y);
                    if (qiZi == null || qiZi.getMyRole() != myrole) {
                        xuanzhong = false;
                        xuanzhongqizi = null;
                        return false;
                    }
                    xuanzhong = true;
                    xuanzhongqizi = qiZi;
                    invalidate();
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    private boolean contain(List<int[]> list, int[] a) {
        boolean re = false;
        for (int[] ints : list) {
            boolean iss = true;
            if (a.length == ints.length) {
                for (int i = 0; i < ints.length; i++) {
                    if (a[i] != ints[i]) {
                        iss = false;
                        break;
                    }
                }
            } else {
                iss = false;
            }
            if (iss) {
                re = true;
            }
        }
        return re;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = Math.min(widthSize, heighSize);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);// 画版
        draePicec(canvas); // 画子
        drawPointer(canvas);// 画 最新一个点
    }

    private void drawPointer(Canvas canvas) {
        if (pointer != null) {
            drawPointer(canvas, pointer);
        }
    }

    private void drawPointer(Canvas canvas, int[] ints) {

        int centerx2 = ((int) (ints[0] * mLineHight + mLineHight / 2));
        int centery2 = ((int) ((MAX_LINE2 - 1 - ints[1]) * mLineHight + mLineHight / 2)) + starheight;
        int radiuslit = ((int) (mLineHight / 10));
        RectF rectFlit = new RectF(centerx2 - radiuslit, centery2 - radiuslit, centerx2 + radiuslit, centery2 + radiuslit);
        canvas.drawOval(rectFlit, mdianPaint);

    }

    private CChessQipan chessQipan;
    private Object xuanzhongqizi;
    private Role myrole;

    public Role getMyrole() {
        return myrole;
    }

    public void setMyrole(Role myrole) {
        this.myrole = myrole;
    }

    private boolean xuanzhong;

    public CChessQipan getChessQipan() {
        return chessQipan;
    }

    public void setChessQipan(CChessQipan chessQipan) {
        this.chessQipan = chessQipan;
    }

    public Object getXuanzhongqizi() {
        return xuanzhongqizi;
    }

    public void setXuanzhongqizi(Object xuanzhongqizi) {
        this.xuanzhongqizi = xuanzhongqizi;
    }

    public boolean isXuanzhong() {
        return xuanzhong;
    }

    public void setXuanzhong(boolean xuanzhong) {
        this.xuanzhong = xuanzhong;
    }

    private void draePicec(Canvas canvas) {
        if (chessQipan == null) {
            return;
        }
        List<CCQiZi> qizes = chessQipan.getAllQiZi();
        for (CCQiZi qize : qizes) {
            if (qize != xuanzhongqizi) {
                drawone(qize, canvas);
            }
        }
        if (xuanzhong) {
            drawxuanzhong(((CCQiZi) xuanzhongqizi), canvas);
        }

    }

    private void drawxuanzhong(CCQiZi qize, Canvas canvas) {
        if (xuanzhong) {
            int centerx = ((int) (qize.getPosion()[0] * mLineHight + mLineHight / 2));
            int centery = ((int) ((MAX_LINE2 - 1 - qize.getPosion()[1]) * mLineHight + mLineHight / 2)) + starheight;
            int radius = ((int) (mLineHight * rate * 1.5));
            int k = (int) ((radius * 2 - mredPaint.getTextSize()) / 2);
            RectF rectF = new RectF(centerx - radius, centery - radius, centerx + radius, centery + radius);
            canvas.drawOval(rectF, mgrayPaint);
            if (qize.getMyRole().isXianshou()) {
                canvas.drawText(qize.getName(), centerx - radius + k, centery + Math.abs(mredPaint.ascent() + mredPaint.descent()) / 2, mredPaint);
            } else {
                canvas.drawText(qize.getName(), centerx - radius + k, centery + Math.abs(mredPaint.ascent() + mredPaint.descent()) / 2, mwhitePaint);
            }
            List<int[]> list = qize.getNextPosion();
            for (int[] ints : list) {
                drawPointer(canvas, ints);
            }
        } else {
            drawone(qize, canvas);
        }
    }

    private void drawone(CCQiZi qize, Canvas canvas) {
        int centerx = ((int) (qize.getPosion()[0] * mLineHight + mLineHight / 2));
        int centery = ((int) ((MAX_LINE2 - 1 - qize.getPosion()[1]) * mLineHight + mLineHight / 2)) + starheight;
        int radius = ((int) (mLineHight * rate));
        int k = (int) ((radius * 2 - mredPaint.getTextSize()) / 2);
        RectF rectF = new RectF(centerx - radius, centery - radius, centerx + radius, centery + radius);
        canvas.drawOval(rectF, mgrayPaint);
        if (qize.getMyRole().isXianshou()) {
            canvas.drawText(qize.getName(), centerx - radius + k, centery + Math.abs(mredPaint.ascent() + mredPaint.descent()) / 2, mredPaint);
        } else {
            canvas.drawText(qize.getName(), centerx - radius + k, centery + Math.abs(mredPaint.ascent() + mredPaint.descent()) / 2, mwhitePaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mPanelHeight = h;
        mLineHight = mPanelWidth * 1.0f / MAX_LINE;
        starheight = Math.abs(mPanelHeight - mPanelWidth) / 2;
        mredPaint.setTextSize(mLineHight * rate);
        mwhitePaint.setTextSize(mLineHight * rate);
    }


    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHight;

        int startX = (int) (lineHeight / 2);
        int endX = (int) (w - lineHeight / 2);
//        int h=mPanelHeight;
        int h = 20 * startX;
        int endX1 = (int) (h - lineHeight / 2 - 5 * lineHeight);
        int endX2 = (int) (h - lineHeight / 2 - 4 * lineHeight);
        int endX3 = (int) (h - lineHeight / 2);
        canvas.drawLine(startX * 7, startX + starheight, 11 * startX, startX * 5 + starheight, mPaint);
        canvas.drawLine(startX * 7, startX * 5 + starheight, 11 * startX, startX + starheight, mPaint);
        canvas.drawLine(startX * 7, startX * 15 + starheight, 11 * startX, startX * 19 + starheight, mPaint);
        canvas.drawLine(startX * 7, startX * 19 + starheight, 11 * startX, startX * 15 + starheight, mPaint);
        for (int i = 0; i < MAX_LINE2; i++) {
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y + starheight, endX, y + starheight, mPaint);
        }
        for (int i = 0; i < MAX_LINE; i++) {
            int y = (int) ((0.5 + i) * lineHeight);
            if (i == 0 || i == MAX_LINE - 1) {
                canvas.drawLine(y, startX + starheight, y, endX3 + starheight, mPaint);
            } else {
                canvas.drawLine(y, startX + starheight, y, endX1 + starheight, mPaint);
                canvas.drawLine(y, endX2 + starheight, y, endX3 + starheight, mPaint);
            }

        }
    }

    UserOperateListener userOperateListener;

    public UserOperateListener getUserOperateListener() {
        return userOperateListener;
    }

    public void setUserOperateListener(UserOperateListener userOperateListener) {
        this.userOperateListener = userOperateListener;
    }

    int[] pointer;

    public int[] getPointer() {
        return pointer;
    }

    public void setPointer(int[] nextpostion) {
        this.pointer = nextpostion;
    }
}
