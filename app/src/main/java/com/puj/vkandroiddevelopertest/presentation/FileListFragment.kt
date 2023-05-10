package com.puj.vkandroiddevelopertest.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.vkandroiddevelopertest.databinding.FragmentFileListBinding
import com.puj.vkandroiddevelopertest.domain.SortType
import com.puj.vkandroiddevelopertest.presentation.adapter.FileListAdapter
import javax.inject.Inject

class FileListFragment: Fragment() {

    private var _binding: FragmentFileListBinding? = null
    private val binding: FragmentFileListBinding
        get() = _binding ?: throw RuntimeException("FragmentFileListBinding is null")

    private lateinit var viewModel: FileListViewModel

    private val component by lazy {
        (requireActivity().application as FileApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileListBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this, viewModelFactory)[FileListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFilesList()
    }

    private fun setupFilesList() {
        val adapter = FileListAdapter(requireActivity())
        viewModel.fileList.observe(requireActivity()){
            adapter.fileList = it
            setupNoFilesMsg(it.isNotEmpty())
        }
        adapter.onItemClickListener = {
            viewModel.onFileClick(it)
        }
        binding.rvFileList.adapter = adapter
    }

    private fun setupNoFilesMsg(hasFiles: Boolean) {
        if(hasFiles){
            binding.llNoFilesMsgContainer.visibility = View.GONE
        }
        else{
            binding.llNoFilesMsgContainer.visibility = View.VISIBLE
        }
    }

    fun sortFiles(sortType: SortType) {
        viewModel.sortFiles(sortType)
    }

    fun onBackPressed() {
        viewModel.goOneLevelUp()
    }

    override fun onDetach() {
        _binding = null
        super.onDetach()
    }

    override fun onDestroy() {
        viewModel.saveFileHashCodes()
        super.onDestroy()
    }

    companion object {

        fun newFragment(): FileListFragment {
            return FileListFragment()
        }
    }
}