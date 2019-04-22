package com.lshy.a23854.chinachess;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.lshy.game.Action;
import com.lshy.game.ActionListener;
import com.lshy.game.Game;
import com.lshy.game.Role;
import com.lshy.game.TwoAbstractRole;
import com.lshy.game.chinachess.CCAction;
import com.lshy.game.chinachess.CCJuMian;
import com.lshy.game.chinachess.CChessImpl;
import com.lshy.game.chinachess.CChessQipan;
import com.lshy.game.sousuo.MMRole;
import com.lshy.game.userrole.UserRole;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initData();
        startgame();
    }

    private CChessImpl cChess;
    private CChessQipan chessQipan;
    private CCJuMian ccJuMian = new CCJuMian();
    private ChinaChessPanel chinaChessPanel;
    private ActionListener listener;
    private UserRole userRole;
    private TwoAbstractRole MMRole;
    private boolean userxianshou = true; //先后手
    private int[][] canju;

    // 构造一场人机对战
    private void initData() {
        cChess = new CChessImpl();
        userRole = new UserRole(cChess, "愚蠢的人类") {
            @Override
            public void doAction() {
                super.doAction();
                chinaChessPanel.setActivitied(true); // 激活画板
            }

            @Override
            public void OperateAction(Action action) {
                super.OperateAction(action);
                chinaChessPanel.setActivitied(false); // 取消激活画板
            }
        };
        MMRole = new MMRole(cChess, 4);
        if (userxianshou) {
            cChess.setRole(userRole, MMRole);
        } else {
            cChess.setRole(MMRole, userRole);
        }
        chessQipan = new CChessQipan(cChess, ccJuMian);
        if (canju != null) {
            chessQipan.canju = canju;
        }
//        cChessQipan.data = Canju.getCanjudata(Canju.canju2);
        ccJuMian.setChessQipan(chessQipan);
        cChess.setJuMian(ccJuMian);
        listener = new ActionListener() {
            @Override
            public void OnRoleDoAction(Action action) {
                System.out.println("角色操作"+action.getMyRole().getId());
                if(action.getMyRole()==MMRole){
                    chinaChessPanel.setPointer(((CCAction) action).nextpostion);
                }
                chinaChessPanel.post(()->{chinaChessPanel.invalidate();});
                // 等待界面绘制完成
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (chessQipan.isjiangju(((CCAction) action))) {
                    System.out.println("----------------将军");
                }
            }
        };
        chinaChessPanel.setChessQipan(chessQipan);
        chinaChessPanel.setUserOperateListener((ccaction) -> {
            userRole.OperateAction(ccaction);
        });
        chinaChessPanel.setMyrole(userRole);
    }

    protected void startgame() {
        cChess.End();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chessQipan.init();
        chinaChessPanel.invalidate();
        cChess.gameLiucheng(listener);
    }

    private void initView() {
        chinaChessPanel = findViewById(R.id.contentPanel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startgame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
