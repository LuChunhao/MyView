package com.example.myview.view.impl;

import android.view.View;

/**
 * Author: @Kongzue
 * Github: https://github.com/kongzue/
 * Homepage: http://kongzue.com/
 * Mail: myzcxhh@live.cn
 * CreateTime: 2018/10/24 22:11
 */
public interface OnLabelClickListener<T> {
    void onClick(int index, View v, T s);
}
