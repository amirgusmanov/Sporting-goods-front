package com.example.springboot_project_front.base

import android.view.View

fun View?.show() { this?.let { visibility = View.VISIBLE } }

fun View?.hide() { this?.let { visibility = View.GONE } }

fun View?.makeInvisible() { this?.let { visibility = View.INVISIBLE } }