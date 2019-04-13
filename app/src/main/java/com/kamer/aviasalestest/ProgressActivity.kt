package com.kamer.aviasalestest

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_progress.*
import kotlin.math.atan2


class ProgressActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val PATH_OFFSET = 100
        private const val ANIMATION_DURATION = 30000L
        private val CONTROL_POINT_ANGLE = Math.toRadians(60.0)

        fun intent(context: Context): Intent = Intent(context, ProgressActivity::class.java)
    }

    private val planeIcon by lazy { BitmapFactory.decodeResource(resources, R.drawable.ic_plane) }

    private val sydney = LatLng(-34.0, 151.0)
    private val moscow = LatLng(55.752041, 37.617508)
    private val newYork = LatLng(40.75603, -73.986956)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        (mapFragment as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        initPath(googleMap, moscow, newYork)
    }

    private fun initPath(map: GoogleMap, from: LatLng, to: LatLng) {
        map.addMarker(MarkerOptions().position(from))
        map.addMarker(MarkerOptions().position(to))

        val bounds = LatLngBounds.builder().include(from).include(to).build()
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, PATH_OFFSET))

        val plane = map.addMarker(MarkerOptions().position(from).anchor(0.5f, 0.5f))
        val fromPoint = from.toPoint()
        val toPoint = to.toPoint()

        val path = calculatePath(fromPoint, toPoint)
        map.addPolyline(
            PolylineOptions()
                .addAll(createPoints(path))
                .width(5f)
                .color(Color.RED)
        )
        animateMovement(plane, path)
    }

    private fun calculatePath(from: PointF, to: PointF): Path =
        Path().apply {
            moveTo(from.x, from.y)
            val mid = PointF((from.x + to.x) / 2, (from.y + to.y) / 2)
            val control1 = findThirdPointOfTriangle(mid, from, CONTROL_POINT_ANGLE, CONTROL_POINT_ANGLE)
            val control2 = findThirdPointOfTriangle(mid, to, CONTROL_POINT_ANGLE, CONTROL_POINT_ANGLE)
            cubicTo(control1.x, control1.y, control2.x, control2.y, to.x, to.y)
        }

    private fun createPoints(path: Path): List<LatLng> {
        val result = mutableListOf<LatLng>()

        val tDelta = 1f / 20
        val pathMeasure = PathMeasure(path, false)
        val start = FloatArray(2)
        val end = FloatArray(2)
        pathMeasure.getPosTan(0f, start, null)
        pathMeasure.getPosTan(pathMeasure.length, end, null)
        var t = 0f
        while (t <= 1.0) {
            val point = FloatArray(2)
            pathMeasure.getPosTan(pathMeasure.length * t, point, null)
            result.add(PointF(point[0], point[1]).toLatLng())
            t += tDelta
        }

        return result
    }

    private fun animateMovement(plane: Marker, path: Path) {
        val pathMeasure = PathMeasure(path, false)
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = ANIMATION_DURATION
        animator.addUpdateListener {
            movePlane(it.animatedValue as Float, pathMeasure, plane)
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                finish()
            }

        })
        animator.start()
    }

    private fun movePlane(percent: Float, pathMeasure: PathMeasure, plane: Marker) {
        val point = FloatArray(2)
        val tan = FloatArray(2)
        pathMeasure.getPosTan(pathMeasure.length * percent, point, tan)
        val position = PointF(point[0], point[1]).toLatLng()

        val angleDeg = Math.toDegrees(atan2(tan[1].toDouble(), tan[0].toDouble())).toFloat()
        val matrix = Matrix()
        matrix.postRotate(angleDeg)
        val rotatedIcon = BitmapDescriptorFactory.fromBitmap(
            Bitmap.createBitmap(planeIcon, 0, 0, planeIcon.width, planeIcon.height, matrix, true)
        )

        plane.position = position
        plane.setIcon(rotatedIcon)
    }
}
