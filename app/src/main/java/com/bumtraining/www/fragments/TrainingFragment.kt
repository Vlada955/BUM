package com.bumtraining.www.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentContainer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumtraining.www.R
import com.bumtraining.www.Training
import com.bumtraining.www.TrainingDetailsActivity
import com.bumtraining.www.databinding.ActivityMainBinding
import com.bumtraining.www.databinding.FragmentTrainingBinding
import com.bumtraining.www.databinding.TrainingCardItemBinding

class TrainingFragment : Fragment(R.layout.fragment_training) {
    private lateinit var binding: FragmentTrainingBinding
    private lateinit var trainingList: MutableList<Training>
    private lateinit var trainingRecyclerView: RecyclerView
    private lateinit var adapter: CardItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var cardItems = mutableListOf(
            Training("Push Training", "24/04/2023"),
            Training("Pull Training", "26/04/2023")
        )

        adapter = CardItemAdapter(cardItems)
        binding.recyclerViewTraining.adapter = adapter

        binding.floatingActionButtonTrainingFragment.setOnClickListener {
            addNewCardItem()
        }
        val itemTouchHelperCallback = CardItemAdapter.CardItemTouchHelperCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewTraining)

    }

    fun addNewCardItem() {
        val newCardItem = Training("New Title", "New Description")

        adapter.addCardItem(newCardItem)

        binding.recyclerViewTraining.scrollToPosition(adapter.itemCount - 1)

    }

}

class CardItemAdapter(private val cardItems: MutableList<Training>) :
    RecyclerView.Adapter<CardItemAdapter.CardItemViewHolder>() {
    class CardItemViewHolder(private var binding: TrainingCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cardItem: Training) {
            binding.textViewTrainingName.text = cardItem.name
            binding.textViewTrainingDate.text = cardItem.date
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {
        val binding =
            TrainingCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {
        holder.bind(cardItems[position])
    }

    override fun getItemCount() = cardItems.size

    fun addCardItem(cardItem: Training) {
        cardItems.add(cardItem)
        notifyItemInserted(cardItems.size-1)
    }

    class CardItemTouchHelperCallback(private val adapter: CardItemAdapter): ItemTouchHelper.Callback(){
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
            adapter.cardItems.removeAt(position)
            adapter.notifyItemRemoved(position)
        }

    }
}






