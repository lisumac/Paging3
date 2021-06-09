package net.simplifiedcoding.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.android.codelabs.paging.model.RepoSearchResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.simplifiedcoding.R
import net.simplifiedcoding.data.MyApi
import net.simplifiedcoding.repository.GithubRepository


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PassengersViewModel
    lateinit var passengersAdapter: PassengersAdapter;
    private lateinit var service: MyApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        service = MyApi()
        val factory = PassengersViewModelFactory(GithubRepository(service))
        viewModel = ViewModelProvider(this, factory).get(PassengersViewModel::class.java)

        doSearch()

    }

    private fun observer() {
        passengersAdapter = PassengersAdapter()
        recycler_view.adapter = passengersAdapter
        lifecycleScope.launch {
            viewModel.currentSearchResult!!.collectLatest {

                passengersAdapter!!.submitData(it)
                passengersAdapter!!.notifyDataSetChanged()


            }
        }

    }

    private fun doSearch() {
        search_repo.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                getSearchResult(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun getSearchResult(toString: String) {
        passengersAdapter = PassengersAdapter()
        recycler_view.adapter = passengersAdapter
        lifecycleScope.launch {
            viewModel.searchRepo(toString).collectLatest {
                passengersAdapter!!.submitData(it)
                passengersAdapter!!.notifyDataSetChanged()
            }
        }


    }
}