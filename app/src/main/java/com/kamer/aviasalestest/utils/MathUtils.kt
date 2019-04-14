package com.kamer.aviasalestest.utils

import android.graphics.PointF
import java.lang.Math.pow
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


fun findThirdPointOfTriangle(pointA: PointF, pointB: PointF, angleA: Double, angleB: Double): PointF {
    val x1 = pointA.x
    val y1 = pointA.y
    val x2 = pointB.x
    val y2 = pointB.y
    val u = x2 - x1
    val v = y2 - y1
    val a3 = sqrt(u * u + v * v)
    val alp3 = PI - angleA - angleB
    val a2 = a3 * sin(angleB) / sin(alp3)
    val RHS1 = x1 * u + y1 * v + a2 * a3 * cos(angleA)
    val RHS2 = y2 * u - x2 * v + a2 * a3 * sin(angleA)
    val x3 = (1 / (a3 * a3)) * (u * RHS1 - v * RHS2)
    val y3 = (1 / (a3 * a3)) * (v * RHS1 + u * RHS2)
    return PointF(x3.toFloat(), y3.toFloat())
}

fun PointF.lengthTo(point: PointF): Float = sqrt(pow(x.toDouble() - point.x, 2.0) + pow(y.toDouble() - point.y, 2.0)).toFloat()