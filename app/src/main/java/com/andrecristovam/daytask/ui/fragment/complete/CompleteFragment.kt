package com.andrecristovam.daytask.ui.fragment.complete

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.andrecristovam.daytask.R
import com.andrecristovam.daytask.data.constants.TaskConstants
import com.andrecristovam.daytask.databinding.FragmentCompleteBinding
import com.andrecristovam.daytask.ui.view.NewTaskActivity
import com.andrecristovam.daytask.ui.adapter.TaskAdapter
import com.andrecristovam.daytask.ui.listener.TaskListener
import com.andrecristovam.daytask.ui.viewmodel.TaskViewModel

class CompleteFragment : Fragment() {

    private lateinit var mViewModel: TaskViewModel
    private val mAdapter: TaskAdapter = TaskAdapter()
    private lateinit var mListener: TaskListener

    private var _binding: FragmentCompleteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recycler = root.findViewById<RecyclerView>(R.id.rv_tasks)
        recycler.adapter = mAdapter

        observe()

        mListener = object : TaskListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, NewTaskActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(TaskConstants.TASKID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.laodFragment(TaskConstants.FILTER.COMPLETED)
            }

            override fun onUpdate(id: Int, title: String, description: String, date: String, hour: String, completed: Boolean) {

                mViewModel.save(id, title, description, date, hour, completed)
                mViewModel.laodFragment(TaskConstants.FILTER.COMPLETED)
            }
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.laodFragment(TaskConstants.FILTER.COMPLETED)
    }

    private fun observe() {
        mViewModel.completedList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateTask(it)
            binding.includeEmpty.emptyState.visibility = if (it.isEmpty()) View.VISIBLE
            else View.GONE

        })
    }
}