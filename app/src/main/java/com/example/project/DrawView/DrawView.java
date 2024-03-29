package com.example.project.DrawView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.project.EndingActivity;
import com.example.project.MainActivity;

import java.util.ArrayList;

class Case {
    public boolean isDrawed = false;
    public int top;
    public int bottom;
    public int left;
    public int right;
    public int c;

    public String drawType;
    public int centerX;
    public int centerY;

    //  Constructor
    Case(
            //boolean isDrawed,
            int top,
            int bottom,
            int left,
            int right,
            String drawType
        ) {
        //this.isDrawed = isDrawed;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.drawType = drawType;
        this.centerX = left + ((right - left) / 2);
        this.centerY = top + ((bottom - top) / 2 );
    }
}

public class DrawView extends View {

    int CASE_NUMBER;

    int isXFound = 1;
    int isYFound = 2;
    int isNotFound = 0;

    Paint paint = new Paint();
    Paint obj = new Paint();

    //  Global indexes
    int indexX = -1;
    int indexY = -1;

    int touchX = -1, touchY = -1;

    ArrayList<Integer> tabX = new ArrayList<Integer>()
            , tabY = new ArrayList<Integer>()
            , tabIndexX = new ArrayList<Integer>()
            , tabIndexY = new ArrayList<Integer>();

    ArrayList<Case> TabCases = new ArrayList<Case>();

    public Case[][] matrice;

    public DrawView(Context context) {
        super(context);
    }
    public DrawView(
            Context context,
            @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLACK);
        obj.setColor(Color.GREEN);

        int bottom = 200;
        int pixel = 200;
        int right = 200;
        int left = 10;
        int initialleft = 10;
        int top = 10;
        int boardsize = CASE_NUMBER;

        if (indexX == -1 && indexY == -1)
        {
            //  Initialiser la taille de la matrice
            matrice = new Case[boardsize][boardsize];
        }

        int x=0;

        /*//  Initialisation de la matrice
        for (int x = 0; x < boardsize; x++)
        {
            for (int y = 0; y < boardsize; y++)
            {
                tab[x][y] = "";
            }
        }*/

        //  Boucle de création des cases
        for (int i=0;i<(boardsize*boardsize);i++){

            while (x< boardsize){

                for (int y=0; y < boardsize; y++){

                    //  Draw Case
                    canvas.drawRect(
                            left,
                            top,
                            right,
                            bottom,
                            paint);

                    if (TabCases.size() < boardsize*boardsize) // boardsize value
                    {
                        //  Instantiation de l'objet Case
                        Case c = new Case(top, bottom, left, right, "");

                        //  Ajouter dans le tableau
                        TabCases.add(c);

                        //  Ajouter le case dans la matrice
                        matrice[x][y] = c;
                    }
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(20);
                    left = left+pixel;
                    right = right+pixel;
                }

                x=x+1;
                left = initialleft;
                top = top+pixel;
                right= pixel;
                bottom= bottom+pixel;
            }
        }

