package alaiz.hashim.labfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "StudentFragment"
class StudentFragment : Fragment(), InputDialogFragment.Callbacks {
    lateinit var noStudentTextView: TextView
    lateinit var addStudentBtn: Button

    private lateinit var studentRecyclerView: RecyclerView
    private var adapter: StudentAdapter? = null

    private val studentViewModel: StudentViewModel by lazy {
        ViewModelProvider(this).get(StudentViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total students: ${studentViewModel.students.size}")
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student, container, false)

        studentRecyclerView =
            view.findViewById(R.id.student_recycler_view) as RecyclerView
        studentRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()
        return view
    }

    private fun updateUI() {
        val students = studentViewModel.students
        adapter = StudentAdapter(students)
        studentRecyclerView.adapter = adapter
    }

    private inner class StudentHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var student: Student

        private val studentNameTextView: TextView = itemView.findViewById(R.id.studentNameTV)
        private val studentNumberTextView: TextView = itemView.findViewById(R.id.studentNumberTV)
        private val studentStatusTextView: TextView = itemView.findViewById(R.id.studentStatustextView)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.deleteImageView)

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(student: Student) {

            deleteImageView.setOnClickListener{
                studentViewModel.deleteStudent(student)
                updateUI()
            }
            this.student = student
            studentNameTextView.text = this.student.stuName
            studentNumberTextView.text = this.student.stuNumber.toString()
            studentStatusTextView.apply {
                if (student.stuPass){
                     text="successful"
                } else text="failed"
            }
        }

        override fun onClick(v: View) {
            Toast.makeText(context, "${student.stuName} clicked!", Toast.LENGTH_SHORT)
                .show()
        }


    }

    private inner class StudentAdapter(var students: List<Student>) : RecyclerView.Adapter<StudentHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : StudentHolder {

            val view = layoutInflater.inflate(R.layout.fragment_student_item_list, parent, false)
            return StudentHolder(view)
        }

        override fun onBindViewHolder(holder: StudentHolder, position: Int) {
            val student = students[position]
            holder.bind(student)
        }

        override fun getItemCount(): Int {

            if (students.isNotEmpty()){
                addStudentBtn.visibility=View.GONE
                noStudentTextView.visibility=View.GONE
            }
            else{
                addStudentBtn.visibility=View.VISIBLE
                noStudentTextView.visibility=View.VISIBLE
            }

            return students.size
        }
    }

    companion object {
        fun newInstance(): StudentFragment {
            return StudentFragment()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_student_list, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_student -> {
                InputDialogFragment.newInstance()
                    .apply {
                setTargetFragment(this@StudentFragment,0)
                show(this@StudentFragment.requireFragmentManager(),"Input")
            }

                true
            } else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStudentAdded(student: Student) {
        studentViewModel.addStudent(student)
        updateUI()
    }

}
