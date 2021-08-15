package com.andrecristovam.daytask.ui.view


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.andrecristovam.daytask.R
import com.andrecristovam.daytask.data.constants.TaskConstants
import com.andrecristovam.daytask.databinding.ActivityNewTaskBinding
import com.andrecristovam.daytask.extensions.format
import com.andrecristovam.daytask.extensions.text
import com.andrecristovam.daytask.ui.viewmodel.AddViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class NewTaskActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: AddViewModel
    private var mTaskId: Int = 0

    private val binding by lazy { ActivityNewTaskBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mViewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        setListeners()
        observe()
        loadData()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn_new_task) {
            val title = binding.tilTitle.editText?.text.toString()
            val description = binding.tilDescription.editText?.text.toString()
            val date = binding.tilDate.editText?.text.toString()
            val hour = binding.tilHour.editText?.text.toString()

            if (title.isEmpty() || date.isEmpty() || hour.isEmpty()) {
                Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
            } else {
                mViewModel.save(mTaskId, title, description, date, hour)
            }
        }
    }

    private fun loadData() {
        val bundle = intent.extras
          if (bundle != null) {
            mTaskId = bundle.getInt(TaskConstants.TASKID)
            mViewModel.load(mTaskId)
        }
    }

    private fun observe() {
        mViewModel.saveTask.observe(this, Observer {
            if (it){
                Toast.makeText(applicationContext, R.string.successfully, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, R.string.failed_to_save, Toast.LENGTH_SHORT).show()
            }
            finish()
        })
        mViewModel.task.observe(this, Observer {
            binding.tilTitle.text = it.title
            binding.tilDescription.text = it.description
            binding.tilDate.text = it.date
            binding.tilHour.text = it.hour

        })
    }

    private fun setListeners() {

        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }
        binding.btnNewTask.setOnClickListener(this)
    }
}