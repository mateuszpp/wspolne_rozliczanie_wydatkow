package com.aproteam.ioucash.activity;

import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.aproteam.ioucash.R;
import com.aproteam.ioucash.databinding.ActivityAddTransactionBinding;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;
import com.aproteam.ioucash.viewmodel.AddTransactionViewModel;

public class AddTransactionActivity extends BaseActivity implements AddTransactionViewModel.AddTransactionListener {

	ActivityAddTransactionBinding binding;

	@Override
	public void createUI() {
		binding = DataBindingUtil.setContentView(this, R.layout.activity_add_transaction);
		AddTransactionViewModel viewModel = new ViewModelProvider(this).get(AddTransactionViewModel.class);
		viewModel.setActivity(this);
		binding.setViewModel(viewModel);
		binding.setLifecycleOwner(this);
		viewModel.setLoginListener(this);
		viewModel.directionFromMe.observe(this, directionFromMe -> {
			binding.senderEditText.setEnabled(!directionFromMe);
			binding.receiverEditText.setEnabled(directionFromMe);
			AutoCompleteTextView selected = directionFromMe ? binding.receiverEditText : binding.senderEditText;
			openIME(selected);
			selected.setSelection(selected.length());
		});

		viewModel.loadUsers();
		viewModel.users.observe(this, users -> {
			ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, users);
			binding.senderEditText.setAdapter(adapter);
			binding.receiverEditText.setAdapter(adapter);
		});
		binding.senderEditText.setThreshold(1);//will start working from first character
		binding.receiverEditText.setThreshold(1);//will start working from first character
	}

	@Override
	public boolean shouldShowBackButton() {
		return false;
	}

	@Override
	public void onAddSuccess(Transaction transaction) {
	}

}