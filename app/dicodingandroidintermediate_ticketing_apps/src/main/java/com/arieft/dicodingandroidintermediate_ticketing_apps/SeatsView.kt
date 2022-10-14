package com.arieft.dicodingandroidintermediate_ticketing_apps

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat

class SeatsView : View {

    private val seats: ArrayList<Seat> = arrayListOf(
        Seat(id = 1, name = "A1", isBooked = false),
        Seat(id = 2, name = "A2", isBooked = false),
        Seat(id = 3, name = "B1", isBooked = false),
        Seat(id = 4, name = "A4", isBooked = false),
        Seat(id = 5, name = "C1", isBooked = false),
        Seat(id = 6, name = "C2", isBooked = false),
        Seat(id = 7, name = "D1", isBooked = false),
        Seat(id = 8, name = "D2", isBooked = false),
    )

    var seat: Seat? = null

    private val backgroundPaint = Paint()
    private val armrestPaint = Paint()
    private val bottomSeatPaint = Paint()
    private val mBounds = Rect()
    private val numberSeatPaint = Paint(Paint.FAKE_BOLD_TEXT_FLAG)
    private val titlePaint = Paint(Paint.FAKE_BOLD_TEXT_FLAG)

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)

        val halfOfHeight = height / 2
        val halfOfWidth = width / 2
        var value = -600f

        for (i in seats.indices) {
            if (i.mod(2) == 0) {
                seats[i].apply {
                    x = halfOfWidth - 300f
                    y = halfOfHeight + value
                }
            } else {
                seats[i].apply {
                    x = halfOfWidth + 100f
                    y = halfOfHeight + value
                }
                value += 300f
            }
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (seat in seats) {
            drawSeat(canvas, seat)
        }

        val text = "Silakan Pilih Kursi"
        titlePaint.apply {
            textSize = 50F
        }
        canvas?.drawText(text, (width / 2F) - 197F, 100F, titlePaint)
    }

    private fun drawSeat(canvas: Canvas?, seat: Seat) {
        if (seat.isBooked) {
            backgroundPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            armrestPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            bottomSeatPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            numberSeatPaint.color = ResourcesCompat.getColor(resources, R.color.black, null)
        } else {
            backgroundPaint.color = ResourcesCompat.getColor(resources, R.color.blue_500, null)
            armrestPaint.color = ResourcesCompat.getColor(resources, R.color.blue_700, null)
            bottomSeatPaint.color = ResourcesCompat.getColor(resources, R.color.blue_200, null)
            numberSeatPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
        }

        // Save State
        canvas?.save()

        // Background
        canvas?.translate(seat.x as Float, seat.y as Float)

        val backgroundPath = Path()
        backgroundPath.addRect(0f, 0f, 200f, 200f, Path.Direction.CCW)
        backgroundPath.addCircle(100f, 50f, 75f, Path.Direction.CCW)
        canvas?.drawPath(backgroundPath, backgroundPaint)

        // Sandaran Tangan
        val armrestPath = Path()
        armrestPath.addRect(0f, 0f, 50f, 200f, Path.Direction.CCW)
        canvas?.drawPath(armrestPath, armrestPaint)
        canvas?.translate(150f, 0f)
        armrestPath.addRect(0f, 0f, 50f, 200f, Path.Direction.CCW)
        canvas?.drawPath(armrestPath, armrestPaint)

        // Bagian Bawah Kursi
        canvas?.translate(-150f, 175f)
        val bottomSeatPath = Path()
        bottomSeatPath.addRect(0f, 0f, 200f, 25f, Path.Direction.CCW)
        canvas?.drawPath(bottomSeatPath, bottomSeatPaint)

        // Menulis Nomor Kursi
        canvas?.translate(0f, -175f)
        numberSeatPaint.apply {
            textSize = 50f
            numberSeatPaint.getTextBounds(seat.name, 0, seat.name.length, mBounds)
        }
        canvas?.drawText(seat.name, 100f - mBounds.centerX(), 100f, numberSeatPaint)

        // Restore ke pengaturan sebelumnya
        canvas?.restore()
    }
}