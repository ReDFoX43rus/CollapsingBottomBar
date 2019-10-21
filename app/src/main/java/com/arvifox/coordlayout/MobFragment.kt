package com.arvifox.coordlayout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_mob.*
import java.lang.ref.WeakReference

class MobFragment : Fragment(), RecAdapter.OnClickListener {

    companion object {
        fun newInstance(bottomBarUpdateListener: IBottomBarUpdateListener): MobFragment {
            val f = MobFragment()
            f.bottomBarUpdateListener = bottomBarUpdateListener
            return f
        }
    }

    private var bottomBarUpdateListener: IBottomBarUpdateListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mob, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvList.itemAnimator = DefaultItemAnimator()
        rvList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvList.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        rvList.adapter = RecAdapter(this, GetData.get())

        val bottomBarUpdateListener = bottomBarUpdateListener ?: return
        (navigationPlaceholder.layoutParams as CoordinatorLayout.LayoutParams).behavior =
            BottomBarBehavior(R.id.toolBarContainer, R.id.collapsingToolbarLayout, bottomBarUpdateListener)
    }

    override fun onClick(p: Int, item: BazDto, v: View) {

    }
}

data class BazDto(val ad: Int)

class RecAdapter(private val l: OnClickListener, private val d: List<BazDto>) :
    RecyclerView.Adapter<RecAdapter.RecViewHolder>() {

    interface OnClickListener {
        fun onClick(p: Int, item: BazDto, v: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val bi = inflater.inflate(R.layout.item_list, parent, false)
        return RecViewHolder(bi)
    }

    override fun getItemCount(): Int = d.size

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        holder.tv.text = "foo=${d[position].ad}"
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    inner class RecViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tv: AppCompatTextView = v.findViewById(R.id.tvFoo2)

        init {
            v.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) l.onClick(
                    adapterPosition,
                    d[adapterPosition],
                    it
                )
            }
        }
    }
}

object GetData {
    fun get(): List<BazDto> = Array(80) { i -> BazDto(i) }.toList()
}

