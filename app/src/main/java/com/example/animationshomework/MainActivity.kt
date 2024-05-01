package com.example.animationshomework

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var animationView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        animationView = findViewById(R.id.imageView)
        findViewById<Button>(R.id.button).setOnClickListener {
            viewPropertyAnimator()
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            valueAnimator()
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            objectAnimatior()
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            animatorSet()
        }
    }

    fun viewPropertyAnimator() {
        animationView.animate()
            .setDuration(1000L)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .scaleX(3f)
            .scaleY(3f)
            .withEndAction {
                animationView.animate()
                    .scaleX(1f)
                    .scaleY(1f)
            }
            .start()
    }

    fun valueAnimator() {
        val animator = ValueAnimator.ofFloat(1f, 3f)
        animator.duration = 1000L
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.repeatCount = 1
        animator.repeatMode = ValueAnimator.REVERSE
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            animationView.scaleX = value
            animationView.scaleY = value
        }
        animator.start()
    }

    fun objectAnimatior() {
        val pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(animationView, pvhX, pvhY)
        animator.duration = 1000L
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.repeatCount = 1
        animator.repeatMode = ValueAnimator.REVERSE
        animator.start()
    }

    fun animatorSet() {
        val pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f)
        val scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(animationView, pvhX, pvhY).apply {
            duration = 3000L
            interpolator = LinearInterpolator()
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
        }

        val rotationAnimator = ObjectAnimator.ofFloat(animationView, "rotation", 0f, 360f).apply {
            duration = 3000L
            interpolator = LinearInterpolator()
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
        }

        AnimatorSet().apply {
            playTogether(scaleAnimator, rotationAnimator)
            start()
        }
    }
}