
package com.fbse.recommentmobilesystem.common.view;

import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class FBSEEditText extends EditText {

    private CharSequence temp;

    private int selectionStart;

    private int selectionEnd;

    @SuppressWarnings("unused")
    private Context context;
    private Integer Maxnumber;

    public FBSEEditText(final Context context) {

        super(context);
        this.addTextChangedListener(new TextWatcher() {

            @Override
            // 初始化长度
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                temp = s;

            }

            // 输入中事件为空
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // 判断输入长度是否超过 最大长度 超出则删除并提示
            @Override
            public void afterTextChanged(Editable s) {

                selectionStart = FBSEEditText.this.getSelectionStart();
                selectionEnd = FBSEEditText.this.getSelectionEnd();
                if (temp.length() > Maxnumber) {
                    MessageUtil.commonToast(context,
                            MessageUtil.getMessage(context, Msg.E0017, new String[] { Maxnumber + "" }),
                            Toast.LENGTH_SHORT);

                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    FBSEEditText.this.setText(s);
                    FBSEEditText.this.setSelection(tempSelection);

                }
            }
        });
    }

}
