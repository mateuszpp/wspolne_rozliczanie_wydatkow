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

	public void updateData(List<Transaction> newTransactions) {
		transactions.clear();
		transactions.addAll(newTransactions);
		notifyDataSetChanged();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ItemRowBinding itemRowBinding;

		public ViewHolder(ItemRowBinding itemRowBinding) {
			super(itemRowBinding.getRoot());
			this.itemRowBinding = itemRowBinding;
		}

		public void bind(Object obj) {
			itemRowBinding.setVariable(BR.model, obj);
			itemRowBinding.executePendingBindings();
		}

	}

	@Override
	public void onCardClicked(Transaction transaction) {
		Toast.makeText(activity, "You clicked " + transaction.getSenderName(), Toast.LENGTH_LONG).show();
	}

}