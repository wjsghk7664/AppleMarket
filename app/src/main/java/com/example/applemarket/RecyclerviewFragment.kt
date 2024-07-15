package com.example.applemarket

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.applemarket.databinding.FragmentRecyclerviewBinding

class RecyclerviewFragment : Fragment() {

    private var _binding: FragmentRecyclerviewBinding? = null
    private val binding get() = _binding!!
    private var y = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = CustomAdapter()

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            adapter.notifyDataSetChanged()
        }

        adapter.itemClick = object : CustomAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("item", Items.items[position])
                launcher.launch(intent)
            }

            override fun onLongClick(view: View, position: Int) {
                val ctx = context
                if (ctx == null) return
                var builder = AlertDialog.Builder(ctx)
                builder.setIcon(ctx.getDrawable(R.drawable.ic_chat))
                builder.setTitle("상품 삭제")
                builder.setMessage("상품을 정말로 삭제하시겠습니까?")

                val listener = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> run {
                                Items.items.removeAt(position)
                                adapter.notifyDataSetChanged()
                            }

                            else -> return
                        }
                    }
                }
                builder.setPositiveButton("확인", listener)
                builder.setNegativeButton("취소", listener)
                builder.show()
            }

        }


        binding.fragmentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (binding.fragmentRecyclerView.canScrollVertically(-1)) {
                        if (binding.scrollbtn.visibility == INVISIBLE) {
                            binding.scrollbtn.visibility = VISIBLE
                            val fadeIn = ObjectAnimator.ofFloat(binding.scrollbtn, "alpha", 0f, 1f)
                            fadeIn.duration = 400
                            fadeIn.start()
                        }
                    } else {
                        val fadeOut = ObjectAnimator.ofFloat(binding.scrollbtn, "alpha", 1f, 0f)
                        fadeOut.duration = 200
                        fadeOut.start()
                        Thread {
                            Thread.sleep(200)
                            binding.scrollbtn.visibility = INVISIBLE
                        }.start()
                    }
                }
            })
        }

        binding.scrollbtn.setOnClickListener {
            binding.fragmentRecyclerView.smoothScrollToPosition(0)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
