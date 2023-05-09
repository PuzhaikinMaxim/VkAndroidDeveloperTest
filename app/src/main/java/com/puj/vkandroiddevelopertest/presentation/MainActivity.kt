package com.puj.vkandroiddevelopertest.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.puj.vkandroiddevelopertest.R
import com.puj.vkandroiddevelopertest.databinding.ActivityMainBinding
import com.puj.vkandroiddevelopertest.domain.SortType

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as FileApplication).component
    }

    private val fragment = FileListFragment.newFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startFileListFragment()
        setupToolbar()
    }

    override fun onBackPressed() {
        fragment.onBackPressed()
    }

    private fun setupToolbar() {
        with(binding.tbMain.menu){
            setOnSortOptionClickListener(
                findItem(R.id.option_sort_by_name_desc),
                SortType.SORT_BY_NAME_DESC
            )
            setOnSortOptionClickListener(
                findItem(R.id.option_sort_by_name_asc),
                SortType.SORT_BY_NAME_ASC
            )
            setOnSortOptionClickListener(
                findItem(R.id.option_sort_by_size_desc),
                SortType.SORT_BY_SIZE_DESC
            )
            setOnSortOptionClickListener(
                findItem(R.id.option_sort_by_size_asc),
                SortType.SORT_BY_SIZE_ASC
            )
            setOnSortOptionClickListener(
                findItem(R.id.option_sort_by_creation_date_desc),
                SortType.SORT_BY_CREATION_DATE_DESC
            )
            setOnSortOptionClickListener(
                findItem(R.id.option_sort_by_creation_date_asc),
                SortType.SORT_BY_CREATION_DATE_ASC
            )
            setOnSortOptionClickListener(
                findItem(R.id.option_sort_by_extension_desc),
                SortType.SORT_BY_EXTENSION_DESC
            )
            setOnSortOptionClickListener(
                findItem(R.id.option_sort_by_extension_asc),
                SortType.SORT_BY_EXTENSION_ASC
            )
        }
    }

    private fun setOnSortOptionClickListener(item: MenuItem, sortType: SortType) {
        item.setOnMenuItemClickListener {
            fragment.sortFiles(sortType)
            true
        }
    }

    private fun startFileListFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fc_file_list, fragment)
            .addToBackStack(null)
            .commit()
    }
}