package com.example.wanjie.casiocalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Character operator = '=';
    private String firstNum = "";
    private String secondNum = "";
    private String mrcTemp = "0";
    private Boolean isFirst = true; // true means current number, false means 2nd number
    private Boolean dotFlag = false;
    private Boolean minusFlag = false;
    private TextView calculateResult;
    private Boolean firstDot = true;
    private String preInput = "";
    private String curInput = "";
    private 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculateResult = (TextView) findViewById(R.id.resultDisplay);
        calculateResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void btn0Clicked(View view) {
        insert("0");
    }

    public void btn1Clicked(View view) {
        insert("1");
    }

    public void btn2Clicked(View view) {
        insert("2");
    }

    public void btn3Clicked(View view) {
        insert("3");
    }

    public void btn4Clicked(View view) {
        insert("4");
    }

    public void btn5Clicked(View view) {
        insert("5");
    }

    public void btn6Clicked(View view) {
        insert("6");
    }

    public void btn7Clicked(View view) {
        insert("7");
    }

    public void btn8Clicked(View view) {
        insert("8");
    }

    public void btn9Clicked(View view) {
        insert("9");
    }

    public void btnDotClicked(View view) {
        preInput = curInput;
        curInput = ".";
        if (firstDot) {
            if (dotFlag) {
                return;
            } else if (firstNum == "") {
                insert("0.");
                dotFlag = true;
            } else {
                insert(".");
                dotFlag = true;
            }
        } else {
            if (dotFlag) {
                return;
            } else if (secondNum == "") {
                insert("0.");
                dotFlag = true;
            } else {
                insert(".");
                dotFlag = true;
            }
        }
    }

    public void dotOperate() {
        dotFlag = false;
        firstDot = false;
    }

    public void btnPlusClicked(View view) {
        calculateNum();
        preInput = curInput;
        curInput = "+";
        operate();
        operator = '+';
        dotOperate();
    }

    public void btnMinusClicked(View view) {
        calculateNum();
        preInput = curInput;
        curInput = "-";
        if (firstNum == "" && secondNum == "" && !minusFlag) {
            insert("-");
            minusFlag = true;
        } else {
            operate();
            operator = '-';
        }
        dotOperate();
    }

    public void btnMultipleClicked(View view) {
        calculateNum();
        preInput = curInput;
        curInput = "*";
        operate();
        operator = '*';
        dotOperate();
    }

    public void btnDivideClicked(View view) {
        calculateNum();
        preInput = curInput;
        curInput = "/";
        operate();
        operator = '/';
        dotOperate();
    }

    public void onSqrtText(View view) {
        calculateNum();
        preInput = curInput;
        curInput = "√";
        operator = '√';
        firstNum = "";
        isFirst = true;
        dotFlag = false;
        secondNum = "0";
    }

    public void btnEqualClicked(View view) {
        preInput = curInput;
        curInput = "=";
        if (operator == '√') {
            calculateNum();
        } else if (firstNum == "" || secondNum == "") {
            // do nothing
        } else {
            calculateNum();
            operator = '=';
        }
    }

    public void btnPercentClicked(View view) {
        preInput = curInput;
        curInput = "%";
        operator = '%';
        secondNum = "0";
        if (firstNum == "") {
            //do nothing
        } else {
            calculateNum();
        }
    }

    public void btnMrcClicked(View view) {
        preInput = curInput;
        curInput = "Mrc";
        Double tempDouble = Double.parseDouble(mrcTemp);
        if (preInput == curInput && preInput.equals("Mrc")) {
            mrcTemp = "0";
            calculateResult.setText("0");
        } else {
            if ((tempDouble % 1) == 0) {
                String temp = tempDouble.toString();
                calculateResult.setText(temp.substring(0, temp.length() - 2));
            } else {
                calculateResult.setText(tempDouble.toString());
            }

        }
    }

    public void btnMMinusClicked(View view) {
        preInput = curInput;
        curInput = "I";
        operate();
        operator = 'I';
        if (preInput == curInput && preInput.equals("I")) {
            // do nothing
        } else {
            mOperate();
        }

    }

    public void btnMPlusClicked(View view) {
        preInput = curInput;
        curInput = "P";
        operate();
        operator = 'P';
        mOperate();
    }

    private void mOperate() {
        if (secondNum == "" && firstNum != "") {
            secondNum = "0";
//            firstNum = mrcTemp;
            calculateNum();
//            isFirst = false;
            mrcTemp = firstNum;
        } else {
            if (firstNum == "") {
                //do nothing
            } else {
                if (mrcTemp != "") {
                    calculateNum();
                }
            }
        }
    }

    public void insert(String newChar) {
        preInput = curInput;
        curInput = newChar;
        if (isFirst) {
            firstNum += newChar;
            calculateResult.setText(firstNum.toString());
        } else {
            secondNum += newChar;
            calculateResult.setText(secondNum.toString());
        }
    }

    public void calculateNum() {
        if (firstNum == "" || secondNum == "") {
            //do nothing
        } else {
            Double firstNumber = Double.parseDouble(firstNum);
            Double secondNumber = Double.parseDouble(secondNum);
            Double tempResult = 0.0;
            if (operator == '+' || operator == 'P') {
                tempResult = firstNumber + secondNumber;
            } else if (operator == '-') {
                tempResult = firstNumber - secondNumber;
            } else if (operator == '*') {
                tempResult = firstNumber * secondNumber;
            } else if (operator == '/') {
                tempResult = firstNumber / secondNumber;
            } else if (operator == '√') {
                tempResult = Math.sqrt(firstNumber);
            } else if (operator == '%') {
                tempResult = firstNumber / 100;
            } else if (operator == 'I') {
                tempResult = Double.parseDouble(mrcTemp) - firstNumber;
            } else if (operator == '=') {
                // it needs to start a new opeartion
                firstNum = secondNum;
                isFirst = false;
                tempResult = Double.parseDouble(firstNum);
                secondNum = "";
                if ((tempResult % 1) == 0) {
                    String temp = tempResult.toString();
                    calculateResult.setText(temp.substring(0, temp.length() - 2));
                } else {
                    calculateResult.setText(tempResult.toString());
                }
                resetFlags();

                return;
            }
            firstNum = tempResult.toString();
            isFirst = false;
            secondNum = "";
            if (operator != 'P' && operator != 'I') {
                if ((tempResult % 1) == 0) {
                    String temp = tempResult.toString();
                    calculateResult.setText(temp.substring(0, temp.length() - 2));
                } else {
                    calculateResult.setText(tempResult.toString());
                }
            } else {
                if ((tempResult % 1) == 0) {
                    mrcTemp = tempResult.toString().substring(0, tempResult.toString().length() - 2);
                } else {
                    mrcTemp = tempResult.toString();
                }
                calculateResult.setText(mrcTemp);
            }
            resetFlags();
        }

    }

    public void operate() {
        isFirst = false;
    }

    public void btnClearClicked(View view) {
        preInput = curInput;
        curInput = "C";
        firstNum = "";
        secondNum = "";
        calculateResult.setText("");
        isFirst = true;
        resetFlags();
    }

    private void resetFlags() {
        dotFlag = false;
        minusFlag = false;
        firstDot = true;
    }

}