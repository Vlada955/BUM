package com.bumtraining.www

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumtraining.www.databinding.ActivityTrainingDetailsBinding
import com.bumtraining.www.databinding.ExerciseCardItemBinding
import com.bumtraining.www.fragments.CardItemAdapter
import com.bumtraining.www.fragments.TrainingFragment

class TrainingDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainingDetailsBinding
    private lateinit var trainingFragment: TrainingFragment
    private var position: Int = -1
    private lateinit var adapter: ExerciseAdapter
    companion object {
        const val EXTRA_TRAINING_NAME = "training_name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = intent
        position = intent.getIntExtra("position", -1)


        val trainingName = binding.editTextTrainingName.text.toString()
        val trainingDate = binding.editTextTrainingDate.text.toString()

        var exerciseItems = mutableListOf(
            Exercise("", "")
        )

        adapter = ExerciseAdapter(exerciseItems)
        binding.recyclerViewExerciseItems.adapter = adapter

        binding.floatingActionButtonAddExerciseItem.setOnClickListener {
            addNewExerciseItem()
        }

        val exerciseItemTouchHelperCallback = ExerciseAdapter.ExerciseItemTouchHelperCallback(adapter)
        val exerciseItemTouchHelper = ItemTouchHelper(exerciseItemTouchHelperCallback)
        exerciseItemTouchHelper.attachToRecyclerView(binding.recyclerViewExerciseItems)

        binding.buttonSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra("updated_training_name", trainingName)
            intent.putExtra("updated_training_date", trainingDate)
            intent.putExtra("updated_training_position", position)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }



    fun addNewExerciseItem() {
        val newExerciseItem = Exercise("", "")

        adapter.addExerciseItem(newExerciseItem)

        binding.recyclerViewExerciseItems.scrollToPosition(adapter.itemCount - 1)

    }
}



//Exercise adapter
class ExerciseAdapter(val exerciseItems: MutableList<Exercise>):
        RecyclerView.Adapter<ExerciseAdapter.ExerciseItemViewHolder>() {
    class ExerciseItemViewHolder(private var binding:ExerciseCardItemBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(exerciseItem: Exercise) {
            binding.editTextExerciseName.text = Editable.Factory.getInstance().newEditable(exerciseItem.exercise)
            binding.editTextYoutubeLink.text = Editable.Factory.getInstance().newEditable(exerciseItem.youtubeLink)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseItemViewHolder {
        val binding =
            ExerciseCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseItemViewHolder, position: Int) {
        holder.bind(exerciseItems[position])
    }

    override fun getItemCount() = exerciseItems.size

    fun addExerciseItem(exerciseItem: Exercise) {
        exerciseItems.add(exerciseItem)
        notifyItemInserted(exerciseItems.size - 1)
    }

    //Swiping away exercises for deletion
    class ExerciseItemTouchHelperCallback(private val adapter: ExerciseAdapter): ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
        ): Int {
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(0, swipeFlags)
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            adapter.exerciseItems.removeAt(position)
            adapter.notifyItemRemoved(position)
        }

    }

}