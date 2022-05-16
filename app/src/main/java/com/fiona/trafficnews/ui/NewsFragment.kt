package com.fiona.trafficnews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.fiona.trafficnews.NewsViewModel
import com.fiona.trafficnews.R
import com.fiona.trafficnews.data.New
import com.fiona.trafficnews.data.NewsDataModel
import com.google.android.material.snackbar.Snackbar
import com.fiona.trafficnews.data.Status

import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {


    private lateinit var adapter: NewsAdapter
    private lateinit var adapterList: NewsDataModel
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        newsViewModel = ViewModelProviders.of(requireActivity())[NewsViewModel::class.java]


        initUI()
        setupObservers()
        newsViewModel.getNews()
    }


    private fun initUI() {
        btn_timezone.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(
                btn_timezone
            ).navigate(R.id.to_update_fragment)
        })

    }

    private fun setupObservers() {
        newsViewModel.newsDataModel.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {

                    it.data?.let { newsList -> updateList(newsList) }

                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                    showSnack(recycler_view, it.message.toString())
                }
            }
        })

    }

    private fun updateList(newsList: NewsDataModel) {
        adapterList = newsList
        adapter = NewsAdapter(requireActivity(), adapterList as NewsDataModel)
        recycler_view.adapter = adapter
        recycler_view.setItemViewCacheSize(100)
        adapter.notifyDataSetChanged()
    }

    private fun showSnack(view: View, message: String) {
        val snackbar = Snackbar.make(
            view, message,
            Snackbar.LENGTH_INDEFINITE
        ).setAction("ok") {
        }
        val snackbarView = snackbar.view
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.textSize = 14f
        snackbar.show()
    }
}