package com.example.applemarket

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.applemarket.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val item = intent.getParcelableExtra<Item>("item")!!

        binding.detailBack.setOnClickListener {
            finish()
        }

        with(binding) {
            detailImage.setImageDrawable(
                getDrawable(
                    resources.getIdentifier(
                        item.image,
                        "drawable",
                        this@DetailActivity.packageName
                    )
                )
            )
            detailId.text = item.seller
            detailLocation.text = item.location
            detailTitle.text = item.title
            detailIntroduce.text = item.introduce
            detailPrice.text = item.price
            detailHeart.setImageResource(if (item.checked) R.drawable.ic_filledheart else R.drawable.ic_heart)
        }

        binding.detailHeart.setOnClickListener {
            item.checked = !item.checked
            Items.items[item.num - 1].apply {
                checked = item.checked
                if (item.checked) this.like++ else this.like--
            }
            binding.detailHeart.setImageResource(if (item.checked) R.drawable.ic_filledheart else R.drawable.ic_heart)
            if (item.checked) run {
                val snackbar = Snackbar.make(binding.root, "관심 목록에 추가되었습니다.", Snackbar.LENGTH_SHORT)
                val snackbarLayout = snackbar.view.layoutParams as FrameLayout.LayoutParams
                snackbarLayout.width = ConvertDPtoPX(this, 350)
                snackbarLayout.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                snackbar.show()
            }
        }
    }

    fun ConvertDPtoPX(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }
}