        if (touchX != -1 && touchY != -1)
        {
            //  Itération sur les tableaux
            for (int tX = 0; tX < tabX.size(); tX++)
            {
                System.out.println("Coords : ");
                System.out.println("X : " + tabX.get(tX));
                System.out.println("Y : " + tabY.get(tX));

                obj.setStyle(Paint.Style.STROKE);
                obj.setStrokeWidth(15);
                if ((tX & 1) == 0) {
                    obj.setColor(Color.GREEN);
                    obj.setTextSize(18);
                    canvas.drawText("X",tabX.get(tX),
                            tabY.get(tX),obj );

                    matrice[tabIndexX.get(tX)][tabIndexY.get(tX)].drawType = "X";

                    if (checkGoal(indexX, indexY, "X") == isXFound)
                    {
                        System.out.println("VERT X GOAL ! ");
                        System.out.println(CASE_NUMBER);

                        //  Itération pour vérification
                        for (int v = 0; v < CASE_NUMBER; v++)
                        {
                            Paint p = new Paint();
                            p.setColor(Color.GREEN);
                            canvas.drawRect(
                                    matrice[tabIndexX.get(tX)][v].left,
                                    matrice[tabIndexX.get(tX)][v].top,
                                    matrice[tabIndexX.get(tX)][v].right,
                                    matrice[tabIndexX.get(tX)][v].bottom,
                                    p
                            );
                        }

                        //  Afficher l'activité final
                        showEndingScreen();
                    }
                    else if (checkGoal(indexX, indexY, "X") == isYFound)
                    {
                        System.out.println("VERT Y GOAL ! ");
                        System.out.println(CASE_NUMBER);

                        //  Itération pour vérification
                        for (int v = 0; v < CASE_NUMBER; v++)
                        {
                            Paint p = new Paint();
                            p.setColor(Color.GREEN);
                            canvas.drawRect(
                                    matrice[v][tabIndexY.get(tX)].left,
                                    matrice[v][tabIndexY.get(tX)].top,
                                    matrice[v][tabIndexY.get(tX)].right,
                                    matrice[v][tabIndexY.get(tX)].bottom,
                                    p
                            );
                        }

                        //  Afficher l'activité final
                        showEndingScreen();
                    }
                }
                else
                {
                    obj.setColor(Color.RED);
                    canvas.drawCircle(
                            tabX.get(tX),
                            tabY.get(tX),
                            50,
                            obj
                    );

                    matrice[tabIndexX.get(tX)][tabIndexY.get(tX)].drawType = "O";

                    if (checkGoal(indexX, indexY, "O") == isXFound)
                    {
                        System.out.println("ROUGE X GOAL ! ");
                        System.out.println(CASE_NUMBER);

                        //  Itération pour vérification
                        for (int v = 0; v < CASE_NUMBER; v++)
                        {
                            Paint p = new Paint();
                            p.setColor(Color.RED);
                            canvas.drawRect(
                                    matrice[tabIndexX.get(tX)][v].left,
                                    matrice[tabIndexX.get(tX)][v].top,
                                    matrice[tabIndexX.get(tX)][v].right,
                                    matrice[tabIndexX.get(tX)][v].bottom,
                                    p
                            );
                        }

                        //  Afficher l'activité final
                        showEndingScreen();
                    }
                    else if (checkGoal(indexX, indexY, "O") == isYFound)
                    {
                        System.out.println("ROUGE Y GOAL ! ");
                        System.out.println(CASE_NUMBER);

                        //  Itération pour vérification
                        for (int v = 0; v < CASE_NUMBER; v++)
                        {
                            Paint p = new Paint();
                            p.setColor(Color.RED);
                            canvas.drawRect(
                                    matrice[v][tabIndexY.get(tX)].left,
                                    matrice[v][tabIndexY.get(tX)].top,
                                    matrice[v][tabIndexY.get(tX)].right,
                                    matrice[v][tabIndexY.get(tX)].bottom,
                                    p
                            );
                        }

                        //  Afficher l'activité final
                        showEndingScreen();
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touchX = (int)event.getX();
            touchY = (int)event.getY();
            //boolean isDraw = false;

            System.out.println("TouchX = " + touchX);
            System.out.println("TouchY = " + touchY);

            System.out.println("Nombre de case : " + TabCases.size());

            for (int x=0; x<matrice.length; x++)
            {
                for (int y=0;y< matrice[x].length; y++)
                {
                    if (
                            touchX <= matrice[x][y].right
                            && touchX >= matrice[x][y].left
                            && touchY <= matrice[x][y].bottom
                            && touchY >= matrice[x][y].top
                            && matrice[x][y].drawType.equals("")
                    )
                    {
                        //  Index de la matrice
                        indexX = x;
                        indexY = y;

                        matrice[x][y].drawType = "X";

                        tabX.add(matrice[x][y].centerX);
                        tabY.add(matrice[x][y].centerY);
                        tabIndexX.add(indexX);
                        tabIndexY.add(indexY);

                        invalidate();
                        return true;
                    }
                }
            }

            /*//  Itération sur le tableau de case
            for (int t = 0; t <= TabCases.size(); t++)
            {

                if (touchX <= TabCases.get(t).right
                        && touchX >= TabCases.get(t).left
                        && touchY <= TabCases.get(t).bottom
                        && touchY >= TabCases.get(t).top
                )
                {
                    touchX = TabCases.get(t).left + (
                            (TabCases.get(t).right - TabCases.get(t).left) / 2);
                    touchY = TabCases.get(t).top + (
                            (TabCases.get(t).bottom - TabCases.get(t).top) / 2 );

                    //  Ajouter les coords dans les tableaux
                    for(int i=0; i<tabX.size(); i++){
                        if(tabX.get(i)==touchX && tabY.get(i) == touchY){
                           isDraw = true;
                        }
                    }
                    if(!isDraw){
                        tabX.add(touchX);
                        tabY.add(touchY);
                    }

                   // TabCases.get(t).isDrawed = true;

                    invalidate();
                    return true;
                }


            }*/

            return true;
        }

        return true;
    }

    public int checkGoal(int indexX, int indexY, String drawType)
    {
        int nbCheckedX = 0;
        int nbCheckedY = 0;

        //  Itération sur X
        for (int x=0; x < CASE_NUMBER; x++)
        {
            if (matrice[indexX][x].drawType.equals(drawType))
            {
                nbCheckedX++;
            }
        }

        //  Itération sur Y
        for (int y=0; y < CASE_NUMBER; y++)
        {
            if (matrice[y][indexY].drawType.equals(drawType))
            {
                nbCheckedY++;
            }
        }

        if (nbCheckedX == CASE_NUMBER)
        {
            return isXFound;
        }

        if (nbCheckedY == CASE_NUMBER)
        {
            return isYFound;
        }

        return isNotFound;
    }

    public void setCASE_NUMBER(int M_CASE_NUMBER)
    {
        this.CASE_NUMBER = M_CASE_NUMBER;
    }

    public void showEndingScreen()
    {
        //  Afficher l'activité final
        Context mContext = getContext();
        Intent endingView = new Intent(mContext, EndingActivity.class);
        mContext.startActivity(endingView);
    }
}
