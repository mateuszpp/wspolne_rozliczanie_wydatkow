package com.aproteam.ioucash.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aproteam.ioucash.BR;
import com.aproteam.ioucash.R;
import com.aproteam.ioucash.activity.BaseActivity;
import com.aproteam.ioucash.databinding.ItemRowBinding;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.viewmodel.CardClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A class responsible for displaying a list of transactions in RecyclerView.
 */
@SuppressLint("NotifyDataSetChanged")
public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> implements CardClickListener {

	private final List<Transaction> transactions = new ArrayList<>();
	private final BaseActivity activity;

	public TransactionsAdapter(BaseActivity activity) {
		this.activity = activity;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		ItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_row, parent, false);
		return new ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Transaction transaction = transactions.get(position);
		holder.itemRowBinding.setModel(transaction);
		boolean isFromMe = transaction.sender.username.equals(activity.getUser().username);
		int color = activity.getResources().getColor(isFromMe ? R.color.red : R.color.green);
		holder.itemRowBinding.transactionTitle.setTextColor(color);
		holder.itemRowBinding.transactionSubtitle.setTextColor(color);
		holder.bind(transaction);
		holder.itemRowBinding.setItemClickListener(this);
	}

	@Override
	public int getItemCount() {
		return transactions.size();
	}

	public Transaction getItem(int position) {
		return transactions.get(position);
	}

	/**
	 * Updates the data set with a new list of transactions and informs the adapter about the changes.
	 *
	 * @param newTransactions the new list of transactions
	 */
	public void updateData(List<Transaction> newTransactions) {
		transactions.clear();
		transactions.addAll(newTransactions);
		notifyDataSetChanged();
	}

	/**
	 * A static nested class representing a single item view in the RecyclerView.
	 * It holds the binding and binds transaction data to the views.
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ItemRowBinding itemRowBinding;

		/**
		 * Constructor of the ViewHolder static nested class.
		 *
		 * @param itemRowBinding the ItemRowBinding for the item view
		 */
		public ViewHolder(ItemRowBinding itemRowBinding) {
			super(itemRowBinding.getRoot());
			this.itemRowBinding = itemRowBinding;
		}

		/**
		 * Binds the transaction object to the item view.
		 *
		 * @param obj the transaction object
		 */
		public void bind(Object obj) {
			itemRowBinding.setVariable(BR.model, obj);
			itemRowBinding.executePendingBindings();
		}

	}

	/**
	 * Called when a card is clicked in the RecyclerView.
	 *
	 * @param transaction the clicked transaction object
	 */
	@Override
	public void onCardClicked(Transaction transaction) {
		Toast.makeText(activity, "You clicked " + transaction.getSenderName(), Toast.LENGTH_LONG).show();
	}

}