package com.sudo_pacman.bookapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun String.myLog(tag: String = "TTT") = Log.d(tag, this)

fun Context.toToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()