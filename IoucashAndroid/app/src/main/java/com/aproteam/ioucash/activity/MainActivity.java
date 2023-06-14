package com.aproteam.ioucash.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.aproteam.ioucash.App;
import com.aproteam.ioucash.R;
import com.aproteam.ioucash.adapter.TransactionsAdapter;
import com.aproteam.ioucash.databinding.ActivityMainBinding;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;
import com.aproteam.ioucash.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the main screen activity.
 * It is responsible for displaying a list of transactions, providing options for adding transactions, settings, logging out.
 */
public class MainActivity extends BaseActivity implements MainViewModel.MainModelCallback {

	ActivityMainBinding binding;
	TransactionsAdapter transactionsAdapter;
	MainViewModel mainViewModel;

	/**
	 * Creates the user interface of the activity.
	 * Sets up data binding, view model and event listeners.
	 * Initializes the transaction list.
	 */
	@Override
	public void createUI() {
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
		mainViewModel.setActivity(this);
		mainViewModel.setMainModelCallback(this);
		binding.setViewModel(mainViewModel);
		binding.setLifecycleOwner(this);

		setupList();
	}

	/**
	 * Sets up the transaction list and deleting functionality.
	 */
	public void setupList() {
		transactionsAdapter = new TransactionsAdapter(this);
		binding.transactionList.setAdapter(transactionsAdapter);
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
			@Override
			public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
				return false;
			}

			@Override
			public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
				Transaction transaction = transactionsAdapter.getItem(viewHolder.getBindingAdapterPosition());
				mainViewModel.removeTransaction(transaction);
			}
		});
		itemTouchHelper.attachToRecyclerView(binding.transactionList);

		Observer<List<Transaction>> transactionsObserver = userArrayList -> {
			if (userArrayList == null)
				App.toast(R.string.errorUnknown);
			else {
				ArrayList<Transaction> transactions = new ArrayList<>();
				List<Transaction> transationsBySender = mainViewModel.getTransactionsBySender().getValue();
				List<Transaction> transationsByReceiver = mainViewModel.getTransactionsByReceiver().getValue();
				if (transationsBySender != null)
					transactions.addAll(transationsBySender);
				if (transationsByReceiver != null)
					transactions.addAll(transationsByReceiver);
				transactionsAdapter.updateData(transactions);
			}
		};
		mainViewModel.getTransactionsBySender().observe(this, transactionsObserver);
		mainViewModel.getTransactionsBySender().observe(this, transactionsObserver);
		mainViewModel.onRefresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.settings)
			startActivity(new Intent(this, SettingsActivity.class));
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean shouldShowBackButton() {
		return false;
	}

	@Override
	public void onLogout() {
		getSessionManager().logout();
		finish();
		startActivity(new Intent(this, LoginActivity.class));
	}

	@Override
	public void onAddTransaction() {
		startActivityForResult(new Intent(this, AddTransactionActivity.class), 0);
	}

	@Override
	public void onTransactionRemoved() {
		App.toast(R.string.transactionRemoved);
	}

	@Override
	public void onTransactionNotRemoved() {
		App.toast(R.string.transactionNotRemoved);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			mainViewModel.onRefresh();
		}
	}

}